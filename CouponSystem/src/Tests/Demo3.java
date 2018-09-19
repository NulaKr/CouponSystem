package Tests;

import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.dao.CouponDBDAO;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;

import java.util.Date;


public class Demo3 {

    public static void main(String[] args) {

        CouponDBDAO couponDBDAO = new CouponDBDAO();

        Date startDate = new Date(1531570245000L);
        Date endDate = new Date(1534249254000L);
        Coupon coupon1 = new Coupon("Best vacation", startDate, endDate, 5, CouponType.HOTELS, "this is new coupon for travelers", 19.90, "");
        Coupon coupon2 = new Coupon("Camping site", startDate, endDate, 5, CouponType.CAMPING, "camping site up north", 29.90, "");


        //test coupon creation
//        try {
//            couponDBDAO.createCoupon(coupon1);
//            couponDBDAO.createCoupon(coupon2);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        } catch (DBConnectionException e) {
//            e.printStackTrace();
//        }


        //test update coupon
        try {
            Coupon coupon = new Coupon(3, "Best vacation", startDate, endDate, 9, CouponType.HEALTH, "A day in the Spa", 59.90, "");
            couponDBDAO.updateCoupon(coupon);
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }

//        Coupon coupon = new Coupon(1, "Best vacation", startDate, endDate, 5, CouponType.HOTELS, "this is new coupon for travelers", 19.90, "");
//        System.out.println(coupon);

        //test remove coupon
//        try {
//            Coupon coupon = couponDBDAO.getCoupon(5);
//            couponDBDAO.removeCoupon(coupon);
//        } catch (CouponSystemException e) {
//            e.printStackTrace();
//        }

    }
}
