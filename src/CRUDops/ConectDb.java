package CRUDops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectDb {

	
    //Database URL, username, and password
	
    public void connectToDb() throws ClassNotFoundException{
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	
    	String url = "jdbc:mysql://127.0.0.1:3306/stockDb";
        String user = "root"; 
        String password = "";
        


        try {
            // Establishing a connection
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful!");
            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
