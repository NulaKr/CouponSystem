package coupon.sys.connection.pool;

import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.CouponSystemException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class ConnectionPool {


    private Set<Connection> connections = new HashSet<>();
    public static final int POOL_SIZE = 10;
    private String url = "jdbc:derby://localhost:1527/CouponSystem";


    private static ConnectionPool ourInstance;

    static {

        try {
            ourInstance = new ConnectionPool();
        } catch (CouponSystemException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionPool() throws CouponSystemException, ClassNotFoundException {

        //Fill the collection of connections with connections
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                Connection con = DriverManager.getConnection(url);
                connections.add(con);
            } catch (SQLException e) {
                throw new DBConnectionException("Got error while initializing the connectionPool", e);

            }
        }

    }

    // Get the singleton instance
    public static ConnectionPool getInstance() throws CouponSystemException {
        if(ourInstance == null){
            throw new DBConnectionException("Couldn't find instance");
        } else {
            return ourInstance;
        }
    }

    /*
     Get a single connection from the connection pool in order to run a query or update to the DB
      */
    public synchronized Connection getConnection() throws DBConnectionException {

        // If there are no available connections the thread will have to wait
        while (connections.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new DBConnectionException("Unexpected error", e);
            }
        }

        // When a connection is available return it and remove it from the collection
        Iterator<Connection> it = connections.iterator();
        Connection con = it.next();
        it.remove();
        return con;
    }

    /*
     When the use of the connection is done - return it to the collection
      */
    public synchronized void returnConnection(Connection con){
        connections.add(con);
        notifyAll();
    }

    /*
     close all open connection. In case there is a need to manually close it without the need to close the application
      */
    public synchronized void closeAllConnections() throws DBConnectionException {
        //can be done much better - only example!!!!

        for (Connection connection:connections){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DBConnectionException("Couldn't close the connection", e);
            }
        }
    }


}
