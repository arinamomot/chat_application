package chat_client;

import chat_client.controller.SmilesController;
import chat_client.view.ErrorWindow;
import chat_client.view.SmilesWindow;
import chat_client.view.StatusBarWindow;
import chat_client.view.SuccessfulRegistation;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;


public class Main extends Application {

    public static LogIn logInView = null;
    public static final Logger LOGGER = Logger.getGlobal();

    public static StatusBarWindow statusbar = null;
    public static Registration registrationView = null;
    public static SuccessfulRegistation success = null;
    public static ContactList contactList = null;
    public static HashMap<String, Application> messages = null;
    public static Socket self;
    public static MyProfile myProfile = null;
    public static Profile profile = null;
    public static CreateGroup createGroup = null;
    public static GroupSettings groupSettings = null;
    public static AddNewMember addNewMember = null;
    public static ErrorWindow errorWindow = null;
    public static SmilesWindow smilesWindow = null;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
/*
        M->C<-V
        client(frontend) -> client(connection)
        client(connection) -> server(run)//magic
        server(run) -> client(connection)
         */
    public static void main(String[] args) {
        //Socket creation
        try {
            self = new Socket("localhost", 9000);
            Connection.setSocket(self);
        } catch (IOException  error) {
            System.out.println("Cannot connect" + error.getMessage());
        }
//        smiles.add("")//hash
        logInView = new LogIn();
        statusbar = new StatusBarWindow();
        registrationView = new Registration();
        messages = new HashMap<String, Application>();
        contactList = new ContactList();
        success = new SuccessfulRegistation();
        myProfile = new MyProfile();
        profile = new Profile();
        createGroup = new CreateGroup();
        groupSettings = new GroupSettings();
        addNewMember = new AddNewMember();
        errorWindow = new ErrorWindow();
        smilesWindow = new SmilesWindow();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage reg = new Stage();
        registrationView.start(reg);
        reg.setOnCloseRequest((e) -> {
            registrationView.show(false);
            Main.shutdown();
        });
        Stage log = new Stage();
        logInView.start(log);
        log.setOnCloseRequest((e) -> {
            logInView.show(false);
            Main.shutdown();
        });
        Stage bar = new Stage();
        statusbar.start(bar);
        bar.setOnCloseRequest((e) -> {
            statusbar.show(false);
            Main.shutdown();
        });
        Stage cont = new Stage();
        contactList.start(cont);
        cont.setOnCloseRequest((e) -> {
            contactList.show(false);
            Main.shutdown();
        });
        Stage suc = new Stage();
        success.start(suc);
        suc.setOnCloseRequest((e) -> {
            success.show(false);
            Main.shutdown();
        });
        Stage mp = new Stage();
        myProfile.start(mp);
        mp.setOnCloseRequest((e) -> {
            myProfile.show(false);
            Main.shutdown();
        });
        Stage prof = new Stage();
        profile.start(prof);
        prof.setOnCloseRequest((e) -> {
            profile.show(false);
            Main.shutdown();
        });
        Stage cg = new Stage();
        createGroup.start(cg);
        cg.setOnCloseRequest((e) -> {
            createGroup.show(false);
            Main.shutdown();
        });
        Stage gs = new Stage();
        groupSettings.start(gs);
        gs.setOnCloseRequest((e) -> {
            groupSettings.show(false);
            Main.shutdown();
        });
        Stage addm = new Stage();
        addNewMember.start(addm);
        addm.setOnCloseRequest((e) -> {
            addNewMember.show(false);
            Main.shutdown();
        });
        Stage err = new Stage();
        errorWindow.start(err);
        err.setOnCloseRequest((e) -> {
            errorWindow.show(false);
            Main.shutdown();
        });
        Stage smilew = new Stage();
        smilesWindow.start(smilew);
        smilew.setOnCloseRequest((e) -> {
            smilesWindow.show(false);
            Main.shutdown();
        });
    }

    /**
     * Close all windows.
     */
    public static void closeAll() {
        Main.myProfile.show(false);
        Main.profile.show(false);
        Main.contactList.show(false);
        Main.groupSettings.show(false);
        Main.logInView.show(false);
        Main.statusbar.show(false);
        Main.registrationView.show(false);
        Main.createGroup.show(false);
        Main.addNewMember.show(false);
        Main.success.show(false);
        Main.profile.show(false);
        Main.errorWindow.show(false);
        Main.smilesWindow.show(false);
    }
    public static void shutdown() {
        if(Main.registrationView.isOpen()) return;
        if(Main.logInView.isOpen()) return;
        if(Main.myProfile.isOpen()) return;
        if(Main.smilesWindow.isOpen()) return;
        if(Main.errorWindow.isOpen()) return;
        if(Main.addNewMember.isOpen()) return;
        if(Main.groupSettings.isOpen()) return;
        if(Main.createGroup.isOpen()) return;
        if(Main.statusbar.isOpen()) return;
        if(Main.contactList.isOpen()) return;
        if(Main.success.isOpen()) return;
        if(Main.profile.isOpen()) return;
        Connection.stop();
    }

}
