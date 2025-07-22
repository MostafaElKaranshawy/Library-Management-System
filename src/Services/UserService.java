package Services;

import Models.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Map;

public class UserService {
    // email, user.
    Map<String, User> users;
    private SearchService<User> userSearchService;

    public UserService(Map<String, User> users, SearchService<User> userSearchService) {
        this.users = users;
        this.userSearchService = userSearchService;
    }

    public void login(String email, String password) {
        User user = users.get(email);
        if (user != null && BCrypt.checkpw(user.getPassword(),password)) {
            System.out.println("Login successful for user: " + user.getName());
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }
    public void register(String name, String email, String password) {
        if (users.containsKey(email)) {
            throw new RuntimeException("User with this email already exists");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(name, email, hashedPassword);
        users.put(email, newUser);
        System.out.println("User registered successfully: " + newUser.getName());
    }
}
