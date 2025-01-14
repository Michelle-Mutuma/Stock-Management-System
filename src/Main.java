//import Controllers.ConectDb;
import Controllers.ItemController;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
    	
    	ItemController newStock = new ItemController();
    	
    	newStock.addStockItem(2, "Juice", "liquid", 1, 0.99);
    	
    }
}
