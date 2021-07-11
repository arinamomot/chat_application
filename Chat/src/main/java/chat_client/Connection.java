package chat_client;

import chat_client.controller.Request;
import chat_client.controller.request.*;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import chat_server.model.GroupEntity;
import chat_server.model.MembershipEntity;
import chat_server.model.MessageEntity;
import chat_server.model.UserEntity;
import com.google.gson.*;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Connection.
 * Sends requests to the server and receives responses from it.
 */
public class Connection {
    private static Socket socket = null;
    private static PrintWriter writer;
    private static BufferedReader reader;
    public static boolean end = false;
    private static Thread core;
    /**
     * The constant gson.
     */
    public static Gson gson;
    private static String token;


    public static void stop() {
        Main.LOGGER.info("Closing application...");
        end = true;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        System.exit(0);
    }

    /**
     * Logout.
     */
    public static void logout() {
        LogOutRequest req = new LogOutRequest();
        req.token = token;
        String json = gson.toJson(req);
        writer.println(json);
        writer.flush();
    }

    /**
     * Login.
     *
     * @param mail     the mail
     * @param password the password
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void login(String mail,
                             String password) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        LoginRequest request = new LoginRequest();
        request.setMail(mail);
        request.setPassword(password);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Create user.
     *
     * @param fname    the fname
     * @param lname    the lname
     * @param mail     the mail
     * @param password the password
     * @param country  the country
     * @param bd       the bd
     * @param gender   the gender
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void createUser(String fname, String lname, String mail, String password, String country, String bd, String gender) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        CreateUserRequest request = new CreateUserRequest();
        request.setFname(fname);
        request.setLname(lname);
        request.setMail(mail);
        request.setPassword(password);
        request.setCountry(country);
        request.setBd(bd);
        request.setGender(gender);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Change user info.
     *
     * @param fname   the fname
     * @param lname   the lname
     * @param country the country
     * @param bd      the bd
     * @param gender  the gender
     * @throws ConnectionError the connection error
     */
    public static void changeUserInfo(String fname, String lname, String country, LocalDate bd, String gender) throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        ChangeUserInfoRequest request = new ChangeUserInfoRequest();
        request.token = token;
        if (!fname.matches("^[a-zA-Z]{2,40}$")) {
            Main.myProfile.controller.setFnameWarn(true);
            return;
        } else {
            Main.myProfile.controller.setFnameWarn(false);
        }
        if (!lname.matches("^[a-zA-Z]{2,40}$")) {
            Main.myProfile.controller.setLnameWarn(true);
            return;
        } else {
            Main.myProfile.controller.setLnameWarn(false);
        }
        if (!country.matches("^[a-zA-Z]{2,40}$")) {
            Main.myProfile.controller.setCountryWarn(true);
            return;
        } else {
            Main.myProfile.controller.setCountryWarn(false);
        }
        LocalDate now = LocalDate.now();
        if ((bd == null) || (bd.isEqual(now)) || (bd.isAfter(now))) {
            Main.myProfile.controller.setBdWarn(true);
            return;
        } else {
            Main.myProfile.controller.setBdWarn(false);
        }
        request.setFname(fname);
        request.setLname(lname);
        request.setCountry(country);
        request.setBd(bd);
        request.setGender(gender);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets messages.
     *
     * @param id the id
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void getMessages(Integer id) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetMessagesRequest request = new GetMessagesRequest();
        request.token = token;
        request.setId(id);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets me.
     *
     * @throws ConnectionError the connection error
     */
    public static void getMe() throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetMeRequest request = new GetMeRequest();
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets users.
     *
     * @param fname the fname
     * @param lname the lname
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void getUsers(String fname, String lname) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetUsersRequest request = new GetUsersRequest();
        request.setFName(fname);
        request.setLName(lname);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    public static void getUserInfo(Integer user_id) throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetUserInfoRequest request = new GetUserInfoRequest();
        request.setId(user_id);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets rights.
     *
     * @param group_id the group id
     * @param user_id  the user id
     * @throws ConnectionError the connection error
     */
    public static void getRights(Integer group_id, Integer user_id) throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetRightsRequest request = new GetRightsRequest();
        request.setGroup_id(group_id);
        request.setUser_id(user_id);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Create group.
     *
     * @param title       the title
     * @param description the description
     * @param type        the type
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void createGroup(String title, String description, Boolean type) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        CreateGroupRequest request = new CreateGroupRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setType(type);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Delete group.
     *
     * @param id the id
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void deleteGroup(Integer id) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        DeleteGroupRequest request = new DeleteGroupRequest();
        request.setId(id);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Delete user.
     *
     * @throws ConnectionError the connection error
     */
    public static void deleteUser() throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        DeleteUserRequest request = new DeleteUserRequest();
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Delete message.
     *
     * @param id       the id
     * @param group_id the group id
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void deleteMessage(Integer id, Integer group_id) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        DeleteMessageRequest request = new DeleteMessageRequest();
        request.setId(id);
        request.setGroup_id(group_id);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }


    /**
     * Edit message.
     *
     * @param id       the id
     * @param group_id the group id
     * @param text     the text
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void editMessage(Integer id, Integer group_id, String text) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        EditMessageRequest request = new EditMessageRequest();
        request.setId(id);
        request.setGroup_id(group_id);
        request.setText(text);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Change rights.
     *
     * @param user_id  the user id
     * @param group_id the group id
     * @param rights   the rights
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void changeRights(Integer user_id, Integer group_id, String rights) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        ChangeRightsRequest request = new ChangeRightsRequest();
        request.setUser_id(user_id);
        request.setGroup_id(group_id);
        request.setRights(rights);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Send message.
     *
     * @param text      the text
     * @param recipient the recipient
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void sendMessage(String text, Integer recipient) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        SendMessageRequest request = new SendMessageRequest();
        request.token = token;
        request.setText(text);
        request.setRecipient(recipient);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets groups.
     *
     * @throws ConnectionError the connection error
     */
    public static void getGroups() throws ConnectionError {
        if (socket == null) throw new ConnectionError();
        GetGroupsRequest request = new GetGroupsRequest();
        request.setTitle("%");
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets group.
     *
     * @param id the id
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void getGroup(Integer id) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetGroupRequest request = new GetGroupRequest();
        request.setId(id);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets in my groups.
     *
     * @param title the title
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void getInMyGroups(String title) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetInMyGroupsRequest request = new GetInMyGroupsRequest();
        request.setTitle(title);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Gets creator.
     *
     * @param id the id
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void getCreator(Integer id) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        GetCreatorRequest request = new GetCreatorRequest();
        request.setId(id);
        request.token = token;
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Add member.
     *
     * @param user_id  the user id
     * @param group_id the group id
     * @param type     the type
     * @throws ConnectionError the connection error
     * @throws SendError       the send error
     */
    public static void addMember(Integer user_id, Integer group_id, Boolean type) throws ConnectionError, SendError {
        if (socket == null) {
            throw new ConnectionError();
        }
        AddMemberRequest request = new AddMemberRequest();
        request.token = token;
        request.setUser_id(user_id);
        request.setGroup_id(group_id);
        request.setGroup_type(type);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Sets socket.
     *
     * @param self the self
     * @throws IOException the io exception
     */
    public static void setSocket(Socket self) throws IOException {
        gson = new Gson();
        socket = self;
        reader = new BufferedReader(new InputStreamReader(self.getInputStream()));
        writer = new PrintWriter(self.getOutputStream());
        core = new Thread(new Listener());
        core.start();
        Main.LOGGER.info("Connected to server");

    }

    /**
     * Remove member.
     *
     * @param group_id the group id
     * @param user_id  the user id
     * @throws ConnectionError the connection error
     */
    public static void removeMember(Integer group_id, Integer user_id) throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        RemoveMemberRequest request = new RemoveMemberRequest();
        request.token = token;
        request.setUser_id(user_id);
        request.setGroup_id(group_id);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Change group info.
     *
     * @param title       the title
     * @param description the description
     * @param group_id    the group id
     * @throws ConnectionError the connection error
     */
    public static void changeGroupInfo(String title, String description, Integer group_id) throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        ChangeGroupInfoRequest request = new ChangeGroupInfoRequest();
        request.token = token;
        request.setTitle(title);
        request.setDescription(description);
        request.setId(group_id);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    /**
     * Change password.
     *
     * @param pass  the pass
     * @param pass2 the pass 2
     * @throws ConnectionError the connection error
     */
    public static void changePassword(String pass, String pass2) throws ConnectionError {
        if (socket == null) {
            throw new ConnectionError();
        }
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.token = token;

        if ((!pass.matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$")) || (pass.length() < 8) || (!pass2.matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$")) || (pass2.length() < 8) || (pass.equals(pass2))) {
            Main.myProfile.controller.setPassWarn(true);
            Main.myProfile.controller.setPass2Warn(true);
            return;
        } else {
            Main.myProfile.controller.setPassWarn(false);
            Main.myProfile.controller.setPass2Warn(false);
        }
        request.setPassword(pass);
        request.setPasswordNew(pass2);
        String json = gson.toJson(request);
        writer.println(json);
        writer.flush();
    }

    private static class Listener implements Runnable {

        @Override
        public void run() {
            while (!end) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (line == null) {
                    Main.LOGGER.severe("Could not get message from server");
                }
                Request request = gson.fromJson(line, Request.class);
                switch (request.action) {
                    case "login":
                        if (request.error != null) {
                            Platform.runLater(() -> {
                                Main.statusbar.show(false);
                                Main.logInView.show(true);
                                Main.logInView.setPassWarn(true);
                                Main.LOGGER.severe("Failed to log in");
                            });
                            break;
                        }
                        Main.LOGGER.info("Successfull login");
                        token = request.token;
                        Platform.runLater(() -> {
                            Main.contactList.show(true);
                            Main.statusbar.show(false);
                        });
                        try {
                            getGroups();
                        } catch (ConnectionError error) {
                            error.printStackTrace();
                        }
                        break;
                    case "createUser":
                        Main.LOGGER.info("Successfull registration");
                        break;
                    case "logout":
                        if (request.error == null) {
                            Platform.runLater(() -> {
                                Main.closeAll();
                                Main.logInView.show(true);
                                Main.LOGGER.info("Logout successfully");
                            });
                        }
                        token = null;
                        break;
                    case "getGroups":
                        handlerGetGroups(request);
                        break;
                    case "getMessages":
                        handlerGetMessages(request);
                        break;
                    case "getUsers":
                        handlerGetUsers(request);
                        break;
                    case "getMe":
                        handlerGetMe(request);
                        break;
                    case "createGroup":
                        handlerCreateGroup(request);
                        break;
                    case "changeUserInfo":
                        handlerChangeUserInfo(request);
                        break;
                    case "changePass":
                        handlerChangePassword(request);
                        break;
                    case "changeGroupInfo":
                        handlerChangeGroupInfo(request);
                        break;
                    case "deleteMessage":
                        break;
                    case "getUserInfo":
                        handlerGetUserInfo(request);
                        break;
                    case "getInMyGroups":
                        handlerGetInMyGroups(request);
                        break;
                    case "getRights":
                        try {
                            handlerGetRights(request);
                        } catch (ConnectionError connectionError) {
                            connectionError.printStackTrace();
                        }
                        break;
                    case "deleteUser":
                        handlerDeleteUser(request);
                        break;
                    case "deleteGroup":
                        handlerDeleteGroup(request);
                        break;
                    case "editMessage":
                        break;
                    case "getGroup":
                        handlerGetGroup(gson.fromJson(line, GetGroupRequest.class));
                        break;
                    case "getCreator":
                        handlerGetCreator(request);
                        break;
                    case "sendMessage":
                        handlerSendMessage(request);
                        break;
                    case "addMember":
                        handlerAddMember(request);
                        break;
                }
            }
        }

        private void handlerGetUserInfo(Request request) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
            JsonObject j_user = jo.getAsJsonObject("data");
            UserEntity user = gson.fromJson(j_user, UserEntity.class);
            Platform.runLater(() -> {
            Main.profile.controller.getUserInfo(user);
            });
        }


        private void handlerGetInMyGroups(Request request) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
            JsonArray jsonArr = jo.getAsJsonArray("data");
            ArrayList<GroupEntity> jsonObjList = gson.fromJson(jsonArr, ArrayList.class);
            ArrayList<GroupEntity> groups = new ArrayList<>();
            for (Object group : jsonObjList)
                groups.add(Connection.gson.fromJson(Connection.gson.toJson(group), GroupEntity.class));
            Platform.runLater(() -> {
                Main.contactList.controller.clearList();
//                Main.addNewMember.controller.clear();
                for (GroupEntity group : groups) {
                    Main.contactList.controller.addContact(group);
                }
            });
            Main.LOGGER.info("successfully found");
        }


        private void handlerSendMessage(Request request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Send message error");
                });
            } else {
                Platform.runLater(() -> {
                    Main.LOGGER.info("Successfully sent");
                    Main.contactList.controller.messageText.setText("");
                    Main.contactList.show(true);
                });
//                    SendMessageRequest.SendMessageData data = gson.fromJson(gson.toJson(request.data), SendMessageRequest.SendMessageData.class);
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
                JsonObject j_message = jo.getAsJsonObject("data");
                MessageEntity message = gson.fromJson(j_message, MessageEntity.class);
                Platform.runLater(() -> {
                    try {
                        Main.contactList.controller.addMessage(message);
                    } catch (ConnectionError | IOException connectionError) {
                        connectionError.printStackTrace();
                    }
                });
            }

        }

        private void handlerGetRights(Request request) throws ConnectionError {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Get rights error");
                });
            } else {
                Main.groupSettings.controller.getRights(request.data.toString());
            }
        }

        private void handlerChangePassword(Request request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.myProfile.controller.setPassWarn(true);
                    Main.myProfile.controller.setPass2Warn(true);
                    Main.myProfile.show(true);
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Password was not changed");
                });
            } else {
                Platform.runLater(() -> {
                    Main.LOGGER.info("Successfully change");
                    Main.myProfile.show(false);
                    Main.myProfile.controller.clear();
                    Main.contactList.show(true);
                });
            }
        }

        private void handlerChangeGroupInfo(Request request) {
            if (request.error != null || request.data == null) {
                Platform.runLater(() -> {
                    Main.groupSettings.show(true);
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Group was not changed");
                });
            } else {
                Platform.runLater(() -> {
                    Main.LOGGER.info("Successfully change");
                    Main.groupSettings.show(false);
                    Main.groupSettings.controller.clear();
                    try {
                        Connection.getGroups();
                    } catch (ConnectionError connectionError) {
                        connectionError.printStackTrace();
                    }
                    Main.contactList.show(true);
                });
            }
        }

        private void handlerChangeUserInfo(Request request) {
            if (request.error != null || request.data == null) {
                Platform.runLater(() -> {
                    Main.myProfile.show(true);
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Users info was not changed");
                });
            } else {
                Platform.runLater(() -> {
                    Main.LOGGER.info("Successfully change");
                    Main.myProfile.show(false);
                    Main.myProfile.controller.clear();
                    Main.contactList.show(true);
                });
            }
        }

        private void handlerAddMember(Request request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.addNewMember.show(false);
                    Main.groupSettings.show(true);
                    Main.contactList.show(true);
                    Main.statusbar.show(false);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Member was not added");
                });
            } else {
                AddMemberRequest.AddMemberData data = gson.fromJson(gson.toJson(request.data), AddMemberRequest.AddMemberData.class);
                try {
                    getGroup(data.getGroup_id());
                } catch (ConnectionError | SendError connectionError) {
                    connectionError.printStackTrace();
                }
                Main.LOGGER.info("Successfully added");
            }
        }

        private void handlerGetCreator(Request request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.contactList.show(true);
                    Main.groupSettings.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Get creator error");
                });
            } else {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
                JsonObject j_user = jo.getAsJsonObject("data");
                UserEntity user = gson.fromJson(j_user, UserEntity.class);
                Platform.runLater(() -> {
                    Main.groupSettings.controller.getCreator(user);
                });
            }
        }

        private void handlerDeleteGroup(Request request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.contactList.show(true);
                    Main.groupSettings.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Group was not deleted");
                });
            } else {
                Platform.runLater(() -> {
                    try {
                        Connection.getGroups();
                        Main.groupSettings.show(false);
                        Main.contactList.controller.titleHeader.setText("");
                        Main.contactList.controller.clearMessages();
                    } catch (ConnectionError connectionError) {
                        connectionError.printStackTrace();
                    }
                });
                Main.LOGGER.info("Successfully deleted");
            }
        }

        private void handlerGetGroup(GetGroupRequest request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Get group error");
                });
            } else {
                GroupEntity group = gson.fromJson(gson.toJson(request.data), GroupEntity.class);
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
                JsonArray jsonArr = jo.getAsJsonArray("members");
                ArrayList jsonObjList = gson.fromJson(jsonArr, ArrayList.class);
                Set<MembershipEntity> members = new HashSet<>();
                for (Object member : jsonObjList)
                    members.add(Connection.gson.fromJson(Connection.gson.toJson(member), MembershipEntity.class));
                Platform.runLater(() -> {
                    try {
                        Main.groupSettings.controller.getGroup(group, members);
                    } catch (ConnectionError connectionError) {
                        connectionError.printStackTrace();
                    }
                    Main.statusbar.show(false);
                });
            }
        }

        private void handlerDeleteUser(Request request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.myProfile.show(true);
                    Main.contactList.show(true);
                    Main.LOGGER.severe("User was not deleted");
                });
            }
            token = null;
            Platform.runLater(() -> {
                Main.closeAll();
                Main.logInView.show(true);
            });
            Main.LOGGER.info("Successfully deleted");
        }

        private void handlerCreateGroup(Request request) {
            Platform.setImplicitExit(false);
            if (request.error != null || request.data == null) {
                Platform.runLater(() -> {
                    Main.createGroup.show(true);
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Group was not created");
                });
            } else {
                Platform.runLater(() -> {
                    Main.LOGGER.info("Successful creation");
                    Main.createGroup.show(false);
                    Main.statusbar.show(false);
                    Main.contactList.show(true);
                    CreateGroupRequest.CreateGroupData data = gson.fromJson(gson.toJson(request.data), CreateGroupRequest.CreateGroupData.class);
                    Main.contactList.controller.addContact(data.getGroup());

                });
            }


        }

        private void handlerGetMe(Request request) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
            JsonObject j_user = jo.getAsJsonObject("data");
            UserEntity user = gson.fromJson(j_user, UserEntity.class);
            Main.myProfile.controller.getMe(user);
            Main.groupSettings.controller.getMe(user);
        }

        private void handlerGetMessages(Request request) {
            if (request.error != null) {
                Platform.runLater(() -> {
                    Main.contactList.show(true);
                    Main.errorWindow.controller.setError(request.error);
                    Main.errorWindow.show(true);
                    Main.LOGGER.severe("Get messages error");
                });
            } else {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
                JsonArray jsonArr = jo.getAsJsonArray("data");
                ArrayList<MessageEntity> jsonObjList = gson.fromJson(jsonArr, ArrayList.class);
                ArrayList<MessageEntity> messages = new ArrayList<>();
                for (Object message : jsonObjList)
                    messages.add(Connection.gson.fromJson(Connection.gson.toJson(message), MessageEntity.class));
                Platform.runLater(() -> {
                    Main.contactList.controller.clearMessages();
                    for (MessageEntity message : messages) {
                        try {
                            Main.contactList.controller.addMessage(message);
                        } catch (ConnectionError | IOException connectionError) {
                            connectionError.printStackTrace();
                        }
                    }
                });
            }
        }


        private void handlerGetUsers(Request request) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
            JsonArray jsonArr = jo.getAsJsonArray("data");
            ArrayList<UserEntity> jsonObjList = gson.fromJson(jsonArr, ArrayList.class);
            ArrayList<UserEntity> users = new ArrayList<>();
            for (Object user : jsonObjList)
                users.add(Connection.gson.fromJson(Connection.gson.toJson(user), UserEntity.class));

            Platform.runLater(() -> {
                for (UserEntity user : users) {
                    Main.addNewMember.controller.getUser(user);
                }
            });

        }

        /**
         * Handler get groups.
         *
         * @param request the request
         */
        public static void handlerGetGroups(Request request) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(request));
            JsonArray jsonArr = jo.getAsJsonArray("data");
            ArrayList<GroupEntity> jsonObjList = gson.fromJson(jsonArr, ArrayList.class);
            ArrayList<GroupEntity> realGroups = new ArrayList<>();
            for (Object grp : jsonObjList)
                realGroups.add(Connection.gson.fromJson(Connection.gson.toJson(grp), GroupEntity.class));
            Platform.runLater(() -> {
                Main.contactList.controller.clearList();
                realGroups.forEach((e) -> Main.contactList.controller.addContact(e));
            });

        }
    }
}

