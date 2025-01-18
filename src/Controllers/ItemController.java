package Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import Models.Item;
import Models.ItemDAO;

public class ItemController {

	private ItemDAO itemDAO;
	
	public ItemController() throws ClassNotFoundException, SQLException {
		itemDAO = new ItemDAO();
	}
    
	
	//CREATE new Item
	public void createItem(int id, String name,String category, int quantity,double price_per_unit) throws ClassNotFoundException {
		Item newItem = new Item(id, name, category, quantity, price_per_unit);
		itemDAO.addStockItem(newItem);
	}
	
	
	//READ all Items
	public ArrayList<Item> getAllItems(){
		return itemDAO.getAllItems();
	}
	
	
	//UPDATE Items after sale
	public void sellItem(int id, int quantity) throws SQLException {
		Item soldItem = new Item(id, quantity);
		itemDAO.updateQuantityAfterSale(soldItem);
	}
	
	
   }
