package connectSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class connect {
	 public static Connection Database() throws ClassNotFoundException {
	        Connection conn = null;
	        
	        try {
	        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=bar_store";
	            String user = "sa";
	            String pass = "1234$";
	            conn = DriverManager.getConnection(dbURL, user, pass);
	        } catch (SQLException ex) {
	        	JOptionPane.showMessageDialog(null, ""+ex);
	        }
			return conn; 
	    }
}
