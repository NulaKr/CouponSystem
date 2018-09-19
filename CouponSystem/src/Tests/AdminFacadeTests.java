package Tests;

import coupon.facade.AdminFacade;
import coupon.sys.beans.Company;
import coupon.sys.beans.Customer;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;

import java.util.Iterator;
import java.util.Set;

public class AdminFacadeTests {

    public static void main(String[] args) throws CouponSystemException {
        AdminFacade adminFacade = new AdminFacade();

        //Test all Get methods
//        Set<Company> companies = adminFacade.getAllCompanies();
//        Iterator it = companies.iterator();
//        while (it.hasNext()){
//            System.out.println(it.next());
//        }
//
//        System.out.println("**********************************************");
//
//        Set<Customer> customers = adminFacade.getAllCustomers();
//        Iterator it1 = customers.iterator();
//        while (it1.hasNext()){
//            System.out.println(it1.next());
//        }
//
//        System.out.println("**********************************************");
//
//        System.out.println(adminFacade.getCompanyByName("TS"));
//
//        System.out.println("**********************************************");
//
//        System.out.println(adminFacade.getCustomerByName("Avi"));
//
//        System.out.println("**********************************************");
//
//        System.out.println(adminFacade.getCompanyByID(1));
//
//        System.out.println("**********************************************");
//
//        System.out.println(adminFacade.getCustomerByID(107));

        //Test for adding customer/company:
//        Company company = new Company("Here", "WeRHere", "here@here.com");
//        adminFacade.addCompany(company);
//
//        Customer customer = new Customer("Hadar", "Pa$$w0rd");
//        adminFacade.addCustomer(customer);


        //Tests for removing customer/company:

//        adminFacade.removeCompany(adminFacade.getCompanyByName("Here"));
//        adminFacade.removeCustomer(adminFacade.getCustomerByName("Hadar"));

        //Test for updating cutomer/company

//        Company company1 = adminFacade.getCompanyByName("CP");
//        Company company2 = new Company(company1.getId(), company1.getCompName(), company1.getPassword(), "newEmail@email.com");
//        adminFacade.updateCompany(company2);
//        adminFacade.getCompanyByName("CP");
//
//        Customer customer1 = adminFacade.getCustomerByName("Avi");
//        Customer customer2 = new Customer(customer1.getId(), customer1.getCustName(), "NewPass");
//        adminFacade.updateCustomer(customer2);
//        System.out.println(adminFacade.getCustomerByName("Avi"));

        // Test for login of admin
//        adminFacade.login("test", "Wronge");
        adminFacade.login("Admin", "1234");
    }


}
