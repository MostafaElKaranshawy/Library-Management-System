package Models;

public class Admin extends User {
    String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    public Admin(String id, String name, String password) {
        super(id, name, "Admin");
        this.password = password;
    }
}
