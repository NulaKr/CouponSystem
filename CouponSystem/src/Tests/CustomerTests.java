package Tests;

import coupon.ClientType;
import coupon.facade.CustomerFacade;
import coupon.facade.Logic.CouponSystem;
import coupon.sys.beans.Customer;
import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.nio.file.attribute.AttributeView;
import java.sql.*;
import java.util.Iterator;
import java.util.Set;

public class CustomerTests {

    public static void main(String[] args) {

        Connection con = null;
        ConnectionPool pool = null;

        {


            String custName = "Avi";
            String password = "zubur123";
            String test = "SELECT * from customer WHERE CUST_NAME='" + custName + "' AND PASSWORD='" + password + "'";

            try {
                pool = ConnectionPool.getInstance();
                con = pool.getConnection();
                Statement statement = con.createStatement();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(test);
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    System.out.println(rs.getInt("ID") + " " + rs.getString("CUST_NAME") + " " + rs.getString("PASSWORD"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DBConnectionException e) {
                e.printStackTrace();
            } catch (CouponSystemException e) {
                e.printStackTrace();
            } finally {
                pool.returnConnection(con);
            }

        }

        CustomerDBDAO customerDBDAO = new CustomerDBDAO();
        try {
            Customer sniff= customerDBDAO.getCustomerByName("sniff");
            System.out.println(sniff);

            Customer avi = customerDBDAO.getCustomerByID(108);
            System.out.println(avi);

            System.out.println("**************************************");

            Set<Customer> customers = customerDBDAO.getAllCustomers();
            Iterator iterator = customers.iterator();
            while(iterator.hasNext()){
                Customer c = (Customer) iterator.next();
                System.out.println(c);
            }
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }



    }
}