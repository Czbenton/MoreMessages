import java.util.ArrayList;

/**
 * Created by Zach on 9/20/16.
 */
public class User {

    String name;
    String password;
    ArrayList<Message> messages = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public User(String password){
        this.password = password;
    }
}
