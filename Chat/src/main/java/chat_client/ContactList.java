package chat_client;

import chat_client.controller.Contacts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;


public class ContactList extends Application {
    public Contacts controller;
    private Stage stage;
    public static ArrayList<String> smiles = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception {
        smiles.add("smilingEyes");
        smiles.add("angryFace");
        smiles.add("anguishedFace");
        smiles.add("astonishedFace");
        smiles.add("beamingFace");
        smiles.add("beatingHeart");
        smiles.add("blowingKissFace");
        smiles.add("confusedFace");
        smiles.add("cryingFace");
        smiles.add("faceWithTearsOfJoyFace");
        smiles.add("okHandEmoji");
        smiles.add("relievedFace");
        smiles.add("savoringFoodFace");
        smiles.add("screamingInFearFace");
        smiles.add("sleepingFace");
        smiles.add("smilingFaceWithHeartEyes");
        smiles.add("smilingFaceWithSunglasses");
        smiles.add("thumbsDown");
        smiles.add("thumbsUp");
        //todo
        this.stage = stage;
        FXMLLoader root = new FXMLLoader(getClass().getResource("contacts.fxml"));
        Scene scene = root.load();
        stage.setScene(scene);
        stage.setMaximized(true);
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
