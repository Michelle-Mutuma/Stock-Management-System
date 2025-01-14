package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectDb {

	
    //Database URL, username, and password
	
    public static Connection connectToDb() throws ClassNotFoundException, SQLException{
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	
    	String url = "jdbc:mysql://127.0.0.1:3306/stockDb";
        String user = "root"; 
        String password = "";
        

        return DriverManager.getConnection(url, user, password);
  
        
    }

}
