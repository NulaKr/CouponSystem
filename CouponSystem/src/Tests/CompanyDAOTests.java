package Tests;

import coupon.sys.beans.Company;
import coupon.sys.beans.Coupon;
import coupon.sys.dao.CompanyDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;

import java.util.Iterator;
import java.util.Set;

public class CompanyDAOTests {

    public static void main(String[] args) throws CouponSystemException {
        CompanyDBDAO companyDBDAO = new CompanyDBDAO();

        Company company = new Company("OneCompany", "password", "test@test.com");

        //Test company creation
//        companyDBDAO.createCompany(company);

        //Test company removal + get company by name
//        companyDBDAO.deleteCompany(companyDBDAO.getCompanyByName("OneCompany"));

        //Test Company update + get company by ID

//        Company company1 = companyDBDAO.getCompanyByID(3);
//        System.out.println(company1);
//        Company company2 = new Company(company1.getId(), company1.getCompName(), "password1", company1.getEmail());
//        companyDBDAO.updateCompany(company2);
//        System.out.println(companyDBDAO.getCompanyByName("OneCompany"));

//        System.out.println(companyDBDAO.getAllCompanies());

        //Test login
//        System.out.println(companyDBDAO.login("OneCompany","password"));

        // Test get coupons

        Set<Coupon> coupons = companyDBDAO.getCoupons(1);
        Iterator it = coupons.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}