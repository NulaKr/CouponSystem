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

/**
 * @author hadar.kraus
 */
public class CompanyDBDAO implements CompanyDAO {


    private ConnectionPool pool;

    {

        try {
            pool = ConnectionPool.getInstance();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }

    }

    /**
     * Constructor
     */
    public CompanyDBDAO() {
    }

    /**
     * Create ne conpany in DB
     * @param company
     * @return 1 for success
     * @return -1 for failure
     * @throws DBConnectionException
     * @throws DaoException
     */
    @Override
    public int createCompany(Company company) throws DBConnectionException, DaoException {
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
            if(createCompanyResults != 1){
                return -1;
            }else {
                System.out.println("created a company:");
                System.out.println(company);
                return 1;
            }
                } catch (SQLException e) {
            throw new DaoException("Error creating company", e);
        } finally {
            pool.returnConnection(con);
        }
    }

    /**
     * Delete company
     * @param company
     * @return 1 for success
     * @return -1 for failure
     * @throws DBConnectionException
     * @throws DaoException
     */
    @Override
    public int deleteCompany(Company company) throws DBConnectionException, DaoException {
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
                return -1;
            } else {
                System.out.println("Deleted " + deleted + "entry from database:");
                System.out.println(company);
                return 1;
            }
        } catch (SQLException e) {
            throw new DaoException("Error deleting company", e);
        } finally {
            pool.returnConnection(con);
        }

    }

    /**
     * update existing company
     * @param company
     * @return 1 for success
     * @return -1 for failure
     * @throws DBConnectionException
     * @throws DaoException
     */
    @Override
    public int updateCompany(Company company) throws DBConnectionException, DaoException {
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
                return -1;
            }else if(numUpdated == 1) {
                System.out.println("One company updated");
                return 1;
            }
        } catch (SQLException e) {
            throw new DaoException("Error updating company", e);
        } finally {
            pool.returnConnection(con);
        }
        return -1;
    }

    /**
     * Get ompany by ID
     * @param companyId
     * @returnCompany object
     * @throws CouponSystemException
     */
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

    /**
     * Get company by name
     * @param compName
     * @return Company object
     * @throws CouponSystemException
     */
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

    /**
     * Get all companies (will be used by admin only)
     * @return Set of companies
     * @throws DBConnectionException
     * @throws DaoException
     */
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

    /**
     * Get all coupons set by company
     * @param compID
     * @return Set of coupons
     * @throws DBConnectionException
     * @throws DaoException
     */
    @Override
    public Set<Coupon> getCoupons(long compID) throws DBConnectionException, DaoException {
        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        Set<Coupon> coupons = new HashSet();
        String getCoupons = "SELECT * from coupon INNER JOIN Join_Company_Coupon ON coupon.ID = Join_Company_Coupon.COUPON_ID " +
                "WHERE Join_Company_Coupon.COMP_ID=" + compID;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getCoupons);

            while (rs.next()){

                coupons.add(new Coupon(rs.getLong("ID"), rs.getString("TITLE"), rs.getDate("START_DATE"), rs.getDate("END_DATE"),
                        rs.getInt("AMOUNT"), CouponType.valueOf(rs.getString("TYPE")), rs.getString("MESSAGE"), rs.getLong("PRICE"), rs.getString("IMAGE")));
            }
            if (coupons.isEmpty()){
                System.out.println("No coupons found");
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting data from server", e);
        } finally {
            pool.returnConnection(con);
        }
        return coupons;
    }

    /**
     * Login for a company
     * @param compName
     * @param password
     * @return True/False
     * @throws DBConnectionException
     * @throws DaoException
     * @throws LoginException
     */
    @Override
    public boolean login(String compName, String password) throws DBConnectionException, DaoException, LoginException {

        Connection con = null;
        try {
            con = pool.getConnection();
        } catch (CouponSystemException e) {
            throw new DBConnectionException("Could not get connection to DB", e);
        }
        String matchPwd = "SELECT ID, PASSWORD from company WHERE COMPNAME='" + compName + "'" + " And PASSWORD='" + password + "'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(matchPwd);

//            String pass = rs.getString("PASSWORD");
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
