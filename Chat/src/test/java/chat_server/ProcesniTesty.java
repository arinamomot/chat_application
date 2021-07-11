package chat_server;

import chat_client.controller.request.*;
import chat_server.model.GroupEntity;
import chat_server.model.MessageEntity;
import chat_server.model.UserEntity;
import chat_server.service.GroupService;
import chat_server.service.MessageService;
import chat_server.service.UserService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProcesniTesty {
    public static UserService userService;
    public static GroupService groupService;
    public static MessageService messageService;
    GroupService mockedGroupService = mock(GroupService.class);

    @BeforeClass
    public static void initEntityManagerFactory(){
        userService = UserService.getInstance();
        groupService = GroupService.getInstance();
        messageService = MessageService.getInstance();
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Test done");
    }

    //Tests for group
    @Test
    public void CreateGroupAndCheckGroups_GroupCreated_ListsNotEqual(){
        String title = "New group for test test";
        String description = "Test group test";
        Boolean type = false;
        Integer user_id = 4701;

        UserEntity user = userService.getUserById(user_id);

        List<GroupEntity> groups = groupService.getGroupsByCreator(user_id);
        Integer size1 = groups.size();

        CreateGroupRequest.CreateGroupData data = new CreateGroupRequest.CreateGroupData();
        data.setTitle(title);
        data.setDescription(description);
        data.setType(type);
        groupService.createGroup(data, user);
        List<GroupEntity> newGroups = groupService.getGroupsByCreator(user_id);
        Integer size2 = newGroups.size();

        //ASSERT
//        Assert.assertNotEquals(groups, newGroups);
        Assert.assertNotEquals(size1,size2);

        List<GroupEntity> newGroupsCheck = groupService.getGroupsByCreator(user_id);

        //Assert
        Assert.assertEquals(newGroups, newGroupsCheck);
    }

    @Test
    public void changeGroupInfo_InfoChanged_NotEqualResults() {
        //Check getGroupById
        Integer group_id = 10601;
        GroupEntity group = groupService.getGroupById(group_id);
        GroupEntity group2 = mockedGroupService.getGroupById(group_id);
        when(mockedGroupService.getGroupById(group_id)).thenReturn(group);
        System.out.println(group2);
        String title1 = group.getTitle();
        String description1 = group.getDescription();

        //Check change Info
        ChangeGroupInfoRequest.ChangeGroupData data = new ChangeGroupInfoRequest.ChangeGroupData();
        data.setId(group_id);
        data.setTitle("New namee");
        data.setDescription("New descriptionn");
//        groupService.changeGroupInfo(data, group_id);
//        GroupEntity newGroup = groupService.getGroupById(group_id);
        mockedGroupService.changeGroupInfo(data, group_id);
        GroupEntity newGroup = mockedGroupService.getGroupById(group_id);
        System.out.println(newGroup);
        String title2 = newGroup.getTitle();
        String description2 = newGroup.getDescription();

        //ASSERT
        Assert.assertNotEquals(title1, title2);
        Assert.assertNotEquals(description1, description2);
    }

    @Test
    public void FindAndDeleteGroup_GroupDeleted_GroupIsNull(){
        Integer group_id = 10601;
        GroupEntity group = groupService.getGroupById(group_id);

        Integer user_id = 4701;
        groupService.deleteGroup(group, user_id);
        GroupEntity newGroup = groupService.getGroupById(group_id);

        //ASSERT
        Assert.assertNull(newGroup);
    }

    //Tests for User
    @Test
    public void changeUserInfo_UserInfoChanged_NotEqualsResult() {
        Integer user_id = 5702;
        UserEntity user = userService.getUserById(user_id);
        String name1 = user.getFirstname();
        String lname1 = user.getLastname();
        Date bd1 = user.getBirthday();
        String country1 = user.getCountry();
        String gender1 = user.getGender();

        ChangeUserInfoRequest.ChangeUserInfoData data = new ChangeUserInfoRequest.ChangeUserInfoData();
        data.setFname("Kate");
        data.setLname("Tomson");
        data.setBd(LocalDate.parse("2011-11-11"));
        data.setCountry("PL");
        data.setGender("f");
        userService.changeUserInfo(data, user_id);

        UserEntity newUser = userService.getUserById(user_id);
        String name2 = newUser.getFirstname();
        String lname2 = newUser.getLastname();
        Date bd2 = newUser.getBirthday();
        String country2 = newUser.getCountry();
        String gender2 = newUser.getGender();

        //ASSERT
        Assert.assertNotEquals(name1, name2);
        Assert.assertNotEquals(lname1, lname2);
        Assert.assertNotEquals(bd1, bd2);
        Assert.assertNotEquals(country1, country2);
        Assert.assertNotEquals(gender1, gender2);
    }

    //Test for messages
    @Test
    public void getMessages_GetAllMessagesOfGroup_ListOfMessages() {
        Integer group_id = 10502;
        List<MessageEntity> messages = messageService.getMessages(group_id);

        //ASSERT
        Assert.assertNotNull(messages);
    }

    @Test
    public void sendMessage_MessageSent_ListsNotEqual(){
        Integer group_id = 10502;
        List<MessageEntity> messages = messageService.getMessages(group_id);

        Integer user_id = 10501;
        SendMessageRequest.SendMessageData data = new SendMessageRequest.SendMessageData();
        data.setRecipient(group_id);
        data.setText("Hello! How are you?");
        messageService.sendMessage(data, user_id);
        List<MessageEntity> newMessages = messageService.getMessages(group_id);

        //ASSERT
        Assert.assertNotEquals(messages, newMessages);
    }

}
