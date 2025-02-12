package Models;

import Utils.ConnectDb;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

public class SupplierDAO {
    private Supplier supplier;

    public SupplierDAO(Supplier supplier){
        this.supplier = supplier;
    }
    public SupplierDAO(){}

    // Create a new supplier
    public void createSupplier() {
        Field[] fields = Supplier.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "INSERT INTO supplier (";
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
                    pstmt.setObject(index++, field.get(supplier));
                }
                pstmt.executeUpdate();
                System.out.println("New upplier added successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Read an supplier by id
    public Supplier readSupplier() {
        Supplier supplierFromDb;
        Field[] fields = Supplier.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM supplier WHERE id = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, supplier.getId());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        supplierFromDb = new Supplier();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            Object value = rs.getObject(field.getName());
                            field.set(supplierFromDb, value);
                            System.out.println(field.getName() + ": " + (value != null ? value : "null"));
                        }
                        return supplierFromDb;
                    } else {
                        System.out.println("Supplier not found.");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read all suppliers
    public List<Supplier> readAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        Field[] fields = Supplier.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "SELECT * FROM supplier";

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Supplier supplier = new Supplier();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = rs.getObject(field.getName());
                        if (value != null) {
                            field.set(supplier, value);
                        } else {
                            field.set(supplier, "null");
                        }
                    }
                    suppliers.add(supplier);
                }
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    // Update an supplier's information
    public void updateSupplier() {
        Field[] fields = Supplier.class.getDeclaredFields();

        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "UPDATE supplier SET ";
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(supplier);
                if(value == null || field.getName().equals("id")) { continue; }
                sql += field.getName() + " = ?,";
            }
            sql = sql.substring(0, sql.length() - 1); // Remove the last comma
            sql += " WHERE id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int index = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(supplier);
                    if(value == null || field.getName().equals("id")) { continue; }
                    pstmt.setObject(index++, field.get(supplier));
                }
                for (Field field : fields) {
                    field.setAccessible(true);
                    if(field.getName().equals("id")) {
                        pstmt.setObject(index++, field.get(supplier));
                    }
                }
                pstmt.executeUpdate();
                System.out.println("Supplier details changed successfully");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Delete an supplier by id
    public void deleteSupplier() {
        try (Connection conn = ConnectDb.connectToDb()) {
            String sql = "DELETE FROM supplier WHERE id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, supplier.getId());
                pstmt.executeUpdate();
                System.out.println("Supplier deleted successfully");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}