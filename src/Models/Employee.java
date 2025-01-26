package Models;

public class Employee {
    private int id;
    private String name;
    private String role;
    private String phone_no;
    private String email;
    private String password;
    
    public Employee(int id, String name, String role, String phone_no, String email, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.phone_no = phone_no;
        this.email = email;
        this.password = password;
    }
    public Employee(){}

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

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone_no(){
        return phone_no;
    }
    public void setPhone_no(String phone_no){
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
