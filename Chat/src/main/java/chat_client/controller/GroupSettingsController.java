package chat_client.controller;

import chat_client.Connection;
import chat_client.Main;
import chat_client.controller.code.CodeStatus;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import chat_server.model.GroupEntity;
import chat_server.model.MembershipEntity;
import chat_server.model.UserEntity;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class GroupSettingsController {
    public Text dateCreated;
    public Text creator_name;
    public Text creator_lastname;
    ToggleGroup toggleGroup = new ToggleGroup();
    public TextArea description;
    public Button deleteGroup;
    public Button toAddNewMember;
    public Button changeGroup;
    public TextField title;
    public RadioButton t_public;
    public RadioButton t_private;
    private String title2;
    private String description2;
    private Integer me_id;
    public Integer creator_id;
    private String rights = "";
    private Boolean type;
    private TextField right;
    private Integer user;
    public String user_rights;
    public Set<MembershipEntity> set = new HashSet<>();

    public ListView<Object> members;

    public Integer id;

    public TextField getTitle() {
        return title;
    }

    public void setTitle(TextField title) {
        this.title = title;
    }

    public Boolean getType() {
        return !t_private.isSelected();
    }

    public void setType(Boolean type) {
        if (type) t_private.setSelected(true);
        t_public.setSelected(true);
    }


    public TextArea getDescription() {
        return description;
    }

    public void setDescription(TextArea description) {
        this.description = description;
    }

    private String getRightsFromSet(Integer user_id) {
        for(MembershipEntity member: set) {
            if(member.getUserid().equals(user_id)) return member.getRights();
        }
        return "";
    }

    /**
     * Gets creator.
     *
     * @param user the user
     */
    public void getCreator(UserEntity user) {
        creator_name.setText(user.getFirstname());
        creator_lastname.setText(user.getLastname());
        creator_id = user.getId();
        Main.addNewMember.controller.creatorId = creator_id;
    }


    /**
     * Gets date created.
     *
     * @return the date created
     */
    public Text getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets date created.
     *
     * @param dateCreated the date created
     */
    public void setDateCreated(Text dateCreated) {
        this.dateCreated = dateCreated;
    }


    /**
     * Gets group.
     * Get information about group and its members, add buttons.
     *
     * @param group the group
     * @throws ConnectionError the connection error
     */
    public void getGroup(GroupEntity group, Set<MembershipEntity> set) throws ConnectionError {
        this.set = set;
        title.setText(group.getTitle());
        title2 = group.getTitle();
        description.setText(group.getDescription());
        description2 = group.getDescription();
        creator_id = group.getCreator();
        setType(group.getType());
        type = group.getType();
        dateCreated.setText(group.getDatecreated().toString());
        members.getItems().clear();
        for (UserEntity user : group.getMembers()) {
            Text title = new Text(user.getFirstname() + "  " + user.getLastname());
            Integer user_id = user.getId();
            Text id = new Text(Integer.toString(user.getId()));
            Button remove = new Button("remove");
            Button changeRights = new Button("change rights");
            Button leave = new Button("leave");
            TextField right = new TextField();
            right.setText(getRightsFromSet(user_id));
            if (user_id.equals(me_id)){
                rights = getRightsFromSet(me_id);
            }
            right.setMaxSize(40, 10);
            remove.setOnMouseClicked((e) -> {
                if (rights.contains("d")) {
                    try {
                        Connection.removeMember(group.getId(), user.getId());
                        Platform.runLater(() -> {
                            Main.groupSettings.show(false);
                            clear();
                        });
                    } catch (ConnectionError connectionError) {
                        connectionError.printStackTrace();
                    }
                } else {
                    Main.errorWindow.controller.setError(CodeStatus.get(422).toString());
                    Main.errorWindow.show(true);
                }
            });
            changeRights.setOnMouseClicked((e) -> {
                if (rights.contains("d")) {
                    if (!right.getText().equals("")) {
                        try {
                            Connection.changeRights(user.getId(), group.getId(), right.getText().toString());
                            Platform.runLater(() -> {
                                Main.groupSettings.show(false);
                                clear();
                            });
                        } catch (ConnectionError | SendError connectionError) {
                            connectionError.printStackTrace();
                        }
                    } else {
                        Main.errorWindow.controller.setError(CodeStatus.get(428).toString());
                        Main.errorWindow.show(true);
                    }
                } else {
                    Main.errorWindow.controller.setError(CodeStatus.get(427).toString());
                    Main.errorWindow.show(true);
                }
            });
            leave.setOnMouseClicked((e) -> {
                try {
                    Connection.removeMember(group.getId(), me_id);
                    Platform.runLater(() -> {
                        Main.groupSettings.show(false);
                        clear();
                        Main.contactList.controller.clearList();
                        Main.contactList.controller.clearMessages();
                        Main.contactList.controller.titleHeader.setText("");
                        Main.contactList.controller.menuGroup.setVisible(false);
                        try {
                            Connection.getGroups();
                        } catch (ConnectionError connectionError) {
                            connectionError.printStackTrace();
                        }
                    });
                } catch (ConnectionError connectionError) {
                    connectionError.printStackTrace();
                }
            });
            id.setVisible(false);
            HBox info = new HBox();
            info.getChildren().add(title);
            info.getChildren().add(id);
            info.getChildren().add(right);
            if (!user_id.equals(creator_id) && (!user_id.equals(me_id))) {
                info.getChildren().add(changeRights);
                info.getChildren().add(remove);
            }
            if (user_id.equals(me_id) && (!me_id.equals(creator_id))) {
                info.getChildren().add(leave);
            }
            Pane contact = new Pane(info);
            members.getItems().add(contact);
        }

    }


    @FXML
    private void initialize() {
        t_private.setToggleGroup(toggleGroup);
        t_public.setToggleGroup(toggleGroup);
    }

    /**
     * Gets me.
     *
     * @param user the user
     * @return id of me
     */
    public Integer getMe(UserEntity user) {
        return me_id = user.getId();
    }

    /**
     * Gets rights.
     *
     * @param user_rights the user rights
     */
    public void getRights(String user_rights) {
        rights = user_rights;
    }

    /**
     * Goto add members.
     *
     * @throws ConnectionError the connection error
     */
    @FXML
    public void gotoAddMembers() throws ConnectionError {
        Main.addNewMember.controller.group_id = id;
        Main.addNewMember.controller.type = type;
        Main.addNewMember.show(true);
        Main.addNewMember.controller.clear();
    }

    /**
     * Change group.
     *
     * @throws ConnectionError the connection error
     */
    @FXML
    public void changeGroupClick() throws ConnectionError {
        if ((!title.getText().equals(title2)) || (!description.getText().equals(description2)))
            Connection.changeGroupInfo(title.getText(), description.getText(), id);
    }

    /**
     * Delete group.
     *
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    @FXML
    public void deleteGroupClick() throws ConnectionError, SendError {
        if (me_id.equals(creator_id)) {
            Connection.deleteGroup(id);
        } else {
            Main.errorWindow.controller.setError(CodeStatus.get(413).toString());
            Main.errorWindow.show(true);
        }
    }

    /**
     * Clear group settings window.
     */
    public void clear() {
        title.setText("");
        description.setText("");
        members.getItems().clear();
    }

    public void clickOnMember(MouseEvent mouseEvent) throws ConnectionError {
        Pane item = (Pane) members.getSelectionModel().getSelectedItem();
        if (item == null) return;
        HBox hbox = (HBox) item.getChildren().get(0);
        Text id = (Text) hbox.getChildren().get(1);
        Main.profile.show(true);
        Connection.getUserInfo(Integer.parseInt(id.getText()));
    }
}
