package chat_client.controller;

import chat_client.Main;
import chat_client.controller.code.CodeStatus;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ErrorWindowController {

    public Button ok;
    public Text error;

    public void setError(String message) {
        error.setText(message);
    }

    public void okAction() {
        Main.errorWindow.show(false);   
    }
}
