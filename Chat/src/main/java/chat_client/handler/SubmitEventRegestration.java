package chat_client.handler;

import chat_client.Connection;
import chat_client.Main;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


/**
 * The action that happens when user click on the registration button.
 */
public class SubmitEventRegestration implements EventHandler {
    /**
     * Variable showing the status of the check (true- verification passed / false - verification faild).
     */
    private Boolean go;

    @Override
    public void handle(Event event) {

        /**
         * Check that the entered first name consists only of letters
         * and there are no less than two and no more than 40.
         */
        String fname = Main.registrationView.getFN();
        if (!fname.matches("^[a-zA-Z]{2,40}$")) {
            Main.registrationView.setFnameWarn(true);
            go = false;
            return;
        } else {
            Main.registrationView.setFnameWarn(false);
            go = true;
        }

        /**
         * Check that the entered last name consists only of letters
         * and there are no less than two and no more than 40.
         */
        String lname = Main.registrationView.getLN();
        if (!lname.matches("^[a-zA-Z]{2,40}$")) {
            Main.registrationView.setLnameWarn(true);
            go = false;
            return;
        } else {
            Main.registrationView.setLnameWarn(false);
            go = true;
        }

        /**
         * Check the entered mail (the presence of @ and a point).
         */
        String mail = Main.registrationView.getMail();
        if (!mail.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}$")) {
            Main.registrationView.setMailWarn(true);
            go = false;
            return;
        } else {
            Main.registrationView.setMailWarn(false);
            go = true;
        }

//        "^[a-zA-Z0-9._%+-]{1,20}+@[a-z0-9.-]{2,10}+\\.[a-z]{2,6}$"

        /**
         * Check the passwords similarity and that they have 8 or more characters.
         */
        String pass = Main.registrationView.getPass();
        String pass2 = Main.registrationView.getPass2();
        if (pass2.equals(pass)) {
            if (!pass.matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$") && (!pass2.matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$"))) {
                Main.registrationView.setPassWarn(true);
                Main.registrationView.setPass2Warn(true);
                go = false;
                return;
            } else {
                Main.registrationView.setPassWarn(false);
                Main.registrationView.setPass2Warn(false);
                go = true;
            }
        }else {
            return;
        }

        /**
         * Check that the entered country consists only of letters
         * and there are no less than two and no more than 40.
         */
        String country = Main.registrationView.getCountry();
        if (!country.matches("^[a-zA-Z]{2,40}$")) {
            Main.registrationView.setCountryWarn(true);
            go = false;
            return;
        } else {
            Main.registrationView.setCountryWarn(false);
            go = true;
        }

        /**
         * Checks if the date field is full and is not later than current date.
         */
        LocalDate now = LocalDate.now();

        LocalDate date = Main.registrationView.getDate();
        if (date == null || date.isEqual(now) || date.isAfter(now)) {
            Main.registrationView.setBdWarn(true);
            go =false;
            return;
        }else{
            Main.registrationView.setBdWarn(false);
            go = true;
        }

        String bd = Main.registrationView.getDate().toString();

        String gender = Main.registrationView.getGender();


        if (go){
            Main.registrationView.show(false);
            Main.registrationView.clear();
            Main.statusbar.show(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished((e) -> {
                Main.statusbar.show(false);
                Main.logInView.show(true);
            });
            pause.play();
        } else {
            return;
        }

        try {
            Connection.createUser(fname, lname, mail, pass, country, bd, gender);
            Main.registrationView.clear();
        } catch (ConnectionError | SendError connectionError) {
            connectionError.printStackTrace();
        }

        // serverrequest

    }
}

