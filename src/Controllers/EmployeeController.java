package Controllers;

import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Field;

import Models.EmployeeDAO;
import Models.Employee;

public class EmployeeController {
    Scanner input = new Scanner(System.in);
    private EmployeeDAO employeeDao;
    
    public EmployeeController(){
        employeeDao = new EmployeeDAO();
    }
    
    //Add an employee
	public void addEmployee(String fullName, String role, String email, String phone_no, String password){
		List<Employee> employees = employeeDao.readAllEmployees();
        for (Employee emp : employees) {//ensures the new employee's contact info is not the same as another employees
            if(emp.getEmail().equals(email)){
                System.out.println("Your email is already in the system please use another");
                System.out.println("Enter your email:");
                email = input.nextLine();
                addEmployee(fullName, role, email, phone_no, password);
            } else if (emp.getPhone_no().equals(phone_no)) {
                System.out.println("Your phone number is already in the system please use another");
                System.out.println("Enter your mobile phone number:");
		        phone_no = input.nextLine();
                addEmployee(fullName, role, email, phone_no, password);
            }
        }
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$";
        if(password.matches(regex)){
            Employee employee = new Employee(fullName, role, email, phone_no, password);
            employeeDao.createEmployee(employee);
        } else {
            System.out.println("Your password must be at least 7 characters long and use at least 1 number, capital letter and special character.Try again.");
            password = "";
            String password2 = "";
            while(true){
                System.out.println("Create a password:");
                password = input.nextLine();
                System.out.println("Confirm your password:");
                password2 = input.nextLine();
                if(password.equals(password2)){break;}
                System.out.println("Passwords must match. Try again.");
            }
            addEmployee(fullName, role, email, phone_no, password);
        }
	}
	
	//Get an employee by id
	public Employee getEmployee(int id){
		return employeeDao.readEmployee(id);
	}

    //Get an employee's id using their email
	public int getEmployeeId(String email){
        List<Employee> employees =  employeeDao.readAllEmployees();
        for(Employee employee:employees){
            if(employee.getEmail().equals(email)){
                return employee.getId();
            }
        }
        return -1;
	}

    //Get all employees
	public void showAllEmployees(){
		List<Employee> employees =  employeeDao.readAllEmployees();
        for(Employee employee:employees){
            employee.displayEmployee();
        }
	}
	
	//change an employee's info
	public void changeEmployeeInfo(Employee emp) throws IllegalAccessException {
        Employee employeeFromDb = employeeDao.readEmployee(emp.getId());
        employeeFromDb.displayEmployee();
        Employee updatedEmployee = new Employee();
        Field[] fields = Employee.class.getDeclaredFields();
    
        for (Field field : fields) {
            field.setAccessible(true);
            Object empValue = field.get(employeeFromDb);
            Object newValue = field.get(emp);
    
            if (field.getName().equals("id")) {
                field.set(updatedEmployee, emp.getId());
                System.out.println(newValue);
            } else if (newValue != null && !newValue.equals(empValue)) {
                field.set(updatedEmployee, newValue);
                System.out.println(newValue);
            }
        }
        employeeDao.updateEmployee(updatedEmployee);
    }
    

    //remove an employee
	public void removeEmployee(int deleteeId){
        System.out.println("Are you sure you want to remove this employee?(yes/no)");
        String answer = input.nextLine();
        if(answer.equals("yes")){
            employeeDao.deleteEmployee(deleteeId);
        }else if(answer.equals("no")){
            System.out.println("Employee not removed");
        }else{
            System.out.println("Invalid input");
            removeEmployee(deleteeId);
        }
	}
}
