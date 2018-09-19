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

    private static CouponSystem ourInstance;

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
                if (username.equals("admin") && password.equals("1234")) {
                    return new AdminFacade();
                } else {
                    throw new LoginException("Wrong username or password");
                }

            case COMPANY:

                try {
                    Company company = companyDBDAO.getCompanyByName(username);

                    if (company.getCompName().equals(username) && company.getPassword().equals(password)) {
                        return new CompanyFacade(company);
                    } else {
                        throw new LoginException("Wrong username or password");
                    }
                } catch (DBConnectionException e) {
                    e.getMessage();
                } catch (DaoException e) {
                    e.getMessage();
                } catch (CouponSystemException e) {
                    e.getMessage();
                }

            case CUSTOMER:

//                try {

                    Customer customer = CustomerFacade.customerLogin(username, password);
                    if(customer != null){
                        return new CustomerFacade(customer);
                    }
                    throw new LoginException("Client type didn't match");

//                } catch (DBConnectionException e) {
//                    e.printStackTrace();
//                } catch (DaoException e) {
//                    e.printStackTrace();
//                }

        }


//        public void shutdown (); {
//            ConnectionPool pool;
//
//            {
//                try {
//                    pool = ConnectionPool.getInstance();
//                    pool.closeAllConnections();
//                    daily.stopTask();
//                } catch (CouponSystemException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        throw new LoginException("Client type didn't match");
    }
}
