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

/**
 * @author hadar.kraus
 */
public class CompanyFacade implements CouponClientFacade {



    CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    CouponDBDAO couponDBDAO = new CouponDBDAO();
    Company company;
    /**
     * Constractor
     **/
    public CompanyFacade(Company company) {
        this.company = company;
    }

    /**
     * @return Company object related to CompanyFacade object
     **/
    public Company getCompany() {
        return company;
    }

    /**
     * Create new coupon For the company.
     * @param coupon Coupon object
     * @return 1 if coupon created
     * @return -1 if already exit
     **/
    public int createCoupon(Coupon coupon) throws CouponSystemException {

        Set<Coupon> coupons = companyDBDAO.getCoupons(this.company.getId());

        Iterator it = coupons.iterator();

        while (it.hasNext()){
            Coupon existingCoupon = (Coupon)it.next();

            if (existingCoupon.getTitle().equals(coupon.getTitle())){
                return -1;
            }
        }

        couponDBDAO.createCoupon(coupon, this.company);
        return 1;
    }

    /**
     * Remove coupon from DB.
     * @param coupon Coupon object
     * @return 1 if coupon was removed
     * @return -1 for errors
     **/
    public int removeCoupon(Coupon coupon) {
        if (coupon == null){
            return -1;
        }

        try {
            couponDBDAO.removeCoupon(coupon);

            return 1;

        } catch (DBConnectionException e) {
            e.getMessage();
        } catch (DaoException e) {
            e.getMessage();
        }
        return -1;
    }

    /**
     * Update existing coupon.
     * @param coupon Coupon object
     * @return 1 for successful update
     * @return -1 for errors
     **/
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
        return -1;
    }

    /**
     * Return coupon object by ID.
     * @param couponId long
     * @return Coupon object
     **/
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

    /**
     * @Return a Set of all coupons related to company.
     **/
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

    /**
     * @Return a Set of coupons related to the company with a specific type.
     **/
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

    /**
     * @Return a Set of coupons related to the company with a specific price.
     **/
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

    /**
     * @Return a Set of coupons related to the company with a specific expiration date
     **/
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

    /**
     * Login method for companies.
     * @param compName Company name - String
     * @param password - String
     * @return CompanyFacade object with the relevant access to a company methods
    **/
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
