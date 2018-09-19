package coupon.facade.Logic;

import coupon.ClientType;
import coupon.facade.*;
import coupon.sys.beans.Company;
import coupon.sys.beans.Customer;
import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.dao.CompanyDBDAO;
import coupon.sys.dao.CouponDBDAO;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

public class CouponSystem {

    // Create instances of DBDAO objects and create the daily thread
    DailyCouponExpirationTask daily = new DailyCouponExpirationTask();
    Thread dailyThread = new Thread(daily, "dailyTread");
    CouponDBDAO couponDBDAO = new CouponDBDAO();
    CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    CustomerDBDAO customerDBDAO = new CustomerDBDAO();

    private static CouponSystem ourInstance = new CouponSystem();

    public static CouponSystem getInstance() {
        return ourInstance;
    }

    private CouponSystem() {
        dailyThread.run();

    }

    public CouponClientFacade login(String username, String password, ClientType clientType) throws LoginException {
        CompanyDBDAO companyDBDAO = new CompanyDBDAO();
        CustomerDBDAO customerDBDAO = new CustomerDBDAO();

        switch (clientType) {
            case ADMIN:

                AdminFacade adminFacade = AdminFacade.login(username, password);
                return adminFacade;

            case COMPANY:

                CompanyFacade companyFacade = CompanyFacade.companyLogin(username, password);
                return companyFacade;

            case CUSTOMER:

                System.out.println(CustomerFacade.customerLogin(username,password));
                CustomerFacade customerFacade = CustomerFacade.customerLogin(username, password);
                return customerFacade;

        }

        throw new LoginException("Client type didn't match");
    }
}
