package coupon.sys.dao;

import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.beans.Customer;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DaoException;

import java.util.Set;

public interface CouponDAO {

    void createCoupon(Coupon coupon, Company company) throws DaoException, DBConnectionException;
    void removeCoupon(Coupon coupon) throws DBConnectionException, DaoException;
    void updateCoupon(Coupon coupon) throws DBConnectionException, DaoException;
    void reduceAmount(Coupon coupon) throws CouponSystemException;
    void purchaseCoupon(Coupon coupon, Customer customer) throws DBConnectionException, DaoException;

    Coupon getCoupon(long id) throws CouponSystemException;
    Set<Coupon> getAllCoupons() throws DBConnectionException, DaoException;
    Set<Coupon> getCouponByType(CouponType type) throws DBConnectionException, DaoException;
}
