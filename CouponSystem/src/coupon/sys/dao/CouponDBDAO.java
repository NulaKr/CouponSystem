package coupon.sys.dao;

import com.sun.xml.internal.bind.v2.model.core.ID;
import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.beans.Customer;
import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DaoException;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CouponDBDAO implements CouponDAO {

//    CouponDBDAO couponDBDAO = new CouponDBDAO();
//    CustomerDBDAO customerDBDAO = new CustomerDBDAO();
//    CompanyDBDAO companyDBDAO = new CompanyDBDAO();

    ConnectionPool pool;

    {
        try {
            pool = ConnectionPool.getInstance();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createCoupon(Coupon coupon, Company company) throws DaoException, DBConnectionException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (DBConnectionException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String createCoupon = "INSERT into coupon (TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        String selectCoupon = "SELECT * from Coupon";
        String couponCompany = "INSERT into Join_Company_Coupon (COMP_ID, COUPON_ID) VALUES (?, ?)";
        try {

            // Add coupon to coupon table
            PreparedStatement pstm = con.prepareStatement(createCoupon);

            pstm.setString(1, coupon.getTitle());
            pstm.setDate(2, new Date(coupon.getStartDate().getTime()));
            pstm.setDate(3, new Date(coupon.getEndDate().getTime()));
            pstm.setInt(4, coupon.getAmount());
            pstm.setString(5, coupon.getType().name());
            pstm.setString(6, coupon.getMessage());
            pstm.setDouble(7, coupon.getPrice());
            pstm.setString(8, coupon.getImage());
            int result = pstm.executeUpdate();
            if(result == 0){
                throw new DaoException("Did not create coupon");
            }

            //Get Coupon ID for joined table
            long id = 0;
            try {
                Coupon coupon1 = this.getCouponByTitle(coupon.getTitle());
                id = coupon1.getId();
            } catch (CouponSystemException e) {
                e.printStackTrace();
            }


            // Add coupon to the Join_Company_Coupon table if the coupon as created successfully
            if(id!=0 && result==1) {
                PreparedStatement pstm2 = con.prepareStatement(couponCompany);
                pstm2.setLong(1, company.getId());
                pstm2.setLong(2, id);
                int result2 = pstm2.executeUpdate();
                System.out.println(result2);
                if (result2 == 0) {
                    this.removeCoupon(coupon);
                    throw new DaoException("Error while adding coupon");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error creating coupon in DB", e);
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void removeCoupon(Coupon coupon) throws DBConnectionException, DaoException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (DBConnectionException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }

        // Handling all 3 tables where a coupon might be in: Coupon, Join_Customer_Coupon, Join_Company_Coupon
        String removeCoupon = "DELETE from coupon WHERE ID=" + coupon.getId();
        String joinCouponCompanyDelete = "DELETE from Join_Company_Coupon WHERE coupon_id=" + coupon.getId();
        String joinCouponCustomerDelete = "DELETE from Join_Customer_Coupon WHERE coupon_id=" + coupon.getId();

        try {
            Statement stmt = con.createStatement();
            int deleteCoupon = stmt.executeUpdate(removeCoupon);

            if (deleteCoupon == 0){
                throw new DaoException("No coupon was removed from coupon table");
            }
            Statement stmt1 = con.createStatement();
            int deleteCompCoupon = stmt1.executeUpdate(joinCouponCompanyDelete);
            if (deleteCompCoupon ==0){
                System.out.println("No coupon was removed from join_Company_coupon table");
            }

            Statement stmt2 = con.createStatement();
            int deleteCustCoupon = stmt2.executeUpdate(joinCouponCustomerDelete);

            if(deleteCustCoupon ==0){
                System.out.println("No coupon was removed from join_Customer_coupon table");
            }

        } catch (SQLException e) {
            throw new DaoException("Error removing coupon from DB");
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void updateCoupon(Coupon coupon) throws DBConnectionException, DaoException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (DBConnectionException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String updateCoupon = "UPDATE coupon SET title=?, start_date=?, end_date=?, amount=?, type=?, message=?, price=?, image=? WHERE ID=" + coupon.getId();

        try {
            PreparedStatement pstm = con.prepareStatement(updateCoupon);
            pstm.setString(1, coupon.getTitle());
            pstm.setDate(2, new Date(coupon.getStartDate().getTime()));
            pstm.setDate(3, new Date(coupon.getEndDate().getTime()));
            pstm.setInt(4, coupon.getAmount());
            pstm.setString(5, coupon.getType().toString());
            pstm.setString(6, coupon.getMessage());
            pstm.setDouble(7, coupon.getPrice());
            pstm.setString(8, coupon.getImage());

            int updateCouponResult = pstm.executeUpdate();

            if (updateCouponResult==0){
                throw new DaoException("No coupon updated");
            }

        } catch (SQLException e) {
            throw new DaoException("Error updating DB", e);
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public Coupon getCouponByID(long couponId) throws CouponSystemException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (DBConnectionException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String getCoupon = "SELECT * from coupon WHERE Id=" + couponId;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCoupon);

            if (rs.next()) {
                Coupon coupon = new Coupon(rs.getLong("ID"), rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
                        rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")), rs.getString("MESSAGE"), rs.getLong("PRICE"), rs.getString("IMAGE"));

                return coupon;
            } else {
                throw new CouponSystemException("Coupon not found");
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public Coupon getCouponByTitle(String title) throws CouponSystemException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (DBConnectionException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String getCoupon = "SELECT * from coupon WHERE TITLE='" + title + "'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCoupon);

            if (rs.next()) {
                Coupon coupon = new Coupon(rs.getLong("ID"), rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
                        rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")), rs.getString("MESSAGE"), rs.getLong("PRICE"), rs.getString("IMAGE"));

                return coupon;
            } else {
                throw new CouponSystemException("Coupon not found");
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public Set<Coupon> getCouponByType (CouponType type) throws DBConnectionException, DaoException {

        Set<Coupon> couponsByType = new HashSet<>();
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (DBConnectionException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String getCouponByType = "SELECT * from coupon WHERE TYPE='" + type + "'";

        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCouponByType);

            while (rs.next()){
                couponsByType.add(new Coupon(rs.getLong("ID"), rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
                        rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")), rs.getString("MESSAGE"), rs.getLong("PRICE"), rs.getString("IMAGE")));


            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }

        return couponsByType;
    }

    @Override
    public Set<Coupon> getAllCoupons () throws DBConnectionException, DaoException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (DBConnectionException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        Set<Coupon> coupons = new HashSet();

        try {
            Statement stmt = con.createStatement();
            String getAllCoupon = "SELECT * from coupon";
            ResultSet rs = stmt.executeQuery(getAllCoupon);

            while (rs.next()) {
                coupons.add(new Coupon(rs.getLong("ID"), rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
                        rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")), rs.getString("MESSAGE"), rs.getLong("PRICE"), rs.getString("IMAGE")));
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }

        return coupons;
    }

    @Override
    public void reduceAmount(Coupon coupon) throws CouponSystemException {

        if (coupon.getAmount()==0){
            throw new CouponSystemException("No coupon left");
        }

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }

        // If amount is 0 throw exception
        if(coupon.getAmount() <= 0){
            throw new DaoException("All out");
        }

        // get amount of coupons available and reduce it by 1;
        int newAmount = coupon.getAmount()-1;

        // set the new amount to the coupon object
        coupon.setAmount(newAmount);
        //update the DB with the coupon new amount
        updateCoupon(coupon);
        try {
            updateCoupon(coupon);
        } catch (DaoException e) {
            e.getMessage();
        }
    }

    @Override
    public void purchaseCoupon(Coupon coupon, Customer customer) throws DBConnectionException, DaoException {
        Connection con = null;

        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }

        String addCouponToLinkedTable = "INSERT INTO Join_Customer_Coupon (CUST_ID, COUPON_ID) VALUES (?,?)";

        if (coupon.getAmount() <= 0){
            throw new DaoException("No available coupons of this kind");
        }
        try {
            // Add the coupon to the linked table Join_Customer_Coupon
            PreparedStatement pstm = con.prepareStatement(addCouponToLinkedTable);
            pstm.setLong(1, customer.getId());
            pstm.setLong(2, coupon.getId());
            pstm.executeUpdate();
            // Reduce the amount of the coupon as it was bought
            reduceAmount(coupon);

        } catch (SQLException e) {
            throw new DaoException("Could not create Customer account", e);
        } catch (CouponSystemException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnection(con);
        }
    }


}
