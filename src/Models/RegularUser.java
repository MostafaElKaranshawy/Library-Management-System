package Models;

import java.util.List;

public class RegularUser extends User {
    String address;
    String phoneNumber;

    List<Book> borrowedBooks;

    public RegularUser() {
        super();
    }

    public RegularUser(String name, String email, String password, String address, String phoneNumber) {
        super(name, email, password);
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
