package coupon.facade;

import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.beans.Customer;
import coupon.sys.dao.CompanyDBDAO;
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

public class CompanyFacade implements CouponClientFacade {



    CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    CouponDBDAO couponDBDAO = new CouponDBDAO();
    Company company;

    public CompanyFacade(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    // Create new coupon. return 1 if successful
    public int createCoupon(Coupon coupon) throws CouponSystemException {

        Set<Coupon> coupons = companyDBDAO.getCoupons(this.company.getId());

        Iterator it = coupons.iterator();

        while (it.hasNext()){
            Coupon existingCoupon = (Coupon)it.next();

            if (existingCoupon.getTitle().equals(coupon.getTitle())){
                throw new CouponSystemException("Coupon already exist");
            }
        }

        couponDBDAO.createCoupon(coupon, this.company);
        return 1;
    }

    // Remove coupon from DB. if for any reason the coupon provided was not created correctly it will return 0. Successfull update will return 1.
    public int removeCoupon(Coupon coupon) {
        if (coupon == null){
            return 0;
        }

        try {
            couponDBDAO.removeCoupon(coupon);

            return 1;

        } catch (DBConnectionException e) {
            e.getMessage();
        } catch (DaoException e) {
            e.getMessage();
        }
        return 0;
    }

    public int updateCoupon(Coupon coupon) throws DaoException {
        if (coupon == null){
            throw new DaoException("Some information is missing. Cannot update coupon");
        }

        try {
            couponDBDAO.updateCoupon(coupon);
            return 1;
        } catch (DBConnectionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Coupon getCouponByID (long couponId) throws DaoException {

        Coupon coupon = null;
        try {
            coupon = couponDBDAO.getCouponByID(couponId);
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }

        if (coupon == null){
            throw new DaoException("No coupon found or error occurred");
        }
        return coupon;
    }

    public Set<Coupon> getAllCoupons() throws DaoException {

        Set<Coupon> coupons = null;
        try {
            coupons = companyDBDAO.getCoupons(company.getId());
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (coupons.isEmpty()){
            throw new DaoException("Could not find any coupon or an error occurred");
        }
        return coupons;
    }

    public Set<Coupon> getCouponByType(CouponType couponType) throws DaoException {

        Set<Coupon> AllCoupons= new HashSet<>();
        Set<Coupon> couponByType = new HashSet<>();

        try {
            AllCoupons = companyDBDAO.getCoupons(this.company.getId());

            Iterator it = AllCoupons.iterator();

            while (it.hasNext()){
                Coupon currentCoupon = (Coupon) it.next();
                if (currentCoupon.getType().equals(couponType)){
                    couponByType.add(currentCoupon);
                }
            }
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (couponByType == null){
            throw new DaoException("No coupons found or an error occurred");
        }

        return couponByType;
    }

    public Set<Coupon> getCouponByPrice(double couponPrice) throws CouponSystemException {

        Set<Coupon> couponsByPrice = new HashSet<>();
        try {
            Set<Coupon> allCompanyCoupons = companyDBDAO.getCoupons(company.getId());
            Iterator it = allCompanyCoupons.iterator();

            while (it.hasNext()){
                Coupon coupon = (Coupon)it.next();

                if (coupon.getPrice() <= couponPrice){
                    couponsByPrice.add(coupon);
                }
            }
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (couponsByPrice.isEmpty()){
            throw new CouponSystemException("No coupon found for this search");
        }
        return couponsByPrice;
    }

    public Set<Coupon> getCouponByTime (Date endDate) throws CouponSystemException {

        Set<Coupon> couponsByTime = new HashSet<>();
        try {
            Set<Coupon> allCompanyCoupons = companyDBDAO.getCoupons(company.getId());

            Iterator it = allCompanyCoupons.iterator();

            while (it.hasNext()){
                Coupon coupon = (Coupon)it.next();

                if (coupon.getEndDate().before(endDate)){
                    couponsByTime.add(coupon);
                }
            }
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (couponsByTime.isEmpty()){
            throw new CouponSystemException("No coupon found for this search");
        }

        return couponsByTime;
    }

    public static CompanyFacade companyLogin(String compName, String password) throws LoginException {
        CompanyDBDAO companyDBDAO= new CompanyDBDAO();
        try {
            if(companyDBDAO.login(compName,password)){
                return new CompanyFacade(companyDBDAO.getCompanyByName(compName));
            }
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }
        throw new LoginException("Client type didn't match");
    }
}
