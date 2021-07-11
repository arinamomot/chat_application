package chat_client;

import chat_client.controller.CreateGroupController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateGroup extends Application {
    public CreateGroupController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader root = new FXMLLoader(getClass().getResource("createGroup.fxml"));
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

    public void clear() {
        controller.title.setText("");
        controller.description.setText("");
    }

    public boolean isOpen() {
        if(stage == null) return false;
        return stage.isShowing();
    }
}
