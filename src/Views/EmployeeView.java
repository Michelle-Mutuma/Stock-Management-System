package Views;

//import java.util.List;
import java.util.Scanner;

import Controllers.EmployeeController;
import Models.Employee;

public class EmployeeView {
	private Scanner input;
	private EmployeeController employeeController;
	
	public EmployeeView() throws ClassNotFoundException{
		employeeController = new EmployeeController();
		input = new Scanner(System.in);
	}
	
	public void displayMenu()throws ClassNotFoundException, IllegalAccessException{
		System.out.println("Enter the number representing an option to chose it: ");
		System.out.println("1. Add an employee");
		System.out.println("2. View all employees");
		System.out.println("3. View specific employee");
		System.out.println("4. Remove an employee");
		int menuChoice = input.nextInt();

		switch(menuChoice) {
		case 1:
            System.out.println("Loading form...");
            registerEmployee();
			break;
		case 2:
			System.out.println("Getting employees...");
            employeeController.showAllEmployees();
            back();
			break;
        case 3:
            System.out.println("Enter employee's id:");
            String vieweeId = input.nextLine();
            System.out.println("Getting employee...");
            displayEmployeeProfile(vieweeId);
            break;
        case 4:
            System.out.println("Enter employee's id:");
            String deleteeId = input.nextLine();
			System.out.println("Processing removal...");
            employeeController.removeEmployee(deleteeId);
            back();
			break;
		default:
			System.out.println("Invalid option. Try again.");
            displayMenu();
			break;
		}
	}

    public void registerEmployee()throws ClassNotFoundException, IllegalAccessException{
        System.out.println("Fill out the following form to register as an employee");

		String role = "";
        while(true){
            System.out.println("Enter the number representing your role {1 - Stocker, 2 - Admin}:");
            role = input.nextLine();
            if(role.equals("1")){
                role = "stocker";
                break;
            }else if(role.equals("2")){
                role = "admin";
                break;
            }
            System.out.println("Invalid choice try again.");
        }

        System.out.println("Enter your full name, e.g, John Doe:");
        String fullName = input.nextLine();

        System.out.println("Enter your email, e.g, johndoe@email.com:");
		String email = input.nextLine();

        System.out.println("Enter your mobile phone number:");
		String phone_no = input.nextLine();

		String password = "";
		String password2 = "";
        while(true){
            System.out.println("Create a password:");
            password = input.nextLine();
            System.out.println("Confirm your password:");
            password2 = input.nextLine();
            if(password.equals(password2)){break;}
            System.out.println("Passwords must match. Try again.");
        }

        employeeController.addEmployee(fullName, role, email, phone_no, password);
        String id = employeeController.getEmployeeId(email);
        displayEmployeeProfile(id);
    }

    public void displayEmployeeProfile(String id) throws ClassNotFoundException, IllegalAccessException{
        Employee employee = employeeController.getEmployee(id);
        if(employee != null){
            System.out.println("  _____   ID: " + employee.getId());
            System.out.println(" /     \\  Name: " + employee.getName());
            System.out.println("| () () | Role: " + employee.getRole());
            System.out.println(" \\  ^  /  Email: " + employee.getEmail());
            System.out.println("  |||||   Phone number: " + employee.getPhone_no());
            System.out.println("Choose a profile option:");
            System.out.println("1 - Update profile");
            System.out.println("2 - Delete profile");
            System.out.println("Any other number - Just viewing");
            int ans = input.nextInt();
            switch (ans) {
                case 1:
                Employee changedEmployee = new Employee();
                    System.out.println("Enter a new value if you wish to change or 0 if not");
                    @SuppressWarnings("unused") String err = input.nextLine();
                    System.out.println("Name: ");
                    String name = input.nextLine();
                    if(!name.equals("0")){
                        changedEmployee.setName(name);
                    }                    
                    System.out.println("Email:");
                    String email = input.nextLine();
                    if(!email.equals("0")){
                        changedEmployee.setEmail(email);
                    }
                    System.out.println("Phone number:");
                    String phone_no = input.nextLine();
                    if(!phone_no.equals("0")){
                        changedEmployee.setPhone_no(phone_no);
                    }
                    changedEmployee.setId(employee.getId());
                    employeeController.changeEmployeeInfo(changedEmployee);
                break;
                case 2: 
                    employeeController.removeEmployee(employee.getId());
                    break;
                default:
                    System.out.println("Ok ig");
                    break;
            }
            back();
        }
    }

    public void back()throws ClassNotFoundException, IllegalAccessException{
        System.out.println("Enter 1 to return to menu or 2 to log out:");
        int ans = input.nextInt();
        if(ans == 1){
            displayMenu();
        }
    }
}
