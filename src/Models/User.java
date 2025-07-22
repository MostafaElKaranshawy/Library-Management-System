package Models;

import Interfaces.Identifiable;
import Interfaces.Nameable;

import java.util.ArrayList;
import java.util.List;

public class User implements Identifiable, Nameable {
    private String id;
    private String name;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(){}

    public User(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // for admin polymorphism
    public String getPassword() { return null; }

    public void setPassword(String password) {}

    // for user polymorphism
    public List<Book> getBorrowedBooks() { return null; }

    public void borrowBook(Book book) {}

    public void returnBook(Book book) {}

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
