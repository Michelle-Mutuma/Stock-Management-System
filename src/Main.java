import CRUDops.ConectDb;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
    	
    	ConectDb connectdb = new ConectDb();
    	connectdb.connectToDb();
    	
    }
}
