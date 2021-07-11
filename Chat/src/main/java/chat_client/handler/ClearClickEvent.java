package chat_client.handler;

import chat_client.Main;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Clear click event (clear button).
 */
public class ClearClickEvent implements EventHandler {
    @Override
    public void handle(Event event) {
        Main.registrationView.clear();
    }
}
