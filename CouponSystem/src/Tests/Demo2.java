package Tests;

import coupon.sys.beans.Company;
import coupon.sys.dao.CompanyDBDAO;
import coupon.sys.exceptions.CouponSystemException;

public class Demo2 {

    public static void main(String[] args) throws CouponSystemException {

        CompanyDBDAO companyDBDAO = new CompanyDBDAO();

        Company c1 = new Company("CP", "zubur123","test@cp.com");
        Company c2 = new Company("CR", "test123","test@cr.com");
        Company c3 = new Company("TS", "book123","test@ts.com");

        //test company creation
//        try {
//            companyDBDAO.createCompany(c1);
//            companyDBDAO.createCompany(c2);
//            companyDBDAO.createCompany(c3);
//        } catch (DBConnectionException e) {
//            e.printStackTrace();
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }

        //test getting company
        try {
//            Company get = companyDBDAO.getCompanyByID(2);
//            System.out.println(get);
//
//            companyDBDAO.deleteCompany(get);

        // test company update
            Company change = new Company(3, "TS", "changing123", "test@ts.com");
            companyDBDAO.updateCompany(new Company(3, "TS", "changing123", "test@ts.com"));

            //get all
            companyDBDAO.getCompanyByID(3);
            System.out.println("############################################");
            System.out.println(companyDBDAO.getAllCompanies());
        } catch (CouponSystemException e) {
            e.printStackTrace();
        }


    }
}
