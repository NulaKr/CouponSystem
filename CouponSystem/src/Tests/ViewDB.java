package Tests;

import coupon.sys.connection.pool.ConnectionPool;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;

import java.sql.*;

public class ViewDB {

    public static void main(String[] args) throws DBConnectionException {


        ConnectionPool pool;
        Connection conn;

        {
            try {
                pool = ConnectionPool.getInstance();
                conn = pool.getConnection();


                String selectCompany = "SELECT * from Company";
                String selectCustomer = "SELECT * from Customer";
                String selectCoupon = "SELECT * from Coupon";
                String selectCustCoupon = "SELECT * from Join_Customer_Coupon";
                String selectCompCoupon = "SELECT * from Join_Company_Coupon";

                Statement stmt = conn.createStatement();

                ResultSet rs1 = stmt.executeQuery(selectCompany);
                ResultSetMetaData rsmd1 = rs1.getMetaData();

                ResultSet rs2 = stmt.executeQuery(selectCustomer);
                ResultSetMetaData rsmd2 = rs2.getMetaData();

                ResultSet rs3 = stmt.executeQuery(selectCoupon);
                ResultSetMetaData rsmd3 = rs3.getMetaData();

                ResultSet rs4 = stmt.executeQuery(selectCustCoupon);
                ResultSetMetaData rsmd4 = rs4.getMetaData();

                ResultSet rs5 = stmt.executeQuery(selectCompCoupon);
                ResultSetMetaData rsmd5 = rs5.getMetaData();


                System.out.println("Company:");
                System.out.println(rsmd1.getColumnLabel(1) + " " + rsmd1.getColumnLabel(2) + " " + rsmd1.getColumnLabel(3) + " " + rsmd1.getColumnLabel(4));
                Statement statement1 = conn.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(selectCompany);
                while (resultSet1.next()) {
                    System.out.println(resultSet1.getInt("ID") + " " + resultSet1.getString("COMPNAME") + " " + resultSet1.getString("PASSWORD") + " " + resultSet1.getString("EMAIL"));
                }

                System.out.println("##################################################################");
                System.out.println("Customer:");
                System.out.println(rsmd2.getColumnLabel(1) + " " + rsmd2.getColumnLabel(2) + " " + rsmd2.getColumnLabel(3));
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(selectCustomer);

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("ID") + " " + resultSet.getString("CUST_NAME") + " " + resultSet.getString("PASSWORD"));
                }
                System.out.println("##################################################################");
                System.out.println("Coupon:");
                System.out.println(rsmd3.getColumnLabel(1) + "\t\t" + rsmd3.getColumnLabel(2) + "\t\t" + rsmd3.getColumnLabel(3) + "\t\t" + rsmd3.getColumnLabel(4) +
                        "\t\t" + rsmd3.getColumnLabel(5) + "\t\t" + rsmd3.getColumnLabel(6) + "\t\t" + rsmd3.getColumnLabel(7) + "\t\t" + rsmd3.getColumnLabel(8) +
                        " " + rsmd3.getColumnLabel(9));

                ResultSet resultSet2 = statement.executeQuery(selectCoupon);

                while (resultSet2.next()) {
                    System.out.println(resultSet2.getInt(1) + " "
                            + resultSet2.getString(2) + "\t\t"
                            + resultSet2.getDate(3) + "\t\t"
                            + resultSet2.getDate(4) + "\t\t"
                            + resultSet2.getInt(5) + "\t\t"
                            + resultSet2.getString(6) + "\t\t"
                            + resultSet2.getString(7) + "\t\t"
                            + resultSet2.getDouble(8) + "\t\t"
                            + resultSet2.getString(9));
                }
                System.out.println("##################################################################");
                System.out.println("Customer-coupon:");
                System.out.println(rsmd4.getColumnLabel(1) + " " + rsmd4.getColumnLabel(2));
                ResultSet resultSet3 = statement.executeQuery(selectCustCoupon);

                while (resultSet3.next()){
                    System.out.println(resultSet3.getLong(1) + " " + resultSet3.getLong(2));
                }
                System.out.println("##################################################################");
                System.out.println("Company-coupon:");
                System.out.println(rsmd5.getColumnLabel(1) + " " + rsmd5.getColumnLabel(2));

                ResultSet resultSet4 = statement.executeQuery(selectCustCoupon);

                while (resultSet4.next()){
                    System.out.println(resultSet4.getLong(1) + " " + resultSet4.getLong(2));
                }

                pool.returnConnection(conn);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (CouponSystemException e) {
                e.printStackTrace();
            }
        }
    }
}
