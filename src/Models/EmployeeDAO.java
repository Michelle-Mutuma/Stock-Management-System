package Models;

import java.util.ArrayList;
import java.util.List;

import Utils.ConnectDb;

//import java.sql.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import Utils.ConnectDb;

public class EmployeeDAO {
    private List<Employee> employees;
    
    public EmployeeDAO(){
    	employees = new ArrayList<>();
    }

    // Create a new employee
    public void createEmployee(Employee emp) {
        
        Field[] fields = Employee.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "INSERT INTO employees (";
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
                    pstmt.setObject(index++, field.get(emp));
                }
                pstmt.executeUpdate();
                System.out.println("New employee created successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
    }

    // Read an employee by id
    public Employee readEmployee(int id) {
        
        Employee employeeFromDb;
        Field[] fields = Employee.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM employees WHERE id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, id);
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
      
    }

    // Read all employees
    public List<Employee> readAllEmployees() {
      
        List<Employee> employees = new ArrayList<>();
        Field[] fields = Employee.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM employees";

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
        
    }

    // Update an employee's information
    public void updateEmployee(Employee emp) throws IllegalAccessException {
       
        Field[] fields = Employee.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "UPDATE employees SET ";
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
        
    } 

    // Delete an employee by id
    public void deleteEmployee(int deleteeId) {
        
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "DELETE FROM employees WHERE id = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, deleteeId);
                pstmt.executeUpdate();
                System.out.println("Employee deleted successfully");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
}
