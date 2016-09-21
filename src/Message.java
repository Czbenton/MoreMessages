/**
 * Created by Zach on 9/20/16.
 */
public class Message {
    public String message;

    public Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%s",message);
    }
}
