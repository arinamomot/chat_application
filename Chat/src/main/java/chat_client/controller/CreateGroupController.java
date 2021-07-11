package chat_client.controller;

import chat_client.Connection;
import chat_client.Main;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Create group controller.
 */
public class CreateGroupController {
 ToggleGroup group = new ToggleGroup();

    @FXML
    public RadioButton t_private;

    @FXML
    public RadioButton t_public;

    @FXML
    public TextField title;

    @FXML
    public TextArea description;

    @FXML
    public Button create;


    @FXML
    private void initialize() {
        t_private.setToggleGroup(group);
        t_private.setSelected(true);
        t_public.setToggleGroup(group);

    }

    /**
     * Create group.
     *
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    @FXML
    public void createGroup() throws ConnectionError, SendError {
        Platform.runLater(() -> {
            Main.statusbar.show(true);
            Main.createGroup.clear();
        });
        Connection.createGroup(getTitle(), getDescription(), getType());
    }


    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title.getText().toString();
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description.getText().toString();
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Boolean getType() {return t_public.isSelected();}

}
