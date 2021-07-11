package chat_client.handler;

import chat_client.Main;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * The action that happens when user click on the <-logIn button.
 */
public class ToLoginEvent implements EventHandler {
    @Override
    public void handle(Event event) {
        Main.registrationView.show(false);
        Main.registrationView.clear();
        Main.logInView.show(true);
    }
}
