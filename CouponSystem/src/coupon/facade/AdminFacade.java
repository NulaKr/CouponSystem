package coupon.facade;

import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.beans.Customer;
import coupon.sys.dao.CompanyDBDAO;
import coupon.sys.dao.CouponDBDAO;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.util.Iterator;
import java.util.Set;

/**
 * Class that indicate the logged in user and provide access to relevant methods for a specific admin
 * @author hadar.kraus
 */
public class AdminFacade implements CouponClientFacade{


    /*
        DBDAO objects creation that will be required by most methods in the class
     */

    CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    CustomerDBDAO customerDBDAO = new CustomerDBDAO();
    CouponDBDAO couponDBDAO = new CouponDBDAO();

    public AdminFacade() {
    }

        /**
         * Adding a new company to DB.
         * @param company Company object
         * @return 1 for successful update
         * @return -1 for an error
        **/
    public int addCompany(Company company) throws DBConnectionException, DaoException {

        Set<Company> companies;
                {

            try {
                companies = companyDBDAO.getAllCompanies();
                Iterator it = companies.iterator();
                while (it.hasNext()){
                    Company existingCompany = (Company)it.next();
                    if (existingCompany.getCompName().equals(company.getCompName())){
                        return -1;
                    }
                }

            } catch (DBConnectionException e) {
                e.getMessage();
            } catch (DaoException e) {
                e.getMessage();
            }
                }
         if (company == null){
            throw new DaoException("some information missing. Can't create company");
        } else {
             companyDBDAO.createCompany(company);
             return 1;
         }
    }

    /**
     * Updating existing company in DB.
     * @param company Company object
     * @return 1 for successful update
     * @return -1 for error
     **/
    public int updateCompany(Company company) throws DaoException {

        if (company == null){
            throw new DaoException("some information missing. Can't update company");
        }
        try {
            companyDBDAO.updateCompany(company);
            return 1;
        } catch (DBConnectionException e) {
            e.getMessage();
        }
        return -1;
    }

    /**
     * Removing company and it's coupons from the DB.
     * @param company Company object
     * @return 1 if company was removed
     * @return -1 for errors
     **/
    public int removeCompany(Company company) throws DaoException {

        if (company == null) {
            throw new DaoException("some information missing. Can't remove company");
        }

        try {
            Set<Coupon> relatedCoupon = companyDBDAO.getCoupons(company.getId());
            Iterator it = relatedCoupon.iterator();
            while (it.hasNext()){
                Coupon coupon = (Coupon)it.next();
                couponDBDAO.removeCoupon(coupon);
                return 1;
            }
            companyDBDAO.deleteCompany(company);
        } catch (DBConnectionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Getting a single company entry from DB by ID.
     * @param companyId long
     * @return Company object
     **/
    public Company getCompanyByID(long companyId) throws CouponSystemException {

        Company company = null;
        company = companyDBDAO.getCompanyByID(companyId);

        if (company == null){
            throw new DaoException("Error getting data from server");
        }
        return company;
    }

    /**
     * Getting a single company entry from DB by name.
     * @param name String
     * @return Company object
   **/
    public Company getCompanyByName(String name) throws CouponSystemException {
        Company company = null;
        company = companyDBDAO.getCompanyByName(name);

        if (company == null){
            throw new DaoException("Error getting data from server");
        }
        return company;
    }

    /**
     * Getting all company entries from DB by name.
     * @return Set of companies
     **/
    public Set<Company> getAllCompanies () throws DBConnectionException, DaoException {
        Set<Company> companies;
        companies = companyDBDAO.getAllCompanies();
        if (companies.isEmpty()){
            throw new DaoException("No companies found or an error occurred");
        }
        return companies;
    }

    /**
    * Adding a new customer to DB.
     * @param customer Customer object
     * @return 1 if customer was added
     * @return -1 for errors
     **/
    public int addCustomer(Customer customer) throws DaoException, DBConnectionException {

        Set<Customer> customers;
        {
            try {
                customers = customerDBDAO.getAllCustomers();
                Iterator it = customers.iterator();

                while (it.hasNext()){
                    Customer cust = (Customer)it.next();

                    if (cust.getCustName().equals(customer.getCustName())){
                        return -1;
                    }
                }
            } catch (DBConnectionException e) {
                e.getMessage();
            } catch (DaoException e) {
                e.getMessage();
            }
        }

        if (customer == null) {
            return -1;
        } else {
            customerDBDAO.createCustomer(customer);
            return 1;
        }
    }

    /**
     * Removing customer from the DB.
     * @param customer Customer object
     * @return 1 if customer was removed
     * @return -1 for errors
     **/
    public int removeCustomer (Customer customer) {

        // make sure that the customer exist
        Set<Customer> customers;
        {
            try {
                customers = customerDBDAO.getAllCustomers();
                Iterator it = customers.iterator();

                if (customer == null) {
                    throw new DaoException("Some information is missing");
                }
                // only if customer exist use the remove method
                while (it.hasNext()) {

                    Customer cust = (Customer) it.next();

                    if (cust.getCustName().equals(customer.getCustName())) {
                        customerDBDAO.removeCustomer(customer);
                        return 1;
                    }
                }
            } catch (DBConnectionException e) {
                e.getMessage();
            } catch (DaoException e) {
                e.getMessage();
            }
        }
        return -1;
    }

    /**
     * Updating existing customer in DB.
     * @param customer Customer object
     * @return 1 for successful update
     * @return -1 for errors
     **/
    public int updateCustomer (Customer customer) throws DaoException {

        if (customer == null){
            throw new DaoException("Some information is missing. Could not update customer");
        } else {
            try {
                customerDBDAO.updateCustomer(customer);
                return 1;
            } catch (DBConnectionException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * Get customer by ID.
     * @param customerId long
     * @return Customer object
     **/
    public Customer getCustomerByID (long customerId) throws DaoException {

        Customer customer = null;
        try {
            customer = customerDBDAO.getCustomerByID(customerId);
            return customer;
        } catch (DBConnectionException e) {
            e.printStackTrace();
        }
        if (customer == null) {
            throw new DaoException("Could not find customer");
        }
        return customer;
    }
    /**
     * Get customer by name.
     * @param customerName String
     * @return Customer object
     **/
    public Customer getCustomerByName (String customerName) throws DaoException {

        Customer cust = null;
        try {
            cust = customerDBDAO.getCustomerByName(customerName);
        } catch (DBConnectionException e) {
            e.printStackTrace();
        }

        if (cust == null){
            throw new DaoException("Error getting data from server");
        }
        return cust;
    }

    /**
     * Get all customers from the DB.
     * @return Set of all customers in DB
     **/
    public Set<Customer> getAllCustomers() throws DBConnectionException, DaoException {
        Set<Customer> customers = null;

        customers = customerDBDAO.getAllCustomers();

        if (customers.isEmpty()){
            throw new DaoException("No customers found or an error occurred");
        }

        return customers;
    }

    /**
     * Login method for admins.
     * @param username String
     * @param password String
     * @return Admin Facade
     **/
    public static AdminFacade login(String username, String password) throws LoginException {
        if(username.equals("Admin") && password.equals("1234")){
            System.out.println("you are connected");
            return new AdminFacade();
        }else {
            throw new LoginException("Username or Password doesn't match");
        }
    }
}
