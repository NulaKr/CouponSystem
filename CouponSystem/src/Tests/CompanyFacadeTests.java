package Tests;

import coupon.facade.CompanyFacade;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class CompanyFacadeTests {

    public static void main(String[] args) throws CouponSystemException {

        //Test Login

        CompanyFacade companyFacade = CompanyFacade.companyLogin("OneCompany", "password");
        System.out.println(companyFacade.getCompany());

        //Test Get methods
//
//        System.out.println(companyFacade.getCompany());
//        System.out.println("**********************************************");
//
//        Set<Coupon> coupons = companyFacade.getAllCoupons();
//        Iterator it = coupons.iterator();
//        while (it.hasNext()){
//            System.out.println(it.next());
//        }
//
//        System.out.println("**********************************************");
//
//        System.out.println(companyFacade.getCouponByID(111));
//        System.out.println(companyFacade.getCouponByPrice(79.0));
//        System.out.println(companyFacade.getCouponByTime(new Date(1903-07-01)));
//        System.out.println(companyFacade.getCouponByType(CouponType.ELECTRICITY));
//
//        // Test create new coupon
//        Date startDate = new Date();
//        Date endDate = new Date(System.currentTimeMillis()+1000000);
//        Coupon coupon = new Coupon("newCoupon", startDate, endDate, 3, CouponType.CAMPING, "Camping is fun", 100.50, "");
//        companyFacade.createCoupon(coupon);
//        System.out.println(companyFacade.getCouponByType(CouponType.CAMPING));

        //Test update coupon

//        Coupon coupon1 = companyFacade.getCouponByID(125);
//        Coupon coupon2 = new Coupon(coupon1.getId(), coupon1.getTitle(), coupon1.getStartDate(), coupon1.getEndDate(), coupon1.getAmount(), coupon1.getType()
//        ,"Camping?", 100.99, "");
//        companyFacade.updateCoupon(coupon2);
//        System.out.println(companyFacade.getCouponByID(125));

        //Test remove coupon

//        companyFacade.removeCoupon(companyFacade.getCouponByID(125));
//        companyFacade.getAllCoupons();
    }
}
