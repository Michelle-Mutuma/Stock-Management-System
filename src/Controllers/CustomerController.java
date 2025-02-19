package Controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Field;

import Models.CustomerDAO;
import Models.Customer;

public class CustomerController {
    Scanner input = new Scanner((System.in));
    private CustomerDAO customerDao;
	
    public CustomerController(){}
    
	//Add a customer
	public void addCustomer(String fullName, String email, String phone_no){
		List<Customer> customers = customerDao.readAllCustomers();
        for (Customer customer : customers) {//ensures the new employee's contact info is not the same as another employees
            if(customer.getEmail().equals(email)){
                System.out.println("Your email is already in the system please use another");
                System.out.println("Enter your email:");
                email = input.nextLine();
                addCustomer(fullName, email, phone_no);
            } else if (customer.getPhone_no().equals(phone_no)) {
                System.out.println("Your phone number is already in the system please use another");
                System.out.println("Enter your mobile phone number:");
		        phone_no = input.nextLine();
                addCustomer(fullName, email, phone_no);
            }
        }
        Customer customer = new Customer(fullName, email, phone_no);
        customerDao.createCustomer(customer);
	}
	
	//Get an customer by id
	public Customer getCustomer(String id){
		return customerDao.readCustomer(id);
	}

    //Get an employee's id using their email
	public String getCustomerId(String email){
        List<Customer> customers =  customerDao.readAllCustomers();
        for(Customer customer:customers){
            if(customer.getEmail().equals(email)){
                return customer.getId();
            }
        }
        return null;
	}

    //Get all Customers
	public void showAllCustomers(){
		List<Customer> customers =  customerDao.readAllCustomers();
        for(Customer customer:customers){
            customer.displayCustomer();
        }
	}
	
	//change an customer's info
	public void changeCustomerInfo(Customer cust) throws SQLException, IllegalAccessException {
        Customer customerFromDb = customerDao.readCustomer(cust.getId());
        Customer updatedCustomer = new Customer();
        Field[] fields = Customer.class.getDeclaredFields();
    
        for (Field field : fields) {
            field.setAccessible(true);
            Object empValue = field.get(customerFromDb);
            Object newValue = field.get(cust);
    
            if (newValue != null && !newValue.equals(empValue)) {
                field.set(updatedCustomer, newValue);
            }
        }
        customerDao.updateCustomer(updatedCustomer);
        System.out.println("Customer's details changed successfully");
    }
    

    //remove an Customer
	public void removeCustomer(String id) throws SQLException {
        System.out.println("Are you sure you want to remove this customer?(yes/no)");
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine();
        if(answer == "yes"){
            customerDao.deleteCustomer(id);
            System.out.println("Customer removed successfully");
        }else if(answer == "no"){
            System.out.println("Customer not removed");
        }else{
            System.out.println("Invalid input");
            removeCustomer(id);
        }
        input.close();
	}
}


