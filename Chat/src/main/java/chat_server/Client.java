package chat_server;

import chat_client.controller.Request;
import chat_client.controller.code.CodeStatus;
import chat_client.controller.request.*;
import chat_server.model.*;
import chat_server.service.GroupService;
import chat_server.service.MemberService;
import chat_server.service.MessageService;
import chat_server.service.UserService;
import com.google.gson.Gson;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.metamodel.MapAttribute;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static chat_server.Main.em;

/**
 * The type Client.
 */
public class Client implements Runnable {
    private BufferedReader input;
    private PrintWriter output;
    private int id;
    private Socket client;
    private boolean active;
    private UserService userService;
    private MemberService memberService;
    private GroupService groupService;
    private MessageService messageService;
    private static Gson gson = new Gson();

    /**
     * Instantiates a new Client.
     *
     * @param is     InputStream
     * @param os     OutputStream
     * @param id     Id of new client(user)
     * @param client Socket of client(user)
     */
    public Client(InputStream is, OutputStream os, int id, Socket client) {
        input = new BufferedReader(new InputStreamReader(is));
        output = new PrintWriter(os);
        this.client = client;
        this.id = id;
    }

    @Override
    public void run() {
        active = true;
        String json;
        userService = UserService.getInstance();
        groupService = GroupService.getInstance();
        memberService = MemberService.getInstance();
        messageService = MessageService.getInstance();
        while (active) {
            try {
                json = input.readLine();
                Request request = gson.fromJson(json, Request.class);
                UserEntity user = null;
                if (request.token != null) {
                    if (Main.active_users.containsKey(request.token)) {
                        int userId = Main.active_users.get(request.token);
                        user = Main.em.find(UserEntity.class, userId);
                    }
                }
                Request answer = null;
                switch (request.action) {
                    case "login":
                        answer = loginAction(request);
                        break;
                    case "createUser":
                        answer = createUserAction(request);
                        break;
                    case "getUsers":
                        answer = getUsersAction(request);
                        break;
                    case "getGroups":
                        answer = getGroupsAction(request);
                        break;
                    case "getGroup":
                        answer = getGroupAction(request);
                        break;
                    case "getCreator":
                        answer = getCreatorAction(request);
                        break;
                    case "sendMessage":
                        answer = sendMessageAction(request);
                        break;
                    case "getMessages":
                        answer = getMessagesAction(request);
                        break;
                    case "logout":
                        answer = logoutAction(request);
                        break;
                    case "createGroup":
                        answer = createGroupAction(request, user);
                        break;
                    case "getInMyGroups":
                        answer = getInMyGroupsAction(request);
                        break;
                    case "getMe":
                        answer = getMe(request);
                        break;
                    case "getUserInfo":
                        answer = getUserInfoAction(request);
                        break;
                    case "deleteUser":
                        answer = deleteUserAction(request);
                        break;
                    case "deleteGroup":
                        answer = deleteGroupAction(request);
                        break;
                    case "changeUserInfo":
                        answer = changeUserInfoAction(request);
                        break;
                    case "changeRights":
                        answer = changeRightsAction(request);
                        break;
                    case "changePass":
                        answer = changePasswordAction(request);
                        break;
                    case "changeGroupInfo":
                        answer = changeGroupInfoAction(request);
                        break;
                    case "addMember":
                        answer = addMemberAction(request);
                        break;
                    case "removeMember":
                        answer = removeMemberAction(request);
                        break;
                    case "getRights":
                        answer = getRightsAction(request);
                        break;
                    default:
                        Main.LOGGER.info("Request from client #" + this.id + ": " + gson.toJson(request));
                }
                if (answer != null) send(answer);

            } catch (IOException e) {
                Main.LOGGER.info("Client " + id + " disconnected");
                active = false;
            }
        }
    }

    private Request getUserInfoAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GetUserInfoRequest.GetUserInfoData data = gson.fromJson(gson.toJson(request.data), GetUserInfoRequest.GetUserInfoData.class);
        UserEntity user = userService.getUserById(data.getId());
        if (user == null){
            request.error = CodeStatus.get(430).toString();
            request.token = null;
            return request;
        }
        user.setGroups(new HashSet<>());
        request.token = null;
        request.data = user;
        request.error = null;
        return request;
    }

    private Request changeRightsAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        ChangeRightsRequest.ChangeRightsData data = gson.fromJson(gson.toJson(request.data), ChangeRightsRequest.ChangeRightsData.class);
        Integer status = MemberService.changeRights(data.getUser_id(), data.getGroup_id(), data.getRights());
        if (status == 429) {
            request.error = CodeStatus.get(429).toString();
            request.token = null;
            return request;
        } else {
            request.error = null;
        }
        request.token = null;
        return request;
    }

    private Request getInMyGroupsAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GetInMyGroupsRequest.GetInMyGroupsData data = gson.fromJson(gson.toJson(request.data), GetInMyGroupsRequest.GetInMyGroupsData.class);
        List<GroupEntity> groups = null;
        Integer user_id = Main.active_users.get(request.token);
        if (data.getTitle() != null) groups = groupService.getGroupByTitle(data.getTitle(), user_id);

        if (groups == null) {
            request.error = CodeStatus.get(424).toString();
            request.token = null;
            return request;
        }

        for (GroupEntity group : groups) {
            em.detach(group);
            group.setMembers(new HashSet<>());
        }
        request.token = null;
        request.data = groups;
        request.error = null;
        return request;

    }

    private Request getMessagesAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GetMessagesRequest.GetMessagesData data = gson.fromJson(gson.toJson(request.data), GetMessagesRequest.GetMessagesData.class);
        List<MessageEntity> messages;
        messages = messageService.getMessages(data.getId());
        if (messages == null) {
            request.token = null;
            request.error = CodeStatus.get(426).toString();
            return request;
        }
        request.token = null;
        request.data = messages;
        request.error = null;
        return request;
    }


    private Request getRightsAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GetRightsRequest.GetRightsData data = gson.fromJson(gson.toJson(request.data), GetRightsRequest.GetRightsData.class);
        Integer user_id;
        if (data.getUser_id() == null) {
            user_id = Main.active_users.get(request.token);
        } else {
            user_id = data.getUser_id();
        }
        MembershipEntity membership = memberService.getMembershipById(user_id, data.getGroup_id());
        if (membership == null) {
            request.error = CodeStatus.get(416).toString();
            request.token = null;
            return request;
        }
        String rights = membership.getRights();
        request.token = null;
        request.data = rights;

        return request;
    }

    private Request changeUserInfoAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        ChangeUserInfoRequest.ChangeUserInfoData data = gson.fromJson(gson.toJson(request.data), ChangeUserInfoRequest.ChangeUserInfoData.class);
        //checking
        if (!data.getFname().matches("^[a-zA-Z]{2,40}$")) {
            request.error = CodeStatus.get(414).toString();
            request.token = null;
            return request;
        }
        if (!data.getLname().matches("^[a-zA-Z]{2,40}$")) {
            request.error = CodeStatus.get(414).toString();
            request.token = null;
            return request;
        }
        if (!data.getCountry().matches("^[a-zA-Z]{2,40}$")) {
            request.error = CodeStatus.get(414).toString();
            request.token = null;
            return request;
        }
        LocalDate now = LocalDate.now();
        if ((data.getBd() == null) || (data.getBd().toLocalDate().isEqual(now)) || (data.getBd().toLocalDate().isAfter(now))) {
            request.error = CodeStatus.get(414).toString();
            request.token = null;
            return request;
        }
        Integer user_id = Main.active_users.get(request.token);
        UserEntity user = userService.changeUserInfo(data, user_id);
        if (user == null) {
            request.error = CodeStatus.get(416).toString();
            request.token = null;
            request.token = null;
            return request;
        } else {
            request.error = null;
        }
        em.detach(user);
        user.setGroups(new HashSet<>());
        request.token = null;
        request.data = user;
        return request;
    }

    private Request changePasswordAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        ChangePasswordRequest.ChangePasswordData data = gson.fromJson(gson.toJson(request.data), ChangePasswordRequest.ChangePasswordData.class);
        if ((data.getPasswordNew().length() < 8) || (!data.getPasswordNew().matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$")) || (data.getPassword().length() < 8) || (!data.getPassword().matches("^[A-Za-z0-9!@#$%^*_+]{8,20}$")) || (data.getPassword().equals(data.getPasswordNew()))) {
            request.error = CodeStatus.get(415).toString();
            request.token = null;
            return request;
        }
        Integer user_id = Main.active_users.get(request.token);
        Integer status = userService.changePass(data, user_id);
        if (status == 416) {
            request.error = CodeStatus.get(416).toString();
            return request;
        }
        request.token = null;
        request.error = null;
        return request;
    }

    private Request changeGroupInfoAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        ChangeGroupInfoRequest.ChangeGroupData data = gson.fromJson(gson.toJson(request.data), ChangeGroupInfoRequest.ChangeGroupData.class);
        //checking
        GroupEntity group = groupService.changeGroupInfo(data, data.getId());
        if (group == null) {
            request.error = CodeStatus.get(419).toString();
            request.token = null;
            return request;
        } else {
            request.error = null;
        }
        em.detach(group);
        group.setMembers(new HashSet<>());
        request.token = null;
        request.data = group;
        return request;
    }

    /**
     * Add member action request.
     *
     * @param request the request
     * @return the request
     */
    public Request addMemberAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        AddMemberRequest.AddMemberData data = gson.fromJson(gson.toJson(request.data), AddMemberRequest.AddMemberData.class);
        Integer user_id = Main.active_users.get(request.token);
        MembershipEntity membership = null;
        try {
            membership = memberService.getMembershipById(user_id, data.getGroup_id());
        } catch (NoResultException ignore) {
        }
        if (membership == null) {
            request.error = CodeStatus.get(416).toString();
            request.token = null;
            return request;
        }
        String rights = membership.getRights();
        if (!rights.contains("i")) {
            request.error = CodeStatus.get(421).toString();
            request.token = null;
            return request;
        }
        if (!data.getGroup_type()) {
            int status = memberService.addMember(data.getUser_id(), data.getGroup_id(), "rw");
            if (status == 420) {
                request.error = CodeStatus.get(420).toString();
                request.token = null;
                return request;
            }
        } else {
            int status = memberService.addMember(data.getUser_id(), data.getGroup_id(), "rwi");
            if (status == 420) {
                request.error = CodeStatus.get(420).toString();
                request.token = null;
                return request;
            }
        }
        request.token = null;
        request.error = null;
        return request;
    }

    private Request removeMemberAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        RemoveMemberRequest.RemoveMemberData data = gson.fromJson(gson.toJson(request.data), RemoveMemberRequest.RemoveMemberData.class);
        memberService.removeMember(data.getUser_id(), data.getGroup_id());
        request.token = null;
        request.error = null;
        return request;
    }

    private Request getCreatorAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GetCreatorRequest.GetCreatorData data = gson.fromJson(gson.toJson(request.data), GetCreatorRequest.GetCreatorData.class);
        UserEntity user = null;
        user = userService.getCreator(data);
        if (user == null) {
            request.error = CodeStatus.get(412).toString();
            request.token = null;
            return null;
        }
        request.token = null;
        em.detach(user);
        user.setGroups(new HashSet<>());
        request.data = user;
        request.error = null;
        return request;
    }

    private Request getGroupAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GroupEntity data = gson.fromJson(gson.toJson(request.data), GroupEntity.class);
        GroupEntity group;
        group = groupService.getGroupById(data.getId());
        if (group == null) {
            request.error = CodeStatus.get(412).toString();
            request.token = null;
            return null;
        }
        Set<UserEntity> list = new HashSet<>(groupService.getMembers(group.getId()));
        Set<MembershipEntity> members = new HashSet<>(groupService.getMemberships(group.getId()));
        group.setMembers(list);

        em.detach(group);
        for (UserEntity member : group.getMembers()) member.setGroups(new HashSet<>());
        GetGroupRequest req = new GetGroupRequest();
        req.members = members;
        req.data= group;

        return req;
    }

    /**
     * Token validation.
     *
     * @param token
     * @return true/false
     */
    private boolean isValidToken(String token) {
        return (Main.active_users.containsKey(token));
    }

    /**
     * Delete user action request.
     *
     * @param request the request
     * @return the request
     */
    public Request deleteUserAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
//        DeleteUserRequest.DeleteUserData data = gson.fromJson(gson.toJson(request.data), DeleteUserRequest.DeleteUserData.class);
        int code = userService.deleteUser(Main.active_users.get(request.token));
        if (code != 204) {
            request.error = CodeStatus.get(411).toString();
            return request;
        }
        request.error = null;
        request.token = null;
        request.data = null;
        return request;
    }

    public Request createGroupAction(Request request, UserEntity user) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        CreateGroupRequest.CreateGroupData data = gson.fromJson(gson.toJson(request.data), CreateGroupRequest.CreateGroupData.class);
        GroupEntity group = groupService.createGroup(data, user);
        if (group == null) {
            request.error = CodeStatus.get(410).toString();
            return request;
        } else {
            request.error = null;
            memberService.addMember(user.getId(), group.getId(), "rwid");
        }

        CreateGroupRequest req = new CreateGroupRequest();
        em.detach(group);
        group.getMembers().forEach(e -> e.setGroups(new HashSet<>()));
        req.setGroup(group);
        req.action = "createGroup";
        req.token = null;
        return req;
    }

    /**
     * Delete group action request.
     *
     * @param request the request
     * @return the request
     */
    public Request deleteGroupAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }

        GroupEntity data = gson.fromJson(gson.toJson(request.data), GroupEntity.class);
        GroupEntity group = groupService.getGroupById(data.getId());
        if (group == null) {
            request.token = null;

            request.error = CodeStatus.get(412).toString();
            return request;
        }
        if (!group.getCreator().equals(Main.active_users.get(request.token))) {
            request.token = null;
            request.error = CodeStatus.get(413).toString();
            return request;
        }
        groupService.deleteGroup(group, Main.active_users.get(request.token));
        request.error = null;
        request.token = null;
        return request;
    }

    /**
     * Gets groups of user.
     *
     * @param request the request
     */
    public Request getGroupsAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GetGroupsRequest.GetGroupsData data = gson.fromJson(gson.toJson(request.data), GetGroupsRequest.GetGroupsData.class);
        Set<GroupEntity> groups;
        if (data.getTitle().equals("%")) {
//            UserEntity user = userService.getUserById(Main.active_users.get(request.token));
//            groups = user.getGroups();
//            em.detach(user);
            groups = new HashSet<>(groupService.getUserGroups(Main.active_users.get(request.token)));
        } else {
            groups = new HashSet<>(groupService.getGroupsByTitle(data.getTitle()));
        }
        Set<GroupEntity> copy = new HashSet<>();
        for (GroupEntity group : groups) {
            em.detach(group);
            GroupEntity cop = group;
            cop.setMembers(new HashSet<>());
            copy.add(cop);
        }
        request.token = null;
        request.data = groups;
        request.error = null;
        return request;
    }

    /**
     * Gets me.
     *
     * @param request the request
     * @return the me
     */
    public Request getMe(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        UserEntity user = null;
        if (request.token != null) {
            if (Main.active_users.containsKey(request.token)) {
                Integer userId = Main.active_users.get(request.token);
                user = userService.getUserById(userId);
            }
        }
        if (user == null) {
            request.error = CodeStatus.get(401).toString();
            request.token = null;
            return request;
        }
        em.detach(user);
        for (GroupEntity group : user.getGroups()) group.setMembers(new HashSet<>());
        request.data = user;
        request.error = null;
        request.token = null;
        return request;
    }

    /**
     * Send message.
     *
     * @param request the request
     */
    public Request sendMessageAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        SendMessageRequest.SendMessageData data = gson.fromJson(gson.toJson(request.data), SendMessageRequest.SendMessageData.class);
        if (data.getRecipient() == null) {
            request.error = CodeStatus.get(424).toString();
            request.token = null;
            return request;
        }
        Integer userid = Main.active_users.get(request.token);
        MessageEntity message = MessageService.getInstance().sendMessage(data, userid);
        if (message == null) {
            request.error = CodeStatus.get(423).toString();
            request.token = null;
            return request;
        }
        request.data = message;
        request.error = null;
        request.token = null;
        return request;
    }

    /**
     * Gets users action.
     *
     * @param request the request
     * @return the users action
     */
    public Request getUsersAction(Request request) {
        if (!isValidToken(request.token)) {
            request.error = CodeStatus.get(401).toString();
            return request;
        }
        GetUsersRequest.GetUsersData data = gson.fromJson(gson.toJson(request.data), GetUsersRequest.GetUsersData.class);
        List<UserEntity> users_name = null;
        List<UserEntity> users_lastname = null;
        if (data.getFname() != null) users_name = userService.getUsersByName(data.getFname());
        if (data.getLname() != null) users_lastname = userService.getUsersByLastName(data.getLname());
        for (UserEntity user : users_name) {
            if (!users_lastname.contains(user)) users_lastname.add(user);
        }
        for (UserEntity user : users_lastname) {
            em.detach(user);
            user.setGroups(new HashSet<>());
        }

        request.token = null;
        request.data = users_lastname;
        request.error = null;
        return request;
    }


    private void send(Request answer) {
        String json = gson.toJson(answer);
        output.println(json);
        output.flush();
    }

    /**
     * Logout action.
     *
     * @param request the request
     * @return the request
     */
    public Request logoutAction(Request request) {
        Main.active_users.remove(request.token);
        request.token = null;
        return request;
    }

    /**
     * Login action.
     *
     * @param request the request
     * @return the request
     */
    public Request loginAction(Request request) {
        LoginRequest.LoginData data = gson.fromJson(gson.toJson(request.data), LoginRequest.LoginData.class);
        Request answer = new Request();
        answer.action = request.action;
        Query sql = em.createQuery("SELECT a FROM AuthEntity as a WHERE a.mail = '" + data.getMail() + "' AND a.password = '" + md5Custom(data.getPassword()) + "'", AuthEntity.class);
        AuthEntity user = null;
        try {
            user = (AuthEntity) sql.getSingleResult();

        } catch (Exception e) {
        }

        if (user == null) {
            answer.error = CodeStatus.get(409).toString();

            return answer;
        }
        UserEntity userEntity = null;
        try {
            userEntity = em.createQuery("SELECT u FROM UserEntity as u WHERE u.mail='" + user.getMail() + "'", UserEntity.class).getSingleResult();
        } catch (NoResultException e) {

        }
        if (userEntity == null) {
            answer.error = CodeStatus.get(409).toString();

            return answer;
        }

        String hash = md5Custom(Long.toString(System.currentTimeMillis()));
        Main.active_users.put(hash, userEntity.getId());
        answer.token = hash;
        answer.action = request.action;
        return answer;
    }

    /**
     * Create user action.
     *
     * @param request the request
     * @return the request
     */
    public Request createUserAction(Request request) {
        CreateUserRequest.RegistrationData data = gson.fromJson(gson.toJson(request.data), CreateUserRequest.RegistrationData.class);
        //checking
        if (data.getPassword().length() < 8) {
            request.error = CodeStatus.get(415).toString();
            return request;
        }
        if (!data.getMail().matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}$")) {
            request.error = "The mail is wrong";
            return request;
        }
        UserEntity user = UserService.getInstance().createUser(data);
        if (user == null) {
            request.error = CodeStatus.get(408).toString();
        } else {
            request.error = null;
        }
        request.token = null;
        request.data = user;
        return request;
    }

    /**
     * Md 5 custom string.
     *
     * @param st the string
     * @return an encrypted string
     */
    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("md5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}

