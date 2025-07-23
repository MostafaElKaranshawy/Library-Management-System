package Models;

import Interfaces.Borrowable;
import Interfaces.Identifiable;
import Interfaces.Nameable;
import ch.qos.logback.core.encoder.JsonEscapeUtil;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;

public class Book implements Identifiable, Nameable, Borrowable {
    private String id;
    private String title;
    private String author;
    private String genre;
    private int availableCopies;

    public Book(String id, String title, String author, String genre, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availableCopies = availableCopies;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return title;
    }
    public void setId(String id) {
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

    @Override
    public String
    toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", availableCopies=" + availableCopies +
                '}';
    }
}
