package Tests;

import coupon.facade.AdminFacade;
import coupon.facade.CustomerFacade;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.dao.CouponDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.util.Iterator;
import java.util.Set;

public class CustomerFacadeTests {
    public static void main(String[] args) throws CouponSystemException {



        //Test Login:

        CouponDBDAO couponDBDAO= new CouponDBDAO();
        CustomerFacade customerFacade = CustomerFacade.customerLogin("Avi", "NewPass");

        System.out.println(customerFacade.getCustomer());

//        // Get logged in Customer's coupons
//        Set<Coupon> coupons = customerFacade.getAllPurchasedCoupons();
//        Iterator it = coupons.iterator();
//        while (it.hasNext()){
//            System.out.println(it.next());
//        }
//
//        System.out.println("**********************************************");
//
//        // Test of different Get Methods
//        Set<Coupon> coupons1 = customerFacade.getAllCouponsByTypeHistorically(CouponType.HOTELS);
//        Iterator it1 = coupons1.iterator();
//        while (it1.hasNext()){
//            System.out.println(it1.next());
//        }
//
//        System.out.println("**********************************************");
//
//
//        Set<Coupon> coupons2 = customerFacade.getAllCouponsByPriceHistorically(19.0);
//        Iterator<Coupon> it2 = coupons2.iterator();
//        while (it2.hasNext()){
//            System.out.println(it2.next());
//        }

        //Test purchase coupon:
        Coupon coupon = couponDBDAO.getCouponByID(112);
        customerFacade.purchaseCoupon(coupon);
    }
}
