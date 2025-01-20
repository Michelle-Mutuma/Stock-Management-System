package Models;

import java.time.LocalDate;

public class Transaction {
	private int		transactionId;
	private int     itemId;
	private String 	type;
	private int		quantity;
	private int 	receiverId;
	private String 	receiverType;
	private double 	discount;
	private LocalDate	currentDate;
	

	
	public Transaction( int ItemId, String Type, int Quantity, String ReceiverType, LocalDate date, double Discount, int ReceiverId ) {
		this.itemId        = ItemId;
		this.type          = Type;
		this.quantity	   = Quantity;
		this.currentDate   = date;
		this.receiverType  = ReceiverType;
		this.discount	   = Discount;
		this.receiverId    = ReceiverId;
		
	}
	
	public Transaction( int id, int ItemId, String Type, int Quantity, String ReceiverType, LocalDate date, double Discount, int ReceiverId ) {
		this.itemId        = ItemId;
		this.type          = Type;
		this.quantity	   = Quantity;
		this.currentDate   = date;
		this.receiverType  = ReceiverType;
		this.discount	   = Discount;
		this.receiverId    = ReceiverId;
		this.transactionId = id;
		
	}
	
	//getter and setters
	public void setItemId(int ItemId) {
		this.itemId = ItemId;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	
	public void setType(String Type) {
		this.type = Type;
	}
	
	public String getType() {
		return type;
	}

	
	public void setQuantity(int Quantity) {
		this.quantity = Quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setReceiverId(int ReceiverId) {
		this.receiverId = ReceiverId;
	}
	
	public int getReceiverId() {
		return receiverId;
	}
	
	
	public void setReceiverType(String ReceiverType) {
		this.receiverType = ReceiverType;
	}
	
	public String getReceiverType() {
		return receiverType;
	}
	
	
	public void setDiscount(int Discount) {
		this.discount = Discount;
	}
	
	public double getDiscount() {
		return discount;
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	
}
