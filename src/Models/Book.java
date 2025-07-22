package Models;

import Interfaces.Borrowable;
import Interfaces.Identifiable;
import Interfaces.Nameable;
import ch.qos.logback.core.encoder.JsonEscapeUtil;
import org.w3c.dom.ls.LSOutput;

public class Book implements Identifiable, Nameable, Borrowable {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int availableCopies;

    public int getId() {
        return id;
    }
    public String getName() {
        return title;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public void borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
        } else {
            throw new RuntimeException("No copies available for borrowing");
        }
    }

    @Override
    public void returnBook() {
        if (availableCopies >= 0) {
            availableCopies++;
        } else {
            throw new RuntimeException("Cannot return book, no copies were borrowed");
        }
    }
}
