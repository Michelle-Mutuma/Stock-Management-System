package Views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Controllers.ItemController;
import Models.Item;

public class ItemView {

	private int ans;
	private Scanner input;
	private ItemController itemController;
	
	public ItemView() throws ClassNotFoundException, SQLException{
		itemController = new ItemController();
		input = new Scanner(System.in);
	}
	
	
	public void displayMenu() throws ClassNotFoundException, SQLException {
		System.out.println("Select a choice: ");
		System.out.println("1. View products");
		System.out.println("2. Add new Item: ");
		System.out.println("3. Sell Item ");
		
		
		ans = input.nextInt();
		
		switch(ans) {
		case 1:
			System.out.println("Fetching Items....");
			viewAllItems();
			break;
		case 2:
			System.out.println("Adding new item chill....");
			addItem();
			break;
		case 3:
			System.out.println("Item sold...");
			sellItem();
			break;
		default:
			System.out.println("Really!!");
			break;
		}
	}
	
	//add item view
	public void addItem() throws ClassNotFoundException, SQLException {
		
		itemController.createItem(3, "Spinach", "Vegetable", 10, 30.0);
		
	}
	
	
	//view all items view
	public void viewAllItems() {

		ArrayList<Item> items = itemController.getAllItems();
		
		if(items.isEmpty()) {
			System.out.println("No items mate!");
		}
		else {
			System.out.println("id  name   category  quantity  price ");
			for(Item item : items) {
				
				System.out.println(item.getId() + "  " +  item.getName() +  "  " + item.getCategory() + "  " + item.getQuantity() + " " + item.getPricePerUnit());
				
			}
		}
	}
	
	//Sell an item
	public void sellItem() throws SQLException {
		itemController.sellItem(2,1);
	}
	
}
