package chat_client;

import chat_client.handler.ClearClickEvent;
import chat_client.handler.SubmitEventRegestration;
import chat_client.handler.ToLoginEvent;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class Registration extends Application{
    private Stage stage;
    private TextField fname = new TextField();
    private TextField lname = new TextField();
    private TextField mail = new TextField();
    private PasswordField pass = new PasswordField();
    private PasswordField pass2 = new PasswordField();
    private TextField countr = new TextField();
    private DatePicker bd = new DatePicker();
    private RadioButton female = new RadioButton();
    private RadioButton male = new RadioButton();


    public LocalDate getDate(){return bd.getValue();}
    public String getFN() { return fname.getText().toString(); }
    public String getLN() {
        return lname.getText().toString();
    }
    public String getMail() {
        return mail.getText().toString();
    }
    public String getPass() {
        return pass.getText().toString();
    }
    public String getPass2() { return pass2.getText().toString(); }
    public String getCountry() {
        return countr.getText().toString();
    }

    /**
     * Gets gender of user
     *
     * @return the gender that the user selected
     */
    public String getGender() {
        if(male.isSelected()) {
            return "m";
        } else  if (female.isSelected()) {
            return "f";
        } else return null;
    }

    /**
     * Sets first name warn
     * If the field does not pass the check, makes it red.
     * @param status the status
     */
    public void setFnameWarn(Boolean status) {
        if(status) { fname.setStyle("-fx-background-color: red");
        } else { fname.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5"); }
    }

    /**
     * Sets last name warn.
     * If the field does not pass the check, makes it red.
     * @param status the status
     */
    public void setLnameWarn(Boolean status) {
        if(status) { lname.setStyle("-fx-background-color: red");
        } else { lname.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5"); }
    }

    /**
     * Sets mail warn.
     * If the field does not pass the check, makes it red.
     * @param status the status
     */
    public void setMailWarn(Boolean status) {
        if(status) { mail.setStyle("-fx-background-color: red");
        } else { mail.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5"); }
    }

    /**
     * Sets password warn.
     * If the field does not pass the check, makes it red.
     * @param status the status
     */
    public void setPassWarn(Boolean status) {
        if(status) { pass.setStyle("-fx-background-color: red");
        } else { pass.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5"); }
    }

    /**
     * Sets password 2 warn.
     * If the field does not pass the check, makes it red.
     * @param status the status
     */
    public void setPass2Warn(Boolean status) {
        if(status) { pass2.setStyle("-fx-background-color: red");
        } else { pass2.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5"); }
    }

    /**
     * Sets country warn.
     * If the field does not pass the check, makes it red.
     * @param status the status
     */
    public void setCountryWarn(Boolean status) {
        if(status) { countr.setStyle("-fx-background-color: red");
        } else { countr.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5"); }
    }

    /**
     * Sets birthday warn.
     * If the field does not pass the check, makes it red.
     * @param status the status
     */
    public void setBdWarn(Boolean status) {
        if(status) { bd.setStyle("-fx-background-color: red");
        } else { bd.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5"); }
    }

    /**
     * Clear the fields of registration form.
     */
    public void clear(){
        fname.setText("");
        lname.setText("");
        mail.setText("");
        pass.setText("");
        pass2.setText("");
        countr.setText("");
        bd.setValue(null);
        fname.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        lname.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        mail.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        pass.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        pass2.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        countr.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        bd.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
    }

    /**
     * Show and hides the stage.
     *
     * @param show (true/false) the show
     */
    public void show(Boolean show){
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

    /**
     * Set pane.
     *
     * @param root the root(pane)
     */
    public void setPane(GridPane root){
        root.setPadding(new Insets(80, 150, 100, 150));
        root.setMinSize(300, 300);
        root.setHgap(10);
        root.setVgap(15);
//        root.setGridLinesVisible(true);
    }

    /**
     * Add elements to the pane.
     *
     * @param root the root(pane)
     */
    public void add(GridPane root){
        //object in which are stored values of RadioButtons
        ToggleGroup group = new ToggleGroup();

        Text heading = new Text("Sign up");
        heading.setStyle("-fx-font-size: 40");
        Text text = new Text("Please fill in this form to create an account.");
        text.setStyle("-fx-font-size: 16");
        Text firstname = new Text("First Name:");
        Text lastname = new Text("Last Name:");
        Text email = new Text("E-mail:");
        Text password = new Text("Password (8+):");
        Text password2 = new Text("Repeat password:");
        Text country = new Text("Country:");
        Text birthday = new Text("Date of birth:");
        Text gender = new Text("Your Gender:");
        female = new RadioButton("female");
        female.setToggleGroup(group);
        female.setSelected(true);

        male = new RadioButton("male");
        male.setToggleGroup(group);

        Button login = new Button("<- Log in");
        Button submit = new Button("Submit");
        Button clear = new Button("Clear");

        login.setOnMouseClicked(new ToLoginEvent());
        submit.setOnMouseClicked(new SubmitEventRegestration());
        clear.setOnMouseClicked(new ClearClickEvent());

        root.add(login, 0,0);
        root.add(heading, 1,0,2,1);
        root.add(text,0,1,3,1);
        root.add(firstname, 0 ,2);
        root.add(fname, 1 ,2, 3,1);
        root.add(lastname, 0, 3);
        root.add(lname, 1, 3,3,1);
        root.add(email, 0, 4);
        root.add(mail, 1, 4, 3,1);
        root.add(password, 0, 5);
        root.add(pass, 1, 5,3,1);
        root.add(password2, 0, 6);
        root.add(pass2,1,6,3,1);
        root.add(country,0,7);
        root.add(countr,1,7,3,1);
        root.add(birthday,0,8);
        root.add(bd,1,8,3,1);
        root.add(gender,0,9);
        root.add(female,1,9);
        root.add(male,2,9);
        root.add(clear,1,10);
        root.add(submit,2, 10);
    }

    /**
     * Get text from a field in the form and parse it to the String.
     *
     * @param text the text
     * @return the string
     */
    public String check(TextField text){
        return text.getText().toString();
    }

    @Override
    public void start(Stage stage){
        this.stage = stage;
        //        mail.setMinWidth(300);
        GridPane root = new GridPane();
        setPane(root);
        add(root);

        stage.setScene(new Scene(root, 600, 600));
        stage.setTitle("Chat");
        stage.setResizable(false);
        stage.centerOnScreen();
    }
}
