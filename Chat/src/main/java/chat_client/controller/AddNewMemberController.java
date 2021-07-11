package chat_client.controller;

import chat_client.Connection;
import chat_client.Main;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import chat_server.model.GroupEntity;
import chat_server.model.UserEntity;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.eclipse.persistence.internal.oxm.schema.model.List;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Add new member controller.
 */
public class AddNewMemberController {

    /**
     * The Search button.
     */
    @FXML
    public Button searchButton;
    /**
     * The Search field.
     */
    @FXML
    public TextField searchField;
    /**
     * The List of users.
     */
    @FXML
    public ListView<Object> listOfUsers = new ListView<>();
    /**
     * The Group id.
     */
    public Integer group_id;
    /**
     * The Type.
     */
    public Boolean type;
    /**
     * The Creator id.
     */
    public Integer creatorId;

    /**
     * Clear function.
     * Clear list of users and search field
     */
    public void clear() {
        searchField.setText("");
        listOfUsers.getItems().clear();
    }

    /**
     * The Members.
     */
    public Map<String, Integer> members = new HashMap<>();


    /**
     * Get users info and show it in search list.
     *
     * @param user the user
     */
    public void getUser(UserEntity user) {
        Text fname = new Text(user.getFirstname());
        Text lname = new Text(user.getLastname());
        Text id = new Text(user.getId().toString());
        id.setVisible(false);
        Button invite = new Button("Invite");
        invite.setOnMouseClicked((e) -> {
            Platform.runLater(() -> {
                Main.statusbar.show(true);
                Main.addNewMember.show(false);
                Main.groupSettings.show(false);
                Main.addNewMember.controller.clear();
            });
            try {
                Connection.addMember(user.getId(), group_id, type);
            } catch (ConnectionError | SendError connectionError) {
                connectionError.printStackTrace();
            }
        });
        HBox info = new HBox();
        info.getChildren().add(fname);
        info.getChildren().add(lname);
        info.getChildren().add(id);
        if (!user.getId().equals(creatorId)) {
            info.getChildren().add(invite);
        }
        Pane userPane = new Pane(info);
        //todo oshibka?
        listOfUsers.getItems().add(userPane);
    }


    /**
     * Search button click.
     *
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public void searchButtonClick() throws ConnectionError, SendError {
        listOfUsers.getItems().clear();
        Connection.getUsers(searchField.getText(), searchField.getText());
    }

}
