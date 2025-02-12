package Models;

import java.util.ArrayList;
import java.util.List;
//import java.sql.*;
import java.lang.reflect.Field;

//import Utils.ConnectDb;

public class EmployeeDAO {
    private List<Employee> employees = new ArrayList<>();
    
    public EmployeeDAO(){
        employees.add(new Employee("1", "Alice Johnson", "admin", "alice.johnson@example.com", "1234567890", "password1"));
        employees.add(new Employee("2", "Bob Smith", "stocker", "bob.smith@example.com", "0987654321", "password2"));
        employees.add(new Employee("3", "Charlie Brown", "admin", "charlie.brown@example.com", "1122334455", "password3"));
        employees.add(new Employee("4", "David Williams", "stocker", "david.williams@example.com", "2233445566", "password4"));
        employees.add(new Employee("5", "Emma Davis", "admin", "emma.davis@example.com", "3344556677", "password5"));
        employees.add(new Employee("6", "Frank Miller", "stocker", "frank.miller@example.com", "4455667788", "password6"));
        employees.add(new Employee("7", "Grace Lee", "admin", "grace.lee@example.com", "5566778899", "password7"));
        employees.add(new Employee("8", "Hannah Wilson", "stocker", "hannah.wilson@example.com", "6677889900", "password8"));
        employees.add(new Employee("9", "Ian Martin", "admin", "ian.martin@example.com", "7788990011", "password9"));
        employees.add(new Employee("10", "Jane Taylor", "stocker", "jane.taylor@example.com", "8899001122", "password10"));

    }

    // Create a new employee
    public void createEmployee(Employee emp) {
        if (emp != null){
            int newId = 1;
            newId += Integer.valueOf(employees.get(employees.size()-1).getId());
            emp.setId(Integer.toString(newId));
            employees.add(emp);
            System.out.println("Employee created successfully");
        } else {
            System.out.println("Employee not found to be created");
        }
        /*
        Field[] fields = Employee.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "INSERT INTO employee (";
            String values = "VALUES (";
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.get(emp) == null || field.getName().equals("id")) { continue; }
                sql += field.getName() + ",";
                values += "?,";
            }
            sql = sql.substring(0, sql.length() - 1); // Remove the last comma
            values = values.substring(0, values.length() - 1); // Remove the last comma
            sql += ") " + values +")";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int index = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    if(field.get(emp) == null || field.getName().equals("id")) { continue; }
                    pstmt.setObject(index++, field.get(employee));
                }
                pstmt.executeUpdate();
                System.out.println("New employee created successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        */
    }

    // Read an employee by id
    public Employee readEmployee(String id) {
        boolean employeeFound = false;
        for(Employee employee:employees){
            if(employee.getId().equals(id)){
                employeeFound = true;
                return employee;
            }
        }
        if(!employeeFound){
            System.out.println("Employee not found");
        }
        return null;
        /*
        Employee employeeFromDb;
        Field[] fields = Employee.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM employee WHERE id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        employeeFromDb = new Employee();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            Object value = rs.getObject(field.getName());
                            field.set(employeeFromDb, value);
                            System.out.println(field.getName() + ": " + (value != null ? value : "null"));
                        }
                        return employeeFromDb;
                    } else {
                        System.out.println("Employee not found.");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
        */
    }

    // Read all employees
    public List<Employee> readAllEmployees() {
        if(employees.isEmpty()){
            System.out.println("No employees found");
            return null;
        }
        return employees;
        /*
        List<Employee> employees = new ArrayList<>();
        Field[] fields = Employee.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM employee";

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = rs.getObject(field.getName());
                        if (value != null) {
                            field.set(employee, value);
                        } else {
                            field.set(employee, "null");
                        }
                    }
                    employees.add(employee);
                }
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return employees;
        */
    }

    // Update an employee's information
    public void updateEmployee(Employee emp) throws IllegalAccessException {
        boolean found = false;
        boolean updated = false;
        for(Employee employee:employees){
            if(employee.getId() == emp.getId()){
                found = true;
                Field[] fields = Employee.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(emp);
                    if(value == null || field.getName().equals("id")) { continue; };
                    field.set(employee, value);
                    updated = true;
                }
                break;
            }
        }
        if(found && updated){
            System.out.println("Employee updated successfully");
        }else if(!found){
            System.out.println("Employee not found");
        } else if(!updated){
            System.out.println("No information found to change");
        } else {
            System.out.println("Unidentified error");
        }
        /*
        Field[] fields = Employee.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "UPDATE employee SET ";
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(emp);
                if(value == null || field.getName().equals("id")) { continue; }
                sql += field.getName() + " = ?,";
            }
            sql = sql.substring(0, sql.length() - 1); // Remove the last comma
            sql += " WHERE id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int index = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(emp);
                    if(value == null || field.getName().equals("id")) { continue; }
                    pstmt.setObject(index++, value);
                }
                for (Field field : fields) {
                    field.setAccessible(true);
                    if(field.getName().equals("id")) {
                        pstmt.setObject(index++, field.get(emp));
                    }
                }
                pstmt.executeUpdate();
                System.out.println("Employee updateed successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        */
    } 

    // Delete an employee by id
    public void deleteEmployee(String id) {
        boolean deleted = false;
        for(Employee employee:employees){
            if(employee.getId().equals(id)){
                employees.remove(employee);
                System.out.println("Employee deleted successfully");
                deleted = true;
                break;
            }
        }
        if(!deleted){
            System.out.println("Employee not found");
        }
        /*
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "DELETE FROM employee WHERE id = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, id);
                pstmt.executeUpdate();
                System.out.println("Employee deleted successfully");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        */
    }
}
