package chat_client.view;

import chat_client.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class StatusBarWindow extends Application {
    private Stage stage;

    private void setPane(GridPane pane) {
        pane.setHgap(10);
        pane.setPadding(new Insets(120));
    }

    private void addObjects(GridPane pane) {
        ProgressIndicator bar = new ProgressIndicator();
        bar.setStyle("-fx-font-size: 30");
        Text text = new Text("Loading");
        text.setStyle("-fx-font-size: 30");

        pane.add(bar, 0, 0);
        pane.add(text, 1, 0);
    }

    public void show(Boolean show) {
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

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        GridPane pane = new GridPane();
        setPane(pane);
        addObjects(pane);

        stage.setScene(new Scene(pane, 400, 300));
        stage.setTitle("StatusBar");
        stage.centerOnScreen();
        stage.setResizable(false);
    }

}
