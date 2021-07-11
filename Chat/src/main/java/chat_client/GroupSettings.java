package chat_client;

import chat_client.controller.Contacts;
import chat_client.controller.GroupSettingsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GroupSettings extends Application {
    public GroupSettingsController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader root = new FXMLLoader(getClass().getResource("groupSettings.fxml"));
        Scene scene = root.load();
        stage.setScene(scene);
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
