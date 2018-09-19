package Tests;


import coupon.sys.beans.Coupon;
import coupon.sys.beans.CouponType;
import coupon.sys.beans.Customer;
import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;
import coupon.sys.exceptions.LoginException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CustomerDAOTests {

    public static void main(String[] args) throws DBConnectionException, DaoException, LoginException {

        CustomerDBDAO customerDBDAO = new CustomerDBDAO();

        //Test user creation
//        customerDBDAO.createCustomer(new Customer("Avi", "zubur12"));
//
        //Test user removal + get customer by ID
//        customerDBDAO.removeCustomer(customerDBDAO.getCustomerByID(113));
//
        //test user update acording to the user that was created above + get customer by name
//        Customer customer= customerDBDAO.getCustomerByName("Avi");
//        System.out.println(customer);
//        Customer newCustomer = new Customer(customer.getId(), customer.getCustName(), "zubur123");
//        customerDBDAO.updateCustomer(newCustomer);

        //Test get all customers
//        System.out.println(customerDBDAO.getAllCustomers());

        //Test get customer's coupons
//        System.out.println(customerDBDAO.getCoupons(107));

        //Test Login (first should return true. Second should return false)
//        System.out.println(customerDBDAO.login("Avi", "zubur123"));
//        System.out.println(customerDBDAO.login("Avi", "zubur12"));
    }
}