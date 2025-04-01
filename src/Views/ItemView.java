package Views;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Scanner;

import Controllers.ItemController;
import Models.Item;
import Services.SalesService;

public class ItemView {

	private int ans;
	private Scanner input;
	private ItemController itemController;
	private SalesService salesService;
	
	public ItemView() throws ClassNotFoundException, SQLException{
		itemController = new ItemController();
		salesService = new SalesService();
		input = new Scanner(System.in);
	}
	
	
	public void displayMenu() throws ClassNotFoundException, SQLException{
		System.out.println("Select a choice: ");
		System.out.println("1. View products");
		System.out.println("2. Add new Item: ");
		System.out.println("3. Add existing Item: ");
		System.out.println("4. Sell Item ");
		
		
		ans = input.nextInt();
		
		executeAction(ans);
	}
	
	public void executeAction(int ans) throws ClassNotFoundException, SQLException {
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
			System.out.println("Updating item...");
			addStockItem();
			break;
		case 4:
			System.out.println("Trying to sell item...");
			sellItem();
			break;
		default:
			System.out.println("Really!!");
			break;
		}
	}
	
	private void addStockItem() throws SQLException {
		//parameters are itemId, and Item quantity
		itemController.reStockItem(2, 12);
		
	}


	
	public void addItem() throws ClassNotFoundException, SQLException {
		
		itemController.createItem(3, "Spinach", "Vegetable", 10, 30.0);
	}
	
	

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
	
	
	public void sellItem() throws SQLException, ClassNotFoundException {
		Item itemToBeSold = new Item(3,2);
		int customerId = 1;
		itemController.sellItem(itemToBeSold,customerId);
		
		
		
	}
	
}
