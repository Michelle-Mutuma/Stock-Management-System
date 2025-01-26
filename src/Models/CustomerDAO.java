package Models;

import Utils.ConnectDb;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

public class CustomerDAO {
    private Customer customer;
    private Customer customerFromDb;

    public CustomerDAO(Customer customer){
        this.customer = customer;
    }
    public CustomerDAO(){}

    // Create a new customer
    public void addCustomer() {
        Field[] fields = Customer.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "INSERT INTO customer (";
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
                    pstmt.setObject(index++, field.get(customer));
                }
                pstmt.executeUpdate();
                System.out.println("New customer added successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Read an customer by id
    public Customer getCustomer() {
        Field[] fields = Customer.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM customer WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, customer.getId());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        customerFromDb = new Customer();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            Object value = rs.getObject(field.getName());
                            field.set(customerFromDb, value);
                            System.out.println(field.getName() + ": " + (value != null ? value : "null"));
                        }
                        return customerFromDb;
                    } else {
                        System.out.println("Customer not found.");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Field[] fields = Customer.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM customer";
            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = rs.getObject(field.getName());
                        if (value != null) {
                            field.set(customer, value);
                        } else {
                            field.set(customer, "null");
                        }
                    }
                    customers.add(customer);
                }
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Update an customer's information
    public void updateCustomer() {
        Field[] fields = Customer.class.getDeclaredFields();
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "UPDATE customer SET ";
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
                    pstmt.setObject(index++, field.get(customer));
                }
                for (Field field : fields) {
                    field.setAccessible(true);
                    if(field.getName().equals("id")) {
                        pstmt.setObject(index++, field.get(customer));
                    }
                }
                pstmt.executeUpdate();
                System.out.println("Customer details changed successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Delete an customer by id
    public void deleteCustomer() {
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "DELETE FROM customer WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, customer.getId());
                pstmt.executeUpdate();
                System.out.println("Customer deleted successfully");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}