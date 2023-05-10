package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class mysqlConnection {

	public static void main(String[] args) {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) { 
        	System.out.println("Driver definition failed");
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test/world?serverTimezone=IST","root","Aa123456");
            System.out.println("SQL connection succeed");
     	} catch (SQLException ex) {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
   	}
}


