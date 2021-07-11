package chat_client;

import chat_client.controller.AddNewMemberController;
import chat_client.controller.Contacts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddNewMember extends Application {
    public AddNewMemberController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader root = new FXMLLoader(getClass().getResource("addNewMember.fxml"));
        Scene parent = root.load();
        stage.setScene(parent);
        stage.setResizable(false);
        controller = root.getController();
    }

    public void show(Boolean show) {
        if(stage == null) return;
        if (show) {
            stage.show();
        } else {
            stage.close();
        }
    }

    public boolean isOpen() {
        if(stage == null) return false;
        return stage.isShowing();
    }
}
