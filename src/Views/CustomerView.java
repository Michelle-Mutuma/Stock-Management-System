package Views;

import java.sql.SQLException;
//import java.util.List;
import java.util.Scanner;

import Controllers.CustomerController;
import Models.Customer;

public class CustomerView {
    private Scanner input;
    private CustomerController customerController;
    
    public CustomerView() throws ClassNotFoundException{
        customerController = new CustomerController();
        input = new Scanner(System.in);
    }
    
    public void displayMenu()throws ClassNotFoundException, IllegalAccessException, SQLException{
		System.out.println("Enter the number representing an option to chose it: ");
		System.out.println("1. Add an customer");
		System.out.println("2. View all customers");
		System.out.println("3. View specific customer");
		System.out.println("4. Remove an customer");
		int menuChoice = input.nextInt();

		switch(menuChoice) {
		case 1:
            System.out.println("Loading form...");
            registerCustomer();
			break;
		case 2:
			System.out.println("Getting customers...");
            customerController.showAllCustomers();
            back();
			break;
        case 3:
            System.out.println("Enter customer's id:");
            String vieweeId = input.nextLine();
            System.out.println("Getting customer...");
            displayCustomerProfile(vieweeId);
            break;
        case 4:
            System.out.println("Enter customer's id:");
            String deleteeId = input.nextLine();
			System.out.println("Processing removal...");
            customerController.removeCustomer(deleteeId);
            back();
			break;
		default:
			System.out.println("Invalid option. Try again.");
            displayMenu();
			break;
		}
	}

    public void registerCustomer()throws ClassNotFoundException, IllegalAccessException, SQLException{
        System.out.println("Fill out the following form to register as an customer");
        @SuppressWarnings("unused")
        String err = input.nextLine();

        System.out.println("Enter your full name, e.g, John Doe:");
        String fullName = input.nextLine();

        System.out.println("Enter your email, e.g, johndoe@email.com:");
		String email = input.nextLine();

        System.out.println("Enter your mobile phone number:");
		String phone_no = input.nextLine();

        customerController.addCustomer(fullName, email, phone_no);
        String id = customerController.getCustomerId(email);
        displayCustomerProfile(id);
    }

    public void displayCustomerProfile(String id) throws ClassNotFoundException, IllegalAccessException, SQLException{
        Customer customer = customerController.getCustomer(id);
        if(customer != null){
            System.out.println("  _____   ID: " + customer.getId());
            System.out.println(" /     \\  Name: " + customer.getName());
            System.out.println("| () () | Email: " + customer.getEmail());
            System.out.println(" \\  ^  /  Phone number: " + customer.getPhone_no());
            System.out.println("  |||||");
            System.out.println("Choose a profile option:");
            System.out.println("1 - Update profile");
            System.out.println("2 - Delete profile");
            System.out.println("Any other number - Just viewing");
            int ans = input.nextInt();
            switch (ans) {
                case 1:
                Customer changedCustomer = new Customer();
                    System.out.println("Enter a new value if you wish to change or 0 if not");
                    @SuppressWarnings("unused") String err = input.nextLine();
                    System.out.println("Name: ");
                    String name = input.nextLine();
                    if(!name.equals("0")){
                        changedCustomer.setName(name);
                    }                    
                    System.out.println("Email:");
                    String email = input.nextLine();
                    if(!email.equals("0")){
                        changedCustomer.setEmail(email);
                    }
                    System.out.println("Phone number:");
                    String phone_no = input.nextLine();
                    if(!phone_no.equals("0")){
                        changedCustomer.setPhone_no(phone_no);
                    }
                    changedCustomer.setId(customer.getId());
                    customerController.changeCustomerInfo(changedCustomer);
                break;
                case 2: 
                    customerController.removeCustomer(customer.getId());
                    break;
                default:
                    System.out.println("Ok ig");
                    break;
            }
            back();
        }
    }

    public void back()throws ClassNotFoundException, IllegalAccessException, SQLException{
        System.out.println("Enter 1 to return to menu or 2 to log out:");
        int ans = input.nextInt();
        if(ans == 1){
            displayMenu();
        }
    }
}
