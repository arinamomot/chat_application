package chat_client;

import chat_client.handler.SignUpClickEvent;
import chat_client.handler.SubmitEventLogIn;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The type Log in.
 */
public class LogIn extends Application {
    private Stage stage;
    private TextField mail = new TextField();
    private TextField pass = new PasswordField();

    public String getMail() {
        return mail.getText().toString();
    }
    public String getPass() {
        return pass.getText().toString();
    }


    public void setMailWarn(Boolean status) {
        if(status) {
            mail.setStyle("-fx-background-color: red");
        } else {
            mail.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }
    public void clear() {
        this.mail.setText("");
        this.pass.setText("");
        this.mail.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        this.pass.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
    }


    public void setPassWarn(Boolean status) {
        if(status) {
            pass.setStyle("-fx-background-color: red");
        } else {
            pass.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }


    public void show(Boolean show){
        if(stage == null) return;
        if (show) {
            stage.show();
        } else {
            stage.close();
        }
    }

    private void setPane(GridPane root){
        root.setPadding(new Insets(80, 150, 100, 150));
        root.setMinSize(300, 300);
        root.setHgap(10);
        root.setVgap(15);
//        root.setGridLinesVisible(true);
    }
    private void add(GridPane root){
        Text heading = new Text("Log in");
        heading.setStyle("-fx-font-size: 40");
        Text text = new Text("Please fill in this form to log in to your account.");
        text.setStyle("-fx-font-size: 16");
        Text email = new Text("Mail:");
        Text password = new Text("Password:");
        Button submit = new Button("Submit");
        Button sign_up = new Button("Sign up");
//        sign_up.addEventHandler(new MouseEvent());
        sign_up.setOnMouseClicked(new SignUpClickEvent());
        submit.setOnMouseClicked(new SubmitEventLogIn());
//        root.getColumnConstraints().add(new ColumnConstraints(200));

        root.add(heading, 3,0,3,1);
        root.add(text,0,1,4,1);
        root.add(email, 1, 2);
        root.add(mail, 2, 2, 3,1);
        root.add(password, 1, 3);
        root.add(pass, 2, 3,3,1);
        root.add(submit,4, 4);
        root.add(sign_up, 3, 4 );
    }

    public boolean isOpen() {
        if(stage == null) return false;
        return stage.isShowing();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        GridPane root = new GridPane();
        setPane(root);
        add(root);

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Chat");
        stage.setResizable(false);
        stage.centerOnScreen();
        show(true);
    }
}
