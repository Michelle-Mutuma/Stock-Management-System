import java.sql.SQLException;
//import java.util.Scanner;

import Views.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException{
    	/* 
    	ItemView itemView = new ItemView();
    	itemView.displayMenu();
    	*/
		EmployeeView view = new EmployeeView();
		view.displayMenu();
    }
}
