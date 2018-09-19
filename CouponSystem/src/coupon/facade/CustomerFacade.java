package coupon.facade;


import coupon.ClientType;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.beans.Customer;
import coupon.sys.dao.CouponDBDAO;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CustomerFacade implements CouponClientFacade {

    CustomerDBDAO customerDBDAO = new CustomerDBDAO();
    CouponDBDAO couponDBDAO = new CouponDBDAO();
    Customer customer;

    public CustomerFacade(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int purchaseCoupon(Coupon coupon) throws CouponSystemException {
        Customer customer = this.customer;

        // Check expiration date. if expired cannot by this coupon

        Date now = new Date(System.currentTimeMillis());
        if (coupon.getEndDate().before(now)) {
            throw new CouponSystemException("Coupon has expired");
        }

        try {
            // Check if the customer already have a coupon with the same title
            Set<Coupon> coupons = customerDBDAO.getCoupons(customer.getId());
            Iterator it = coupons.iterator();

            while (it.hasNext()) {
                Coupon customerCoupon = (Coupon) it.next();

                if (customerCoupon.getTitle().equals(coupon.getTitle())) {
                    throw new CouponSystemException("Coupon already exist for this used");
                }
            }

            // If we got so far the coupon wasn't purchased before and or the coupon is expired we can continue
            couponDBDAO.purchaseCoupon(coupon, customer);

            return 1;
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Set<Coupon> getAllPurchasedCoupons() throws DaoException {

        Set<Coupon> coupons = null;
        try {
            coupons = customerDBDAO.getCoupons(customer.getId());
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (coupons.isEmpty()) {
            throw new DaoException("No coupons found or error has occurred");
        }
        return coupons;
    }

    public Set<Coupon> getAllCouponsByTypeHistorically(CouponType couponType) throws DaoException {
        Set<Coupon> allPurchased = getAllPurchasedCoupons();
        Set<Coupon> allPurchasedByType = new HashSet<>();
        Iterator it = allPurchased.iterator();

        while (it.hasNext()) {
            Coupon coupon = (Coupon) it.next();
            if (coupon.getType().equals(couponType)) {
                allPurchasedByType.add(coupon);
            }
        }

        if (allPurchasedByType.isEmpty()) {
            throw new DaoException("No coupons found or error has occurred");
        }
        return allPurchasedByType;
    }

    public Set<Coupon> getAllCouponsByPriceHistorically(double couponPrice) throws DaoException {

        Set<Coupon> allPurchased = getAllPurchasedCoupons();
        Set<Coupon> allPurchasedByPrice = new HashSet<>();
        Iterator it = allPurchased.iterator();

        while (it.hasNext()) {
            Coupon coupon = (Coupon) it.next();
            if (coupon.getPrice() == couponPrice) {
                allPurchasedByPrice.add(coupon);
            }
        }

        if (allPurchasedByPrice.isEmpty()) {
            throw new DaoException("No coupons found or error has occurred");
        }
        return allPurchasedByPrice;
    }

    public static CustomerFacade customerLogin(String username, String password) throws LoginException {
        CustomerDBDAO customerDBDAO = new CustomerDBDAO();
        try {
            if(customerDBDAO.login(username,password)){
               return new CustomerFacade(customerDBDAO.getCustomerByName(username));
            }
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        throw new LoginException("Client type didn't match");
    }
}
