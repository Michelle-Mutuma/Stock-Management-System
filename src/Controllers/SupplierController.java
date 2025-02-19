package Controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Field;

import Models.SupplierDAO;
import Models.Supplier;

public class SupplierController {
    private SupplierDAO supplierDao;
    private Supplier supplier;
	
	public SupplierController(Supplier supplier) throws ClassNotFoundException, SQLException {
		supplierDao = new SupplierDAO(supplier);
        this.supplier = supplier;
	}
    public SupplierController(){}
    
	//Add a supplier
	public void addSupplier() throws ClassNotFoundException {
		List<Supplier> suppliers = supplierDao.readAllSuppliers();
        for (Supplier sup : suppliers) {//ensures the new supplier's contact info is not the same as another suppliers
            if (sup.getPhone_no().equals(supplier.getPhone_no())) {
                System.out.println("Your phone number is already in the system please use another");
                break;
            } else if(sup.getEmail().equals(supplier.getEmail())){
                System.out.println("Your email is already in the system please use another");
                break;
            }else{
                supplierDao.createSupplier();
                System.out.println("New supplier added successfully");
            }
        }
	}
	
	//Get an supplier by id
	public Supplier getSupplier(){
		return supplierDao.readSupplier();
	}

    //Get all suppliers
	public List<Supplier> getAllSuppliers(){
		return supplierDao.readAllSuppliers();
	}
	
	//change an supplier's info
	public void changeSupplierInfo() throws SQLException, IllegalAccessException {
        Supplier supplierFromDb = supplierDao.readSupplier();
        Supplier updatedSupplier = new Supplier();
        Field[] fields = Supplier.class.getDeclaredFields();
    
        for (Field field : fields) {
            field.setAccessible(true);
            Object empValue = field.get(supplierFromDb);
            Object newValue = field.get(supplier);
    
            if (newValue != null && !newValue.equals(empValue)) {
                field.set(updatedSupplier, newValue);
            }
        }

        SupplierDAO newSupplierDao = new SupplierDAO(updatedSupplier);
        newSupplierDao.updateSupplier();
        System.out.println("Supplier's details changed successfully");
    }
    

    //remove an supplier
	public void removeSupplier() throws SQLException {
        System.out.println("Are you sure you want to remove this supplier?(yes/no)");
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine();
        if(answer == "yes"){
            supplierDao.deleteSupplier();
            System.out.println("Supplier removed successfully");
        }else if(answer == "no"){
            System.out.println("Supplier not removed");
        }else{
            System.out.println("Invalid input");
            removeSupplier();
        }
        input.close();
	}
}

