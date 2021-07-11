package chat_client.handler;

import chat_client.Main;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * The type Sign up click event.
 */
public class SignUpClickEvent implements EventHandler {
    @Override
    public void handle(Event event) {
        Main.registrationView.show(true);
        Main.logInView.clear();
        Main.logInView.show(false);
    }
}
