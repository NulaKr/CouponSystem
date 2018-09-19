package Tests;

import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.beans.Customer;
import coupon.sys.dao.CompanyDBDAO;
import coupon.sys.dao.CouponDBDAO;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class CouponDAOTests {

    public static void main(String[] args) throws CouponSystemException {
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        CompanyDBDAO companyDBDAO = new CompanyDBDAO();
        CustomerDBDAO customerDBDAO = new CustomerDBDAO();

        //Test coupon creation
        Company company = companyDBDAO.getCompanyByID(1);
        Date date = new Date(3, 5, 1);
        Date date2 = new Date(3, 6, 1);
        Coupon coupon = new Coupon("testingTitle", date, date2, 4, CouponType.ELECTRICITY, "Electirc", 79.90, "");
        couponDBDAO.createCoupon(coupon, company);

        //Test coupon removal + get coupon by ID
//        Coupon coupon = couponDBDAO.getCouponByID(104);
//        couponDBDAO.removeCoupon(coupon);

        //Test update of coupon + get coupon by Title
//        Coupon coupon = couponDBDAO.getCouponByTitle("Best vacation");
//        Coupon coupon1 = new Coupon(coupon.getId(),coupon.getTitle(),coupon.getStartDate(), coupon.getEndDate(),
//                coupon.getAmount(),coupon.getType(), coupon.getMessage(), 199.9,"");
//        couponDBDAO.updateCoupon(coupon1);


        //Test getting all coupons
//        System.out.println(couponDBDAO.getAllCoupons());

        //Test reduce amount
//        Coupon coupon = couponDBDAO.getCouponByID(3);
//        System.out.println(coupon.getAmount());
//        couponDBDAO.reduceAmount(coupon);
//        System.out.println(coupon.getAmount());

        //Test purchase

        //Test get coupon by type
        Set<Coupon> couponSet = couponDBDAO.getCouponByType(CouponType.HOTELS);
        Iterator it = couponSet.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
