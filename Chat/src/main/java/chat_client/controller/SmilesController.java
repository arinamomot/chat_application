package chat_client.controller;

import chat_client.Connection;
import chat_client.Main;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SmilesController {
    public Button smilingEyes;
    public Integer recipient;
    public Button faceWithTearsOfJoyFace;
    public Button smilingFaceWithSunglasses;
    public Button cryingFace;
    public Button beatingHeart;
    public Button angryFace;
    public Button okHandEmoji;
    public Button smilingFaceWithHeartEyes;
    public Button sleepingFace;
    public Button blowingKissFace;
    public Button screamingInFearFace;
    public Button thumbsUp;

    public void sendSmile(MouseEvent mouseEvent) throws ConnectionError, SendError {
        String id = mouseEvent.getPickResult().getIntersectedNode().getId();
        System.out.println(id);
        Connection.sendMessage("smile#" + id, recipient );
        Main.smilesWindow.show(false);
    }
}
