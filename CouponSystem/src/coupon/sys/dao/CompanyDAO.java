package coupon.sys.dao;

import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.util.Set;

public interface CompanyDAO {

    int createCompany(Company company) throws DBConnectionException, DaoException;
    int deleteCompany(Company company) throws DBConnectionException, DaoException;
    int updateCompany(Company company) throws DBConnectionException, DaoException;

    Company getCompanyByID(long compID) throws CouponSystemException;
    Set<Company> getAllCompanies() throws DBConnectionException, DaoException;
    Set<Coupon> getCoupons(long compID) throws DBConnectionException, DaoException;


    boolean login(String compName, String password) throws DBConnectionException, DaoException, LoginException;
}
