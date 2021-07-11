package chat_client.handler;

import chat_client.Main;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * The type Create group event.
 */
public class CreateGroupEvent implements EventHandler {

    @Override
    public void handle(Event event) {
        Main.createGroup.show(false);
    }
}
