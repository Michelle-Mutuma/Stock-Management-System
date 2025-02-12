package Services;

import java.util.ArrayList;

public class Receipt {
private double priceOfItem;
private int quantity;
private String itemName;
private double subTotal;
private double grandTotal;

public Receipt(double GrandTotal) {
	this.grandTotal = GrandTotal;
}

public Receipt (double price, int Quantity, String nameOfItem, double SubTotal) {
	this.priceOfItem = price;
	this.quantity = Quantity;
	this.itemName = nameOfItem;
	this.subTotal = SubTotal;

}


public double getPrice() {
	return priceOfItem;
}
public String getItemName() {
	return itemName;
}
public int getQuantity() {
	return quantity;
}
public double getsubTotal() {
	return subTotal;
}


public void printReceipt(ArrayList<Receipt> receipts){

	System.out.println("THANK YOU FOR SHOPPING WITH US!!....");
	
	for(Receipt receipt : receipts) {
		System.out.printf("Item: %s \tQty:%d \tKsh:%.2f", receipt.getItemName(), receipt.getQuantity(),receipt.getsubTotal());
		this.grandTotal += receipt.getsubTotal();
		System.out.println();
	}
	
	System.out.println("Total \t Ksh:" + this.grandTotal);
}

}
