package coupon.sys.dao;

import coupon.sys.beans.Coupon;
import coupon.sys.beans.Customer;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.util.Set;

public interface CustomerDAO {

    void createCustomer(Customer customer) throws DBConnectionException, DaoException;
    void removeCustomer (Customer customer) throws DBConnectionException, DaoException;
    void updateCustomer (Customer customer) throws DBConnectionException, DaoException;


    Customer getCustomerByID(long CustomerId) throws DBConnectionException, DaoException;
    Set<Customer> getAllCustomers() throws DBConnectionException, DaoException;
    Set<Coupon> getCoupons(long customerId) throws DBConnectionException, DaoException;
    boolean login(String compName, String password) throws DBConnectionException, LoginException;

}
