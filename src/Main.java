import Models.Admin;
import Models.Book;
import Models.RegularUser;
import Models.User;
import Services.SystemService;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Welcome to the Library System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Regular User Dashboard");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Admin ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    try {
                        Admin admin = SystemService.login(id, password);
                        System.out.println("Welcome, " + admin.getName() + "!");
                        showAdminMenu(scanner);
                    } catch (RuntimeException e) {
                        System.out.println("Login failed: " + e.getMessage());
                    }
                    break;

                case "2":
                    showRegularUserDashboard(scanner);
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void showAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View all users");
            System.out.println("2. Search user by name");
            System.out.println("3. Register new regular user");
            System.out.println("4. Add book");
            System.out.println("5. Remove book");
            System.out.println("6. Update book");
            System.out.println("7. View all books");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    List<User> users = SystemService.getAllUsers();
                    System.out.printf("%-10s %-15s %-10s%n", "User ID", "Name", "Role");
                    System.out.println("=".repeat(40));

                    users.forEach(user -> {
                        System.out.printf("%-10s %-15s %-10s%n",
                                crop(user.getId(), 10),
                                crop(user.getName(), 15),
                                crop(user.getRole(), 10));
                    });
                    break;

                case "2":
                    System.out.print("Enter name to search: ");
                    String name = scanner.nextLine();
                    try {
                        User user = SystemService.getUserByName(name);
                        System.out.println(user.toString());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.out.print("Enter name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    SystemService.registerNewUser(newName, address, phone);
                    System.out.println("User registered successfully.");
                    break;

                case "4":
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Copies: ");
                    int copies = Integer.parseInt(scanner.nextLine());
                    SystemService.addBook(title, author, genre, copies);
                    System.out.println("Book added.");
                    break;

                case "5":
                    List<Book> curbBooks = SystemService.getAllBooks();
                    System.out.printf("%-10s %-30s %-20s %-15s %-10s%n", "ID", "Title", "Author", "Genre", "Copies");
                    System.out.println("--------------------------------------------------------------------------------------");

                    for (Book b : curbBooks) {
                        System.out.printf("%-10s %-30s %-20s %-15s %-10d%n",
                                crop(b.getId(), 10),
                                crop(b.getTitle(), 30),
                                crop(b.getAuthor(), 20),
                                crop(b.getGenre(), 15),
                                b.getAvailableCopies()
                        );
                    }
                    System.out.print("Book ID to remove: ");
                    String removeId = scanner.nextLine();
                    SystemService.removeBook(removeId);
                    System.out.println("Book removed.");
                    break;

                case "6":
                    List<Book> curBooks = SystemService.getAllBooks();
                    System.out.printf("%-10s %-30s %-20s %-15s %-10s%n", "ID", "Title", "Author", "Genre", "Copies");
                    System.out.println("--------------------------------------------------------------------------------------");

                    for (Book b : curBooks) {
                        System.out.printf("%-10s %-30s %-20s %-15s %-10d%n",
                                crop(b.getId(), 10),
                                crop(b.getTitle(), 30),
                                crop(b.getAuthor(), 20),
                                crop(b.getGenre(), 15),
                                b.getAvailableCopies()
                        );
                    }
                    System.out.print("Book ID to update: ");
                    String bookId = scanner.nextLine();
                    try {
                        Book previousBook = SystemService.getBookById(bookId);
                        System.out.println("Current details");
                        System.out.printf("%-10s %-30s %-20s %-15s %-10s%n", "ID", "Title", "Author", "Genre", "Copies");
                        System.out.printf("%-10s %-30s %-20s %-15s %-10d%n",
                                crop(previousBook.getId(), 10),
                                crop(previousBook.getTitle(), 30),
                                crop(previousBook.getAuthor(), 20),
                                crop(previousBook.getGenre(), 15),
                                previousBook.getAvailableCopies()
                        );
                    }
                    catch (Exception e) {
                        System.out.println("Book not found: " + e.getMessage());
                        continue;
                    }

                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("New author: ");
                    String newAuthor = scanner.nextLine();
                    System.out.print("New genre: ");
                    String newGenre = scanner.nextLine();
                    System.out.print("New copies: ");
                    String nCopies = scanner.nextLine();
                    int newCopies = -1;
                    if(!nCopies.isEmpty()) {
                        try {
                            newCopies = Integer.parseInt(nCopies);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number of copies. Keeping previous value.");
                        }
                    }

                    SystemService.updateBook(bookId, newTitle, newAuthor, newGenre, newCopies);
                    System.out.println("Book updated.");
                    break;

                case "7":
                    List<Book> books = SystemService.getAllBooks();
                    System.out.printf("%-10s %-30s %-20s %-15s %-10s%n", "ID", "Title", "Author", "Genre", "Copies");
                    System.out.println("--------------------------------------------------------------------------------------");

                    for (Book b : books) {
                        System.out.printf("%-10s %-30s %-20s %-15s %-10d%n",
                                crop(b.getId(), 10),
                                crop(b.getTitle(), 30),
                                crop(b.getAuthor(), 20),
                                crop(b.getGenre(), 15),
                                b.getAvailableCopies()
                        );
                    }

                    break;
                case "0":
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void showRegularUserDashboard(Scanner scanner) {
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();

        User user = null;
        try {
            user = SystemService.getAllUsers().stream()
                    .filter(u -> u.getId().equals(userId) && u instanceof RegularUser)
                    .findFirst()
                    .orElseThrow(() -> new Exception("User not found or not a regular user."));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        RegularUser regularUser = (RegularUser) user;

        while (true) {
            System.out.println("\n===== User Dashboard =====");
            System.out.println("1. Borrow a book");
            System.out.println("2. Return a book");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Map<String, List<Book>> booksByGenre = SystemService.getAllBooks().stream()
                            .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                            .collect(Collectors.groupingBy(Book::getGenre));

                    if (booksByGenre.isEmpty()) {
                        System.out.println("No books available.");
                    } else {
                        System.out.println("\nAvailable Books by Genre:");
                        booksByGenre.forEach((genre, books) -> {
                            System.out.println("\nGenre: " + genre);
                            System.out.printf("  %-10s %-30s %-20s %-10s%n", "Book ID", "Title", "Author", "Copies");
                            System.out.println("  --------------------------------------------------------------------------");
                            books.forEach(book -> {
                                System.out.printf("  %-10s %-30s %-20s %-10d%n",
                                        crop(book.getId(), 10),
                                        crop(book.getTitle(), 30),
                                        crop(book.getAuthor(), 20),
                                        book.getAvailableCopies()
                                );
                            });
                        });



                        System.out.print("\nEnter Book ID to borrow:  (or no to cancel) ");
                        String bookId = scanner.nextLine();
                        if(bookId.equals("no")){
                            break;
                        }
                        try {
                            Book borrowed = SystemService.borrowBook(bookId, userId);
                            System.out.println("Borrowed successfully: " + borrowed.getTitle());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case "2":
                    List<Book> borrowedBooks = regularUser.getBorrowedBooks();
                    if (borrowedBooks.isEmpty()) {
                        System.out.println("You have no borrowed books.");
                    } else {
                        System.out.println("Your Borrowed Books:");
                        System.out.printf("%-10s %-25s %-20s %-15s %n", "ID", "Title", "Author", "Genre");
                        System.out.println("--------------------------------------------------------------------------------------");

                        for (Book b : borrowedBooks) {
                            System.out.printf("%-10s %-25s %-20s %-15s %n",
                                    crop(b.getId(), 10),
                                    crop(b.getTitle(), 25),
                                    crop(b.getAuthor(),20),
                                    crop(b.getGenre(), 15)
                            );
                        }

                        System.out.print("Enter Book ID to return:   (or no to cancel) ");
                        String returnId = scanner.nextLine();
                        if (returnId.equals("no")) {
                            break;
                        }
                        try {
                            SystemService.returnBook(returnId, userId);
                            System.out.println("Book returned successfully.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case "3":
                    System.out.println("Exiting user dashboard.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    private static String crop(String str, int maxLength) {
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
