package Models;

public class Item {
	
	//Item model
	private int id;
	private String name;
	private String category;
	private int quantity;
	private double pricePerUnit;
	
	public Item(int id, String name, String category, int quantity, double pricePerUnit) {
		this.id = id;
		this.name = name;
		this.category=category;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
	}

	//getters and Setters
	
	//getters//
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCategory(){
		return category;
	}
	
	public double getPricePerUnit(){
		return pricePerUnit;
	}
	public int getQuantity() {
		return quantity;
	}
	
	
	//setters//
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
}