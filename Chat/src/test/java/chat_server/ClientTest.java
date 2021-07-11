package chat_server;

import chat_client.Connection;
import chat_client.controller.Request;
import chat_client.controller.code.CodeStatus;
import chat_client.controller.request.*;
import chat_client.exception.ConnectionError;
import chat_client.exception.SendError;
import chat_server.model.GroupEntity;
import chat_server.model.UserEntity;
import chat_server.service.GroupService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    Socket self;
    Client client;
    String token = null;
    String mail = "cvu@mail.com";
    ArrayList<String> groups = new ArrayList<>();
    UserEntity user = null;
    private static class Server implements Runnable {
        @Override
        public void run() {
            try {
                Main.main(new String[]{});
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @BeforeAll
    static void setUp() throws InterruptedException {
        // start server and wait 8 sec to launch and connect
        new Thread(new Server()).start();
        Thread.sleep(8000);
    }

    @BeforeEach
    public void before() throws IOException {
        //connect to server
        System.out.println("Test done");
        self = new Socket("localhost", 9000);
        Connection.setSocket(self);
        client = new Client(self.getInputStream(), self.getOutputStream(), 0, self);
        new Thread(client).start();
    }

    @Test
    void getGroups() throws ConnectionError, SendError {
        //create
        String email = Client.md5Custom(String.valueOf(System.currentTimeMillis())) + "@mail.com";
        String pass = Client.md5Custom(String.valueOf(System.currentTimeMillis()));
        UserEntity user = signup(email, pass);
        //login
        String token = login(user.getMail(), pass);
        //
        GetGroupsRequest request = new GetGroupsRequest();
        request.setTitle("%");
        request.token = token;
        Request answer = client.getGroupsAction(request);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(Connection.gson.toJson(answer));
        JsonArray jsonArr = jo.getAsJsonArray("data");
        ArrayList<GroupEntity> groups = Connection.gson.fromJson(jsonArr, ArrayList.class);
        //ASSERT
        assertEquals(0, groups.size());
        //delete user from db
        DeleteUserRequest requestDelete = new DeleteUserRequest();
        requestDelete.token = token;
        client.deleteUserAction(requestDelete);
    }

    @Test
    void createGroup() throws ConnectionError, SendError {
        //create
        String email = Client.md5Custom(String.valueOf(System.currentTimeMillis())) + "@mail.com";
        String pass = Client.md5Custom(String.valueOf(System.currentTimeMillis()));
        UserEntity user = signup(email, pass);
        //login
        String token = login(user.getMail(), pass);

        //
        CreateGroupRequest request = new CreateGroupRequest();
        String title = Client.md5Custom(String.valueOf(System.currentTimeMillis()));
        request.setTitle(title);
        request.setDescription("test");
        request.setType(true);
        request.token = token;
        Request answer = client.createGroupAction(request, user);
        //ASSERT
        assertNull(answer.error);
        GetGroupsRequest getGroups = new GetGroupsRequest();
        getGroups.setTitle("%");
        getGroups.token = token;
        answer = client.getGroupsAction(getGroups);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(Connection.gson.toJson(answer));
        JsonArray jsonArr = jo.getAsJsonArray("data");
        ArrayList groups = Connection.gson.fromJson(jsonArr, ArrayList.class);
        ArrayList<GroupEntity> realGroups = new ArrayList<>();
        for(Object grp : groups) realGroups.add(Connection.gson.fromJson(Connection.gson.toJson(grp), GroupEntity.class));
        //ASSERT
        assertEquals(realGroups.size(), GroupService.getInstance().getUserGroups(user.getId()).size());

        //delete user from db
        DeleteUserRequest requestDelete = new DeleteUserRequest();
        requestDelete.token = token;
        client.deleteUserAction(requestDelete);

    }

    @Test
    void getMeTest() throws ConnectionError, SendError {
        //create
        String email = Client.md5Custom(String.valueOf(System.currentTimeMillis())) + "@mail.com";
        String pass = Client.md5Custom(String.valueOf(System.currentTimeMillis()));
        UserEntity user = signup(email, pass);
        //login
        String token = login(user.getMail(), pass);

        //
        GetMeRequest request = new GetMeRequest();
        request.token = null;
        Request answer = client.getMe(request);
        //ASSERT
        assertNotNull(answer.error);
        request = new GetMeRequest();
        request.token = token;
        answer = client.getMe(request);
        //ASSERT
        assertNull(answer.error);
        UserEntity userFromDB = Connection.gson.fromJson((Connection.gson.toJson(answer.data)), UserEntity.class);
        System.out.println(user.toString());
        System.out.println(userFromDB.toString());
        //ASSERT
        assertEquals(user.toString(), userFromDB.toString());

        //delete user from db
        DeleteUserRequest requestDelete = new DeleteUserRequest();
        requestDelete.token = token;
        client.deleteUserAction(requestDelete);
    }

    @Test
    void logoutAction() throws ConnectionError, SendError {
        loginAction();
        LogOutRequest req = new LogOutRequest();
        req.token = this.token;
        Request res = client.logoutAction(req);
        assertNull(res.error);

    }

    void loginAction() throws ConnectionError, SendError {
        String email = Client.md5Custom(String.valueOf(System.currentTimeMillis())) + "@mail.com";
        String pass = Client.md5Custom(String.valueOf(System.currentTimeMillis()));
        UserEntity user = signup(email, pass);
        LoginRequest req = new LoginRequest();
        req.setMail(user.getMail());
        req.setPassword("12345678");
        Request res = client.loginAction(req);

        assertEquals(res.error, CodeStatus.get(409).toString());

        req.setPassword(pass);
        res = client.loginAction(req);
        assertNull(res.error);

        DeleteUserRequest request = new DeleteUserRequest();
        request.token = res.token;
        client.deleteUserAction(request);
    }

    @Test
    void createUserAction() {
        // create user test
        CreateUserRequest req = new CreateUserRequest();
        String mail = Client.md5Custom(String.valueOf(System.currentTimeMillis())) + "@mail.com";
        req.setMail(mail);
        // l2kjnf023j9r923jrm2039mr023@mail.com
        req.setBd("2000-10-10");
        req.setGender("M");
        req.setCountry("CZ");
        req.setPassword("12345678");
        req.setLname("Mail");
        req.setFname("Google");
        Request res = null;
        try {
            res = client.createUserAction(req);
        } catch (NullPointerException ignored) {
            fail();
        }
        assertNull(res.error);
        String json = Connection.gson.toJson(res.data);
        UserEntity user = Connection.gson.fromJson(json, UserEntity.class);
        assertEquals(mail, user.getMail());

        //delete created user
        String token = login(mail, "12345678");
        DeleteUserRequest request = new DeleteUserRequest();
        request.token = token;
        client.deleteUserAction(request);

    }

    UserEntity signup(String mail,String pass) {
        // create user test
        CreateUserRequest req = new CreateUserRequest();
        req.setFname("Google");
        req.setLname("Mail");
        req.setMail(mail);
        req.setPassword(pass);
        // l2kjnf023j9r923jrm2039mr023@mail.com
        req.setBd("2000-10-10");
        req.setCountry("CZ");
        req.setGender("M");
        Request res = null;
        res = client.createUserAction(req);
        assertNull(res.error);
        String json = Connection.gson.toJson(res.data);
        return Connection.gson.fromJson(json, UserEntity.class);
    }

    String login(String mail, String pass) {
        LoginRequest request = new LoginRequest();
        request.setMail(mail);
        request.setPassword(pass);
        Request answer = client.loginAction(request);
        return answer.token;
    }

    UserEntity getMe(String token) {
        GetMeRequest request = new GetMeRequest();
        request.token = token;
        Request answer = client.getMe(request);
        String json = Connection.gson.toJson(answer.data);
        return Connection.gson.fromJson(json, UserEntity.class);
    }

    @Test
    void addMemberTest(){
        //create user
        String email = Client.md5Custom(String.valueOf(System.currentTimeMillis())) + "@mail.com";
        String pass = Client.md5Custom(String.valueOf(System.currentTimeMillis()));
        UserEntity user = signup(email, pass);
        //login
        String token = login(email, pass);
        //create group
        GroupEntity group = createGroup(token, "test" + Client.md5Custom(String.valueOf(System.currentTimeMillis())));
        AddMemberRequest request = new AddMemberRequest();
        request.setGroup_id(group.getId());
        //create new user
        UserEntity newUser = signup( "new" + email, pass);
        request.setUser_id(newUser.getId());
        request.setGroup_type(group.getType());
        request.token = token;
        Request result = client.addMemberAction(request);

//        System.out.println(GroupService.getInstance().getGroupById(group.getId()));

        //delete created users
        DeleteUserRequest requestDelete = new DeleteUserRequest();
        requestDelete.token = token;
        client.deleteUserAction(requestDelete);
        token = login("new" + email, pass);
        requestDelete = new DeleteUserRequest();
        requestDelete.token = token;
        client.deleteUserAction(requestDelete);
    }

    GroupEntity createGroup(String token, String title) {
        CreateGroupRequest request = new CreateGroupRequest();
        request.setTitle(title);
        request.setDescription("test");
        request.setType(true);
        request.token = token;
        Request answer = client.createGroupAction(request, getMe(token));
        assertNull(answer.error);
        return Connection.gson.fromJson(Connection.gson.toJson(answer.data), GroupEntity.class);
    }
}