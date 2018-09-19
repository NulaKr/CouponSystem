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

import java.util.Iterator;
import java.util.Set;

public class AdminFacade implements CouponClientFacade{


    /*
        DBDAO objects creation that will be required by most methods in the class
     */

    CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    CustomerDBDAO customerDBDAO = new CustomerDBDAO();
    CouponDBDAO couponDBDAO = new CouponDBDAO();

    public AdminFacade() {
    }

    /*
            Adding a new company to DB. If the Company name already exist the company will not be created and will throw a DAO exception
            The method is returning 1 in case of successful update
        */
    public int addCompany(Company company) throws DBConnectionException, DaoException {

        Set<Company> companies;
                {

            try {
                companies = companyDBDAO.getAllCompanies();
                Iterator it = companies.iterator();
                while (it.hasNext()){
                    Company existingCompany = (Company)it.next();
                    if (existingCompany.getCompName().equals(company.getCompName())){
                        throw new DaoException("Company name already exist");
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

    /*
        Updating existing company in DB. The method will return 1 if the entry was updated or 0 if not found or DB is empty
     */
    public int updateCompany(Company company) throws DaoException {

        if (company == null){
            throw new DaoException("some information missing. Can't update company");
        }
        try {
            companyDBDAO.updateCompany(company);
        } catch (DBConnectionException e) {
            e.getMessage();
            return 0;
        }
        return 1;
    }

    /*
        Removing company from the DB. The method will return 1 if the company was removed and 0 if the company not found or DB is empty
     */
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
            }
            companyDBDAO.deleteCompany(company);
        } catch (DBConnectionException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /*
    Getting a single company entry from DB by ID. most of the exceptions are thrown
    by the used method from the DBDAO method but is case of edge case not handled and the company is null this method will throw DAOException
     */
    public Company getCompanyByID(long companyId) throws CouponSystemException {

        Company company = null;
        company = companyDBDAO.getCompanyByID(companyId);

        if (company == null){
            throw new DaoException("Error getting data from server");
        }
        return company;
    }

    /*
  Getting a single company entry from DB by name. most of the exceptions are thrown
  by the used method from the DBDAO method but is case of edge case not handled and the company is null this method will throw DAOException
   */
    public Company getCompanyByName(String name) throws CouponSystemException {
        Company company = null;
        company = companyDBDAO.getCompanyByName(name);

        if (company == null){
            throw new DaoException("Error getting data from server");
        }
        return company;
    }

    /*
        Getting all company entries from DB by name. most of the exceptions are thrown
        by the used method from the DBDAO method but is case of edge case not handled and the company is null this method will throw DAOException
     */
    public Set<Company> getAllCompanies () throws DBConnectionException, DaoException {
        Set<Company> companies;
        companies = companyDBDAO.getAllCompanies();
        if (companies.isEmpty()){
            throw new DaoException("No companies found or an error occurred");
        }
        return companies;
    }

    /*
    Adding a new customer to DB. If the Customer name already exist the Customer will not be created and will throw a DAO exception
    The method is returning 1 in case of successful update
    */
    public int addCustomer(Customer customer) throws DaoException, DBConnectionException {

        Set<Customer> customers;
        {
            try {
                customers = customerDBDAO.getAllCustomers();
                Iterator it = customers.iterator();

                while (it.hasNext()){
                    Customer cust = (Customer)it.next();

                    if (cust.getCustName().equals(customer.getCustName())){
                        throw new DaoException("Customer already exist");
                    }
                }
            } catch (DBConnectionException e) {
                e.getMessage();
                return 0;
            } catch (DaoException e) {
                e.getMessage();
                return 0;
            }
        }

        if (customer == null) {
            throw new DaoException("Some information is missing");
        } else {
            customerDBDAO.createCustomer(customer);
            return 1;
        }
    }

    /*
        Removing customer from the DB. The method will return 1 if the company was removed and 0 if the customer not found or DB is empty
     */
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
                return 0;
            } catch (DaoException e) {
                e.getMessage();
                return 0;
            }
        }
        return 0;
    }

    /*
        Updating existing customer in DB. The method will return 1 if the entry was updated or 0 if not found or DB is empty
     */
    public int updateCustomer (Customer customer) throws DaoException {

        if (customer == null){
            throw new DaoException("Some information is missing. Could not update customer");
        } else {
            try {
                customerDBDAO.updateCustomer(customer);
                return 1;
            } catch (DBConnectionException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    /*
        Get customer by ID. if customer is null (not found) will throw exception
     */
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
    /*
        Get customer by name
     */
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

    public Set<Customer> getAllCustomers() throws DBConnectionException, DaoException {
        Set<Customer> customers = null;

        customers = customerDBDAO.getAllCustomers();

        if (customers.isEmpty()){
            throw new DaoException("No customers found or an error occurred");
        }

        return customers;
    }
}
