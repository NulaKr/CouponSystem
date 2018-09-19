package db_building;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

    public static void main(String[] args) {

        // This will be the DB in which the tables are stored
        String url = "jdbc:derby://localhost:1527/CouponSystem;create=true;";

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("connected");

            //The different table creation
            String createCompany = "CREATE TABLE Company(ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), compname VARCHAR(40), password VARCHAR(16), email VARCHAR(30))";
            String createCustomer = "CREATE TABLE Customer(ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), cust_name VARCHAR(30), password VARCHAR(16))";
            String createCoupon = "CREATE TABLE Coupon(ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), title VARCHAR(100), start_date DATE, end_date DATE, amount INT, type VARCHAR(30), message VARCHAR(1000), price FLOAT, image VARCHAR(40))";
            String createJoinCustCoupon = "CREATE TABLE Join_Customer_Coupon (cust_id BIGINT, coupon_id BIGINT, primary key(cust_id, coupon_id))";
            String createJoinCompCoupon = "CREATE TABLE Join_Company_Coupon (comp_id BIGINT, coupon_id BIGINT, primary key(comp_id, coupon_id))";

            // Create a statement
            Statement stmt = conn.createStatement();

            //execute the table creation
            stmt.executeUpdate(createCompany);
            stmt.executeUpdate(createCoupon);
            stmt.executeUpdate(createCustomer);
            stmt.executeUpdate(createJoinCompCoupon);
            stmt.executeUpdate(createJoinCustCoupon);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
