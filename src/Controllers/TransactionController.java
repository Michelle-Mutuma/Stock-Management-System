package Controllers;

import java.sql.SQLException;

import Services.SalesService;
public class TransactionController {
	
	private SalesService salesService;
	
	public TransactionController() throws ClassNotFoundException, SQLException {
		salesService = new SalesService();
	}
	
	
	
	//CREATE Transaction
	public void createTransaction(int ItemId, String Type, int Quantity, String ReceiverType, int ReceiverId, int Discount){
		
		salesService.updateTransactionsAfterSale(ItemId, Quantity, ReceiverType, ReceiverId);
		
	}
	
}
