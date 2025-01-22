package Services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Models.Item;
import Models.ItemDAO;
import Models.Transaction;
import Models.TransactionDAO;

public class SalesService {
	
	private TransactionDAO transactionDAO;
	private ItemDAO itemDAO;
	private Transaction newTransaction;
	private double discount;
	private ArrayList <Item> itemsSold;

	
	public SalesService() throws ClassNotFoundException, SQLException {
		itemDAO = new ItemDAO();
		transactionDAO = new TransactionDAO();
	    itemsSold = new ArrayList<Item>();
		discount = 0;
	}

	
	public boolean isItemReadyForSale(int itemId, int quantity, String ReceiverType, int ReceiverId) throws ClassNotFoundException, SQLException {
		
		Item item;
		item = fetchItemInfo(itemId);
		boolean sellOrNot = isQuantityEnough(quantity, item);
	
		
		if(sellOrNot) {
			
			item.setQuantity(quantity);		
			itemsSold.add(setItemInfoAfterDiscount(item));
			updateItemAfterSale(itemId, quantity);
			updateTransactionsAfterSale(itemId, quantity, ReceiverType, ReceiverId);
			return true;
		}
		else {
			System.out.println("An item is out of stock!");
			return false;
		}
	}
	
	
	private Item fetchItemInfo(int itemId) {
		Item itemOnSale = itemDAO.viewItemById(itemId);
		
		return itemOnSale;
		
	}

	public boolean isQuantityEnough(int quantity, Item item) {
		
		return (item.getQuantity() >= quantity);
	}

	public void updateItemAfterSale(int itemId, int itemQuantity) {
		Item soldItem = new Item(itemId, itemQuantity);
		itemDAO.updateQuantityAfterSale(soldItem);
	}
	
	
	public void updateTransactionsAfterSale( int itemId, int itemQuantity, String receiverType, int receiverId) {
		
		LocalDate currentDate = LocalDate.now();
		discount = calculateDiscount(receiverId);
		
		
		newTransaction = new Transaction( itemId,  "out",  itemQuantity,  receiverType, currentDate,discount, receiverId );
		transactionDAO.createTransaction(newTransaction);
	}
	
	public double calculateDiscount(int customerId) {
		ArrayList <Transaction> customerTransactions = new ArrayList <Transaction>();
		
		customerTransactions = transactionDAO.viewTransactionById(customerId);
		
		int frequencyOfCustomer = customerTransactions.size();
		
		int tenthSale = frequencyOfCustomer % 10;
		
		double calculatedDiscount = 0;
		
		if(tenthSale == 0 && frequencyOfCustomer>0) {
			calculatedDiscount = 0.1;//10% discount
		}
		
		return calculatedDiscount;
	}
	
	private Item setItemInfoAfterDiscount(Item thisItem) {
		double CurrentPrice = thisItem.getPricePerUnit();
		
		double newDiscountedPrice = CurrentPrice - (CurrentPrice * discount);
		thisItem.setPricePerUnit(newDiscountedPrice);
		
		return thisItem;
		
	}
	
	public ArrayList <Receipt> prepareReceipt(ArrayList <Item> soldItems) throws ClassNotFoundException, SQLException {
		ArrayList <Receipt> receipt = new ArrayList<Receipt>();
		
		for(Item item : soldItems) {
			Receipt thisReceipt;
			
			double priceOfItem = item.getPricePerUnit();
			int quantity = item.getQuantity();
			String itemName = item.getName();
			double total = priceOfItem * quantity;
			
			thisReceipt = new Receipt(priceOfItem, quantity, itemName, total);
			receipt.add(thisReceipt);
		}
		
		return receipt;
	}
	
	public void printReceipt() throws ClassNotFoundException, SQLException {
		
		System.out.println("Printing receipt...");
		Receipt finalReceipt = new Receipt(0);//Initialize the receipt instance with a grandTotal of 0
		ArrayList<Receipt> preparedReceipts = prepareReceipt(itemsSold);
		finalReceipt.printReceipt(preparedReceipts);
	}
}
