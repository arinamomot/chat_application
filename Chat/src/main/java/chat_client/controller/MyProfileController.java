package chat_client.controller;

import chat_client.Connection;
import chat_client.Main;
import chat_client.controller.code.CodeStatus;
import chat_client.exception.ConnectionError;
import chat_server.model.UserEntity;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.Date;

public class MyProfileController {
    public Button deleteAccount;
    public Button changeButton;
    public Button changePassBut;
    ObservableList<String> statuses = FXCollections.
            observableArrayList("Online", "Offline", "Busy", "Not disturb", "Be back soon", "Absent");
    ToggleGroup group = new ToggleGroup();
    @FXML
    private ChoiceBox<String> status;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField mail;
    @FXML
    private TextField country;
    @FXML
    private DatePicker bd;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private TextField pass;
    @FXML
    private TextField pass2;

    private String firstname = "";
    private String lastname = "";
    private String country2 = "";
    private String gender = "";
    private String birthDate = "";

    @FXML
    private void initialize() {
        status.setItems(statuses);
        status.setValue("Online");
        male.setToggleGroup(group);
        female.setToggleGroup(group);

    }

    public String getType() {
        if (female.isSelected()) return "f";
        if (male.isSelected()) return "m";
        return null;
    }

    public void setFname(String fname) {
        this.fname.setText(fname);
    }

    public void setLFname(String lname) {
        this.lname.setText(lname);
    }

    public void setMail(String mail) {
        this.mail.setText(mail);
    }

    public void setCountry(String country) {
        this.country.setText(country);
    }

    public void setDate(String date) {
        this.bd.setValue(LocalDate.parse(date));
    }

    public void setGender(String gender) {
        if (gender.equals("F")) female.setSelected(true);
        if (gender.equals("M")) male.setSelected(true);
        if (gender.equals("f")) female.setSelected(true);
        if (gender.equals("m")) male.setSelected(true);

    }


    /**
     * Gets me.
     * Get and show information about user.
     *
     * @param user the user
     */
    public void getMe(UserEntity user) {
        setFname(user.getFirstname());
        firstname = user.getFirstname();
        setLFname(user.getLastname());
        lastname = user.getLastname();
        setMail(user.getMail());
        setCountry(user.getCountry());
        country2 = user.getCountry();
        setGender(user.getGender());
        gender = user.getGender();
        setDate(String.valueOf(user.getBirthday()));
        birthDate = String.valueOf(user.getBirthday());
    }

    /**
     * Delete account function.
     *
     * @throws ConnectionError the connection error
     */
    public void deleteAccountClick() throws ConnectionError {
        Connection.deleteUser();
    }

    /**
     * Clear.
     */
    public void clear() {
        fname.setText("");
        lname.setText("");
        mail.setText("");
        pass.setText("");
        pass2.setText("");
        country.setText("");
        bd.setValue(null);
        fname.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        lname.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        mail.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        pass.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        pass2.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        country.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        bd.setStyle("-fx-background-color: white ;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
    }

    /**
     * Change information.
     *
     * @throws ConnectionError the connection error
     */
    public void changeInformation() throws ConnectionError {
        if ((!fname.getText().equals(firstname)) || (!lname.getText().equals(lastname)) || (!country.getText().equals(country2)) || (!bd.getValue().toString().equals(birthDate))) {
            Connection.changeUserInfo(fname.getText(), lname.getText(), country.getText(), bd.getValue(), getType());
        } else {
            Main.myProfile.show(true);
        }
    }

    /**
     * Change password.
     *
     * @throws ConnectionError the connection error
     */
    public void changePassword() throws ConnectionError {
        if ((!pass.getText().equals("")) && (!pass2.getText().equals(""))) {
            if ((pass.getText().length() < 8) || (pass2.getText().length() < 8) || (pass.getText().equals(pass2.getText())) || (!pass.getText().matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$")) || (!pass2.getText().matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$"))) {
                setPassWarn(true);
                setPass2Warn(true);
                Main.myProfile.show(true);
                Main.errorWindow.controller.setError(CodeStatus.get(418).toString());
                Main.errorWindow.show(true);
                return;
            } else {
                Connection.changePassword(pass.getText(), pass2.getText());
            }
        } else {
            Main.myProfile.show(true);
        }
    }

    /**
     * Sets first name warn
     * If the field does not pass the check, makes it red.
     *
     * @param status the status
     */
    public void setFnameWarn(Boolean status) {
        if (status) {
            fname.setStyle("-fx-background-color: red");
        } else {
            fname.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }

    /**
     * Sets last name warn.
     * If the field does not pass the check, makes it red.
     *
     * @param status the status
     */
    public void setLnameWarn(Boolean status) {
        if (status) {
            lname.setStyle("-fx-background-color: red");
        } else {
            lname.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }

    /**
     * Sets password warn.
     * If the field does not pass the check, makes it red.
     *
     * @param status the status
     */
    public void setPassWarn(Boolean status) {
        if (status) {
            pass.setStyle("-fx-background-color: red");
        } else {
            pass.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }

    /**
     * Sets password 2 warn.
     * If the field does not pass the check, makes it red.
     *
     * @param status the status
     */
    public void setPass2Warn(Boolean status) {
        if (status) {
            pass2.setStyle("-fx-background-color: red");
        } else {
            pass2.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }

    /**
     * Sets country warn.
     * If the field does not pass the check, makes it red.
     *
     * @param status the status
     */
    public void setCountryWarn(Boolean status) {
        if (status) {
            country.setStyle("-fx-background-color: red");
        } else {
            country.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }

    /**
     * Sets birthday warn.
     * If the field does not pass the check, makes it red.
     *
     * @param status the status
     */
    public void setBdWarn(Boolean status) {
        if (status) {
            bd.setStyle("-fx-background-color: red");
        } else {
            bd.setStyle("-fx-background-color: white;-fx-border-color: #c1c1c1; -fx-border-radius: 5");
        }
    }

}
