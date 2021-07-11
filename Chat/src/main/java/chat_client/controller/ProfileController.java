package chat_client.controller;

import chat_client.Main;
import chat_server.model.UserEntity;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class ProfileController {
    ToggleGroup group = new ToggleGroup();
    public Text fname;
    public Text lname;
    public TextField mail;
    public Text country;
    public Text bd;
    public RadioButton male;
    public RadioButton female;

    public void setFname(String fname) {
        this.fname.setText(fname);
    }

    public void setLFname(String lname) {
        this.lname.setText(lname);
    }

    public void setCountry(String country) {
        this.country.setText(country);
    }

    public void setDate(String date) {
        this.bd.setText(date);
    }

    public void setGender(String gender) {
        if (gender.equals("F")) female.setSelected(true);
        if (gender.equals("M")) male.setSelected(true);
        if (gender.equals("f")) female.setSelected(true);
        if (gender.equals("m")) male.setSelected(true);

    }

    @FXML
    private void initialize() {
        male.setToggleGroup(group);
        female.setToggleGroup(group);
    }

    public void getUserInfo(UserEntity user) {
        System.out.println(user);
        setFname(user.getFirstname());
        setLFname(user.getLastname());
        setCountry(user.getCountry());
        setGender(user.getGender());
        setDate(String.valueOf(user.getBirthday()));
    }
}
