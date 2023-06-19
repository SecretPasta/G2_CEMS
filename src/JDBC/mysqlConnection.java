/**
 * The `mysqlConnection` class provides methods for establishing a connection to a MySQL database.
 * It includes functionality for connecting to the database, retrieving the connection object, and handling exceptions.
 */
package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlConnection {
    /**
     * The connection object for the MySQL database.
     */
    public static Connection conn;

    /**
     * Establishes a connection to the MySQL database using the provided URL, username, and password.
     *
     * @param url      the URL of the database
     * @param user     the username for authentication
     * @param password the password for authentication
     * @return `true` if the connection is successful, `false` otherwise
     */
    @SuppressWarnings("deprecation")
    public static boolean connect(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeeded");
        } catch (Exception ex) {
            System.out.println("Driver definition failed");
            return false;
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("SQL connection succeeded");
            return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    /**
     * Retrieves the connection object for the MySQL database.
     *
     * @return the connection object
     * @throws SQLException           if an error occurs while accessing the database
     * @throws ClassNotFoundException if the MySQL JDBC driver is not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn != null && !conn.isClosed())
            return conn;
        return conn;
    }
}
