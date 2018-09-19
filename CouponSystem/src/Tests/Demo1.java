package Tests;

import coupon.sys.beans.Customer;
import coupon.sys.dao.CustomerDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;

import java.util.Iterator;
import java.util.Set;

public class Demo1 {

    public static void main(String[] args) throws DaoException, DBConnectionException {

        CustomerDBDAO customerDBDAO = new CustomerDBDAO();
        Customer c = new Customer("cybereason", "test123");


        // test customer creation
        try {
            customerDBDAO.createCustomer(new Customer("Avi", "zubur123"));
            customerDBDAO.createCustomer(new Customer("sniff", "stupidCat666"));
            customerDBDAO.createCustomer(new Customer("Tera", "fatCat666"));
        } catch (DBConnectionException e) {
            e.printStackTrace();
        }



        // test customer removal (with getCustomerByID)
//        Customer got  = customerDBDAO.getCustomerByID(1);
//
//        System.out.println(got);
//         customerDBDAO.removeCustomer(got);

        //test customer update
//         Customer change = new Customer(2, "Hadar", "testing123");
//         customerDBDAO.updateCustomer(change);

  //      Set<Customer> customers = customerDBDAO.getAllCustomers();
    //    Iterator it = customers.iterator();
      //  Customer c1;
        //while (it.hasNext()){
          //  c1 = (Customer) it.next();
            //customerDBDAO.removeCustomer(c1);
     //   }

    }

}
