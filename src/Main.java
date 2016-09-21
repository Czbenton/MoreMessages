import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static User user;
    static Message message;
    static ArrayList<Message> list = new ArrayList();

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");
        Spark.init();

        Spark.get("/", ((request, response) -> {
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    } else {
                        m.put("name", user.name);
                        m.put("messageList",list);
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/createUser",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    user = new User(name);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/createMessage",
                ((request, response) -> {
                    String userMessage = request.queryParams("message");
                    message = new Message(userMessage);
                    list.add(message);
                    response.redirect("/");
                    return "";
                })
        );
    }
}
