package Models;

import Utils.ConnectDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ItemDAO {
	
	private Connection connection;

	
	public ItemDAO() throws ClassNotFoundException, SQLException {
		connection = ConnectDb.connectToDb();
		
	}
	
	// CREATE: Add a new stock item to the database
    public void addStockItem(Item newItem) throws ClassNotFoundException {

    	String sql = "INSERT INTO Items (id, name, category, quantity, price) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        	
        	   stmt.setInt(1, newItem.getId());
        	   stmt.setString(2, newItem.getName());
               stmt.setString(3, newItem.getCategory()); 
               stmt.setInt(4, newItem.getQuantity());
               stmt.setDouble(5, newItem.getPricePerUnit());
               
               stmt.executeUpdate();
               System.out.println("Stock item added successfully.");
               
               System.out.println("id: " + newItem.getId());
               System.out.println("Name: " + newItem.getName());
               System.out.println("Category: " + newItem.getCategory());
               System.out.println("Price per Unit: " + newItem.getPricePerUnit());
               
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
    
    //READ all Items
    public ArrayList <Item> getAllItems(){
    	ArrayList <Item> items = new ArrayList<Item>();
    	
    	String sql = "SELECT * FROM Items;";
    	
    	try(Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
    		
    		while(rs.next()) {
    			Item item = new Item(
    					rs.getInt("id"),
    					rs.getString("name"),
    					rs.getString("category"),
    					rs.getInt("quantity"),
    					rs.getDouble("price")
    					);
    			
    			items.add(item);
    		} 
    	}catch(SQLException e) {
			e.printStackTrace();
		}
    	
    	return items;
    }
    
    
    //READ item by id
    
    public Item viewItemById(int itemId) {
    	String sql = "SELECT * FROM Items WHERE id= ?;";
    	Item item = null;
    	
    	try(PreparedStatement stmt = connection.prepareStatement(sql);){
    		
    		stmt.setInt(1, itemId);
    		ResultSet rs = stmt.executeQuery();
    		
    		while(rs.next()) {
    			 item = new Item(
    					rs.getInt("id"),
    					rs.getString("name"),
    					rs.getString("category"),
    					rs.getInt("quantity"),
    					rs.getDouble("price")
    					);
    			
    		}
    		
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return item;
    }
    
    //UPDATE Item after sale
    
    public void updateQuantityAfterSale(Item soldItem){
    	String sql = "UPDATE Items SET quantity = quantity-? WHERE id=? AND quantity>=?;";
    	
    	try(PreparedStatement stmt = connection.prepareStatement(sql);){
    		stmt.setInt(1, soldItem.getQuantity());
    		stmt.setInt(2, soldItem.getId());
    		stmt.setInt(3, soldItem.getQuantity());
    		
    		int rowsAffected = stmt.executeUpdate();
    		
    		if(rowsAffected > 0) {
    			System.out.println("Item: " + soldItem.getId() + " sold successfully...");
    		}
    		else {
    			System.out.println("Not enough stock to complete this sale");
    		}
    	} catch(SQLException e) {
			e.printStackTrace();
		}
    }
    
    
}
