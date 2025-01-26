package Models;

import Utils.ConnectDb;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

public class EmployeeDAO {
    private Employee employee;
    private Employee employeeFromDb;

    public EmployeeDAO(Employee employee){
        this.employee = employee;
    }
    public EmployeeDAO(){}

    // Create a new employee
    public void addEmployee() {
        Field[] fields = Employee.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "INSERT INTO employee (";
            String values = "VALUES (";
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getName().equals("id")) { continue; }
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
                    if(field.getName().equals("id")) { continue; }
                    pstmt.setObject(index++, field.get(employee));
                }
                pstmt.executeUpdate();
                System.out.println("New employee added successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Read an employee by id
    public Employee getEmployee() {
        Field[] fields = Employee.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM employee WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, employee.getId());
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
    public List<Employee> getAllEmployees() {
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
    }

    // Update an employee's information
    public void updateEmployee() {
        Field[] fields = Employee.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "UPDATE employee SET ";
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getName().equals("id")) { continue; }
                sql += field.getName() + " = ?,";
            }
            sql = sql.substring(0, sql.length() - 1); // Remove the last comma
            sql += " WHERE id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int index = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    if(field.getName().equals("id")) { continue; }
                    pstmt.setObject(index++, field.get(employee));
                }
                for (Field field : fields) {
                    field.setAccessible(true);
                    if(field.getName().equals("id")) {
                        pstmt.setObject(index++, field.get(employee));
                    }
                }
                pstmt.executeUpdate();
                System.out.println("Employee details changed successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Delete an employee by id
    public void deleteEmployee() {
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "DELETE FROM employee WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, employee.getId());
                pstmt.executeUpdate();
                System.out.println("Employee deleted successfully");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}