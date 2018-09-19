package db_building;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AlterTable {
    public static void main(String[] args) {

        // This will be the DB in which the tables are stored
        String url = "jdbc:derby://localhost:1527/CouponSystem;create=true;";

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("connected");

            //The different table creation
            String pk = "ALTER TABLE Join_Company_Coupon DROP PRIMARY KEY";
            String pk1 = "ALTER TABLE Join_Customer_Coupon DROP PRIMARY KEY";

            // Create a statement
            Statement stmt = conn.createStatement();

            //execute the table creation
            stmt.executeUpdate(pk);
            stmt.executeUpdate(pk1);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
