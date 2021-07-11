package chat_client.handler;

import chat_client.Connection;
import chat_client.Main;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * The action that happens when user click on the login button.
 */
public class SubmitEventLogIn implements EventHandler {

    @Override
    public void handle(Event event) {
        /**
         * Check the entered mail (the presence of @ and a point).
         */
        String mail = Main.logInView.getMail();
        if (!mail.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}$")) {
            Main.logInView.setMailWarn(true);
            return;
        } else {
            Main.logInView.setMailWarn(false);
        }

        /**
         * Check if the password have 8 or more characters.
         */
        String pass = Main.logInView.getPass();
        if (!pass.matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$")){
            Main.logInView.setPassWarn(true);
            return;
        } else {
            Main.logInView.setPassWarn(false);
        }
        Main.logInView.show(false);

        Main.statusbar.show(true);
        try {
            Connection.login(mail, pass);
            Main.logInView.clear();
        } catch (ConnectionError  | SendError connectionError) {
            connectionError.printStackTrace();
        }
    }
}
