package chat_client;

import chat_client.controller.MyProfileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyProfile extends Application {
    public MyProfileController controller;
    public Text mail;
    public Text name;
    public Text country;
    public Text bd;
    public Text gender;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader root = new FXMLLoader(getClass().getResource("my_profile.fxml"));
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
