package coupon.sys.dao;

import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

//TODO - All exceptions change to throw exception for CouponSysException

public class CompanyDBDAO implements CompanyDAO {


    private ConnectionPool pool;

    {

        //TODO - find a way to replace throw exception for the connection which is not CouponSystemException(DBConnectionException))
        try {
            pool = ConnectionPool.getInstance();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }

    }

    public CompanyDBDAO() {
    }

    @Override
    public void createCompany(Company company) throws DBConnectionException, DaoException {
        Connection con = null;
        String createComp = "INSERT INTO Company (COMPNAME, PASSWORD, EMAIL) VALUES(?, ?, ?)";
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        try {
            PreparedStatement pstm = con.prepareStatement(createComp);
            pstm.setString(1, company.getCompName());
            pstm.setString(2, company.getPassword());
            pstm.setString(3, company.getEmail());
            int createCompanyResults = pstm.executeUpdate();

            if(createCompanyResults ==0){
                throw new DaoException("Did not Create company to DB");
            }
                } catch (SQLException e) {
            throw new DaoException("Error creating company", e);
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public void deleteCompany(Company company) throws DBConnectionException, DaoException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        long comId = company.getId();
        String deleteComp = "DELETE from company WHERE ID=" + comId;
        try {
            Statement stmt = con.createStatement();
            int deleted = stmt.executeUpdate(deleteComp);

            if (deleted == 0){
                throw new DaoException("could not find company with the provided information");
            } else {
                System.out.println("Deleted " + deleted + "entry from database");
            }
        } catch (SQLException e) {
            throw new DaoException("Error deleting company", e);
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void updateCompany(Company company) throws DBConnectionException, DaoException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }

        String updateCompany = "UPDATE company SET COMPNAME=?, PASSWORD=?, EMAIL=? WHERE ID=" + company.getId();

        try {
            PreparedStatement pstm = con.prepareStatement(updateCompany);
            pstm.setString(1, company.getCompName());
            pstm.setString(2, company.getPassword());
            pstm.setString(3, company.getEmail());
            int numUpdated = pstm.executeUpdate();

            if(numUpdated ==0){
                throw new DaoException("Could not find company");
            }else if(numUpdated == 1){
                System.out.println("One company updated");
            } else {
                System.out.println(numUpdated + "companies updated");
            }
        } catch (SQLException e) {
            throw new DaoException("Error updating company", e);
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public Company getCompanyByID(long companyId) throws CouponSystemException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }

        String getCompany = "SELECT * from company WHERE id=" + companyId;
        Company company = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCompany);

            if (rs.next()) {
                company = new Company(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
            } else {
                throw new DaoException("Couldn't find company with the provided ID");
            }

        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        }finally {
            pool.returnConnection(con);
        }
        return company;
    }

    public Company getCompanyByName (String compName) throws CouponSystemException{

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }

        String getCompany = "SELECT * from company WHERE COMPNAME='" + compName + "'";
        Company company = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCompany);

            if (rs.next()) {
                company = new Company(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));
            } else {
                throw new DaoException("Couldn't find company with the provided ID");
            }

        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        }finally {
            pool.returnConnection(con);
        }
        return company;
    }

    @Override
    public Set<Company> getAllCompanies() throws DBConnectionException, DaoException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        Set<Company> companies = new HashSet();
            String getCompanies = "SELECT * from company";

            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(getCompanies);

                while (rs.next()){
                    companies.add(new Company(rs.getLong("ID"), rs.getString("COMPNAME"),
                            rs.getString("PASSWORD"), rs.getString("EMAIL")));
                }

                if (companies.isEmpty()){
                    throw new DaoException("No company found");
                }
            } catch (SQLException e) {
                throw new DaoException("Error Getting data from server", e);
            } finally {
                pool.returnConnection(con);
            }
        return companies;
    }

    @Override
    public Set<Coupon> getCoupons(long compID) throws DBConnectionException, DaoException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        Set<Coupon> coupons = new HashSet();
        String getCoupons = "SELECT * from Join_Company_Coupon WHERE COMP_ID=" +
                            compID + "INNER JOIN coupon ON Join_Company_Coupon.COUPON_ID = coupon.ID";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCoupons);

            while (rs.next()){

                coupons.add(new Coupon(rs.getLong("ID"), rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
                        rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")), rs.getString("MESSAGE"), rs.getLong("PRICE"), rs.getString("IMAGE")));
            }
            if (coupons.isEmpty()){
                throw new DaoException("No coupons found");
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }
        return coupons;
    }

    @Override
    public boolean login(String compName, String password) throws DBConnectionException, DaoException, LoginException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String matchPwd = "SELECT ID, PASSWORD from company WHERE name='" + compName + "'" + " And PASSWORD='" + password + "'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(matchPwd);

            String pass = rs.getString("PASSWORD");
            if (rs.next()){
                return true;
            } else{
                throw new LoginException("Wrong username or password");
            }
        } catch (SQLException e) {
            throw new DaoException("Error connecting to server", e);
        } finally {
            pool.returnConnection(con);
        }
    }

}
