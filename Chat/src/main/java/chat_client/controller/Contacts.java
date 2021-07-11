package chat_client.controller;

import chat_client.Connection;
import chat_client.ContactList;
import chat_client.Main;
import chat_client.controller.code.CodeStatus;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import chat_server.model.GroupEntity;
import chat_server.model.MessageEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;


/**
 * The type Contacts.
 */
public class Contacts {
    @FXML
    public ListView<Object> listContact;
    public AnchorPane header;
    public MenuItem groupSettings;
    public MenuButton menuGroup;
    @FXML
    public Text titleHeader;
    public Button send;
    public Button smile;
    public TextArea messageText;
    public VBox messagesPane;
    public ScrollPane scrollPane;
    public Button searchGroup;
    public TextField searchGroupTextField;

    @FXML
    MenuItem myProfileButton;
    @FXML
    MenuItem CreateGroup;
    @FXML
    MenuItem logoutButton;
    private Integer groupId;

    /**
     * Clear list.
     */
    public void clearList() {
        listContact.getItems().clear();
    }

    /**
     * Add group to the groups list panel.
     *
     * @param group the group
     */
    public void addContact(GroupEntity group) {
        Text title = new Text(group.getTitle());
        title.setStyle("-fx-font-size: 18");
        Text id = new Text(Integer.toString(group.getId()));
        id.setVisible(false);
        HBox info = new HBox();
        info.getChildren().add(title);
        info.getChildren().add(id);
        Pane contact = new Pane(info);
        listContact.getItems().add(contact);
    }


    /**
     * Click on contact.
     * Functions that happen when you click on a group.
     *
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public void clickOnContact() throws ConnectionError, SendError {
        if (listContact.getItems().isEmpty()) return;
        menuGroup.setVisible(true);
        titleHeader.setVisible(true);
        messageText.setVisible(true);
        send.setVisible(true);
        smile.setVisible(true);
        scrollPane.setVisible(true);
        messagesPane.setVisible(true);
        Pane item = (Pane) listContact.getSelectionModel().getSelectedItem();
        if (item == null) return;
        HBox hbox = (HBox) item.getChildren().get(0);
        Text text = (Text) hbox.getChildren().get(0);
        Text id = (Text) hbox.getChildren().get(1);
        groupId = Integer.parseInt(id.getText());
        titleHeader.setText(text.getText());
        clearMessages();
        Connection.getMessages(groupId);
    }


    /**
     * Click my profile.
     *
     * @throws ConnectionError the connection error
     */
    @FXML
    public void clickMyProfile() throws ConnectionError {
        Main.myProfile.show(true);
        Connection.getMe();
    }

    /**
     * Click create group.
     */
    @FXML
    public void clickCreateGroup() {
        Main.createGroup.show(true);
    }

    /**
     * Click logout.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void clickLogout() throws IOException {
        Main.contactList.controller.clearList();
        Main.contactList.controller.titleHeader.setText("");
        Main.contactList.controller.clearMessages();
        messagesPane.setVisible(false);
        messageText.setVisible(false);
        scrollPane.setVisible(false);
        menuGroup.setVisible(false);
        send.setVisible(false);
        smile.setVisible(false);

        Connection.logout();
    }

    /**
     * Click group settings.
     *
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    @FXML
    public void clickGroupSettings() throws ConnectionError, SendError {
        Main.groupSettings.show(true);
        Main.groupSettings.controller.clear();
        Pane item = (Pane) listContact.getSelectionModel().getSelectedItem();
        if (item == null) return;

        Main.groupSettings.controller.id = groupId;
        Connection.getCreator(groupId);
//        Connection.getRights(groupId, null);
//        System.out.println(System.currentTimeMillis());
        Connection.getMe();

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Connection.getGroup(groupId);
    }

    /**
     * Send message.
     *
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public void sendMessage() throws ConnectionError, SendError {
        if (!messageText.getText().equals("")) {
            Connection.sendMessage(messageText.getText(), groupId);
        } else {
            Main.errorWindow.controller.setError(CodeStatus.get(425).toString());
            Main.errorWindow.show(true);
        }
    }

    /**
     * Clear messages.
     */
    public void clearMessages() {
        messagesPane.getChildren().clear();
    }

    private boolean isSmile(String text) {
        // smile format is smile#hash
        if ((text.length() >= 6 && text.substring(0, 6).equals("smile#"))) {
            return ContactList.smiles.contains(text.substring(6));
        }
        return false;
    }

    /**
     * Add message.
     *
     * @param message the message
     * @throws ConnectionError the connection error
     * @throws IOException     the io exception
     */
    public void addMessage(MessageEntity message) throws ConnectionError, IOException {
        if (message.getText() == null) return;
        Text name = new Text(message.sender_user.getFirstname() + message.sender_user.getLastname());
        Text id = new Text(Integer.toString(message.getId()));
        id.setVisible(false);

        Text date = new Text(message.getTimesend().toString());
        VBox mes1 = new VBox();
        mes1.setSpacing(5);
        mes1.setStyle("-fx-border-color: #2ca8ff; -fx-font-size: 12; -fx-border-radius: 5; -fx-padding: 5; -fx-background-color: #cff1ff");
        mes1.getChildren().add(name);
        if (isSmile(message.getText())) {
            String url = "https://raw.githubusercontent.com/momot2020/images/master/" + message.getText().substring(6) + ".png";
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Image image = new Image(url);
            ImageView imageView = new ImageView(image);
            mes1.getChildren().add(imageView);
        } else {
            mes1.getChildren().add(new Text(message.getText()));

        }
        mes1.getChildren().add(date);
        scrollPane.setStyle("fx-background-color: #ffffff");
        messagesPane.setSpacing(10);
        messagesPane.getChildren().add(mes1);
        messagesPane.setStyle("-fx-padding: 10");
    }

    /**
     * Add smile.
     */
    public void addSmile() {
        Main.smilesWindow.show(true);
        Main.smilesWindow.controller.recipient = groupId;
    }

    /**
     * Search in my groups.
     *
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public void searchInMyGroups() throws ConnectionError, SendError {
        if (!searchGroupTextField.getText().toString().equals("")) {
            Connection.getInMyGroups(searchGroupTextField.getText().toString());
        } else {
            Connection.getGroups();
        }
    }
}
