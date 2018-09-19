package coupon.sys.dao;

import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.beans.Customer;
import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

//TODO - add

/*
Every Method other then the CTOR are taking a connection from the connectionPool and finally returning it to the the Pool SET
 */

public class CustomerDBDAO implements CustomerDAO {

    ConnectionPool pool;

    {
        try {
            pool = ConnectionPool.getInstance();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }
    }

    //CTOR
    public CustomerDBDAO() {
    }


    @Override
    public void createCustomer(Customer customer) throws DBConnectionException, DaoException {
        Connection con = null;
        String createCustomer = "INSERT INTO Customer (CUST_NAME, PASSWORD) VALUES(?,?)";

        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }

        try {
            PreparedStatement pstm = con.prepareStatement(createCustomer);
            pstm.setString(1, customer.getCustName());
            pstm.setString(2, customer.getPassword());
            int exUp = pstm.executeUpdate();
            System.out.println(exUp);
            if (exUp == 0) {
                throw new DaoException("Error creating customer account");
            } else if (exUp == 1) {
                System.out.println("One account created");
            } else {
                System.out.println(exUp + " accounts were updated");
            }

        } catch (SQLException e) {
            throw new DaoException("Could not create Customer account", e);
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public void removeCustomer(Customer customer) throws DBConnectionException, DaoException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String removeCust = "DELETE from customer WHERE ID=" + customer.getId();

        try {
            Statement stmt = con.createStatement();
            int removeCustomer = stmt.executeUpdate(removeCust);
            if (removeCustomer == 0) {
                throw new DaoException("Could not find customer to remove");
            } else if (removeCustomer == 1) {
                System.out.println("One account was removed");
            } else {
                System.out.println(removeCustomer + " accounts were removed");
            }

        } catch (SQLException e) {
            throw new DaoException("Could not remove customer's account", e);
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void updateCustomer(Customer customer) throws DBConnectionException, DaoException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String updateCustomer = "UPDATE customer SET CUST_NAME=?, PASSWORD=? WHERE ID=" + customer.getId();

        try {
            PreparedStatement pstm = con.prepareStatement(updateCustomer);
            pstm.setString(1, customer.getCustName());
            pstm.setString(2, customer.getPassword());
            int exUp = pstm.executeUpdate();

            if (exUp == 0) {
                throw new DaoException("Could not find Customer");
            } else if (exUp == 1) {
                System.out.println("One account updated: " + customer);
            } else {
                System.out.println(exUp + "accounts updated");
            }

        } catch (SQLException e) {
            throw new DaoException("Could not update provided customer", e);
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public Customer getCustomerByID(long customerId) throws DBConnectionException, DaoException {

        Customer customer = null;
        String getCust = "SELECT * from customer WHERE ID=" + customerId;
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCust);

            if (rs.next()) {
                customer = new Customer(rs.getLong("ID"), rs.getString("CUST_NAME"), rs.getString("PASSWORD"));
            }
            if (customer == null) {
                throw new DaoException("Could not find customer ID in DB");
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }
        return customer;
    }

    public Customer getCustomerByName(String customerName) throws DBConnectionException, DaoException {
        Customer customer = null;
        String getCust = "SELECT * from customer WHERE CUST_NAME='" + customerName +"'";
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCust);

            if (rs.next()) {
                customer = new Customer(rs.getLong("ID"), rs.getString("CUST_NAME"), rs.getString("PASSWORD"));
            }
            if (customer == null) {
                throw new DaoException("Could not find customer Name in DB");
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }
        return customer;
    }

    @Override
    public Set<Customer> getAllCustomers() throws DBConnectionException, DaoException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String getCustomers = "SELECT * from customer";
        Set<Customer> customers = new HashSet();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCustomers);
            while (rs.next()) {

                customers.add(new Customer(rs.getLong("ID"), rs.getString("CUST_NAME"), rs.getString("PASSWORD")));
            }

        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }

        if (customers.isEmpty()) {
            throw new DaoException("No customers found");
        }
        return customers;
    }

    @Override
    public Set<Coupon> getCoupons(long customerId) throws DBConnectionException, DaoException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        Set<Coupon> coupons = new HashSet();
        String getCoupons = "SELECT * from Join_Customer_Coupon WHERE CUST_ID=" + customerId + "INNER JOIN coupon ON Join_Company_Coupon.COUPON_ID = coupon.ID";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(getCoupons);

            while (rs1.next()) {

                coupons.add(new Coupon(rs1.getLong("ID"), rs1.getString("TITLE"), rs1.getDate("START_DATE"), rs1.getDate("END_DATE"),
                        rs1.getInt("AMOUNT"), CouponType.valueOf(rs1.getString("TYPE")), rs1.getString("MESSAGE"), rs1.getLong("PRICE"), rs1.getString("IMAGE")));
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }
        if (coupons.isEmpty()) {
            throw new DaoException("No coupons found");
        }
        return coupons;

    }

    @Override
    public boolean login(String custName, String password) throws DBConnectionException, LoginException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String matchPwd = "SELECT * from customer WHERE CUST_NAME='" + custName + "' AND PASSWORD='" + password + "'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(matchPwd);

            if (rs.next()){
                System.out.println(rs.next());
                return true;
            }

    } catch (SQLException e) {
            e.getMessage();
        } finally {
            pool.returnConnection(con);
        }
        throw new LoginException("Wrong username or password");
    }
}
