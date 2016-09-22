import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {
    static User user;
    static Message message;
    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");
        Spark.init();

        Spark.get("/", ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);

                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    } else {
                        m.put("name", user.name);
                        m.put("messageList", user.messages);
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/createUser",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    String password = request.queryParams("password");
                    if (users.containsKey(name)) {
                        if (password.equals(users.get(name).password)) {
                            user = new User(name, password);
                            Session session = request.session();
                            session.attribute("userName", name);
                            response.redirect("/");
                        } else {
                            response.redirect("/");
                        }
                    } else {
                        user = new User(name, password);
                        users.put(name, user);
                        Session session = request.session();
                        session.attribute("userName", name);
                        response.redirect("/");
                    }
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/createMessage",
                ((request, response) -> {
                    String userMessage = request.queryParams("message");
                    message = new Message(userMessage);
                    user.messages.add(message);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/deleteMessage",
                ((request, response) -> {
                    int messageNum = Integer.parseInt(request.queryParams("delete"));
                    user.messages.remove(messageNum - 1);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/editMessage",
                ((request, response) -> {
                    int messageNum = Integer.parseInt(request.queryParams("editNum"));
                    String newMessage = request.queryParams("edit");
                    user.messages.set(messageNum - 1, new Message(newMessage));
                    response.redirect("/");
                    return "";
                })
        );
    }
}
