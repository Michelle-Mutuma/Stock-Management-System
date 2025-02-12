package Models;

public class Supplier {
    private int id;
    private String name;
    private String phone_no;
    private String email;

    public Supplier(int id, String name, String phone_no, String email) {
        this.id = id;
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;
    }
    public Supplier(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }
    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void displaySupplier(){
        System.out.print("ID: " + getId());
        System.out.print("   Name: " + getName());
        System.out.print("   Email: " + getEmail());
        System.out.println("   Phone_no: " + getPhone_no());
    }
}
