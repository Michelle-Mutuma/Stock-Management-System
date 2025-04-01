package Controllers;

import java.sql.SQLException;

import Models.TransactionDAO;
import Services.SalesService;
public class TransactionController {
	
	private SalesService salesService;
	private TransactionDAO transactionDAO;
	
	public TransactionController() throws ClassNotFoundException, SQLException {
		salesService = new SalesService();
		transactionDAO = new TransactionDAO();
	}
	
	
	
	//CREATE Transaction
	public void createTransaction(int ItemId, String Type, int Quantity, String ReceiverType, int ReceiverId, int Discount){
		
		salesService.updateTransactionsAfterSale(ItemId, Quantity, ReceiverType, ReceiverId);
		
	}
	
	public void viewAllTransactions() {
		transactionDAO.viewTransactions();
	}
	
}
