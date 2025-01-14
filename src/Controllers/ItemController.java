package Controllers;
import Models.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemController {

    // CREATE: Add a new stock item to the database
    public void addStockItem(int id, String name,String category, int quantity, double price_per_unit) throws ClassNotFoundException {

    	
    	
    	String sql = "INSERT INTO items (id, name, category, quantity, price_per_unit) VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = Controllers.ConectDb.connectToDb();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
        	   stmt.setInt(1, id);
        	   stmt.setString(2, name);
               stmt.setString(3, category); 
               stmt.setInt(4, quantity);
               stmt.setDouble(5, price_per_unit);
               stmt.executeUpdate();
               System.out.println("Stock item added successfully.");
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
