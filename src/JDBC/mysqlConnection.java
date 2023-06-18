package JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlConnection {
	public static Connection conn;
	
	@SuppressWarnings("deprecation")
	public static boolean connect(String url, String user, String password) {
		try
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 //System.out.println("Driver definition failed");
        	 return false;
        	 }
        
        try 
        {
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("SQL connection succeed");
            return true;
            
            // 172.20.10.3
     	} catch (SQLException ex) 
     	    {/* handle any errors*/  		
            //System.out.println("SQLException: " + ex.getMessage());
            //System.out.println("SQLState: " + ex.getSQLState());
            //System.out.println("VendorError: " + ex.getErrorCode());
            return false;
            }
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn != null && !conn.isClosed())
            return conn;
        return conn;
    }
}

