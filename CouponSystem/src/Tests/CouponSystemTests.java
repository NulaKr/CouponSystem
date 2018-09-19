package Tests;

import coupon.ClientType;
import coupon.facade.AdminFacade;
import coupon.facade.CompanyFacade;
import coupon.facade.CustomerFacade;
import coupon.facade.Logic.CouponSystem;
import coupon.sys.beans.Company;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

public class CouponSystemTests {
    public static void main(String[] args) throws LoginException, DBConnectionException, DaoException {


        CouponSystem couponSystem = CouponSystem.getInstance();

        CustomerFacade customerFacade = (CustomerFacade)couponSystem.login("Avi", "NewPass", ClientType.CUSTOMER);
        System.out.println(customerFacade.getCustomer());
        System.out.println("**********************************************");

        AdminFacade adminFacade = (AdminFacade) couponSystem.login("Admin", "1234", ClientType.ADMIN);
        System.out.println(adminFacade.getAllCustomers());
        System.out.println("**********************************************");

        CompanyFacade companyFacade = (CompanyFacade)couponSystem.login("CP", "zubur123", ClientType.COMPANY);
        System.out.println(companyFacade.getCompany());
    }
}
