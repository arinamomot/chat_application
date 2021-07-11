package chat_client.view;

import chat_client.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.function.ToDoubleBiFunction;

public class SuccessfulRegistation extends Application {
    private Stage stage;

    private void addObjects(BorderPane pane) {
        Text text = new Text("Registration was successful!");
        text.setStyle("-fx-font-size: 25");

        pane.setCenter(text);
    }

    public void show(Boolean show) {
        if (show) {
            stage.show();
        } else {
            stage.close();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        BorderPane pane = new BorderPane();
        addObjects(pane);

        stage.setScene(new Scene(pane, 400, 200));
        stage.setTitle("Successful Registration");
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    public boolean isOpen() {
        if(stage == null) return false;
        return stage.isShowing();
    }
}

