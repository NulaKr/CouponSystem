package coupon.facade.Logic;

import coupon.ClientType;
import coupon.facade.*;
import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.dao.CompanyDBDAO;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.LoginException;

/**
 * @author hadar.kraus
 */
public class CouponSystem {

    // Create the daily thread
    private DailyCouponExpirationTask daily = new DailyCouponExpirationTask();
    private Thread dailyThread = new Thread(daily, "dailyTread");


    private ConnectionPool pool;

    {


        try {
            pool = ConnectionPool.getInstance();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }

    }


    private static CouponSystem ourInstance = new CouponSystem();

    /**
    * Create single instance
     **/
    public static CouponSystem getInstance() {
        return ourInstance;
    }

    private CouponSystem() {
        dailyThread.run();

    }

    /**
    Login method for all client types. Uses the login method of the Facade classes according to the client type (switch case)
    Required parameters - Username (String), Password (String), ClientType (ClientType ENUM)
     **/
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

    /**
    Will shut down the system - Close the cleanup thread and return all connections
     **/
    public void shutdown(){
        try {
            daily.stopTask();
            pool.closeAllConnections();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }
    }
}
