package chat_client.view;

import chat_client.Main;
import chat_client.controller.ErrorWindowController;
import chat_client.controller.GroupSettingsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ErrorWindow extends Application {
    public ErrorWindowController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("errorWindow.fxml"));
        Scene scene = root.load();
        stage.setScene(scene);
        stage.setResizable(false);
        controller = root.getController();
    }

    public void show(Boolean show) {
        if (stage == null) return;
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
