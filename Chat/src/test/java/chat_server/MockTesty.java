package chat_server;


import chat_server.model.GroupEntity;
import chat_server.model.MessageEntity;
import chat_server.model.UserEntity;
import chat_server.service.GroupService;
import chat_server.service.MessageService;
import chat_server.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.NoResultException;

import java.util.List;

import static org.mockito.Mockito.*;

public class MockTesty {
    UserService mockedUserService = mock(UserService.class);
    GroupService mockedGroupService = mock(GroupService.class);
    MessageService mockedMessageService = mock(MessageService.class);
    public static UserService userService;
    public static GroupService groupService;
    public static MessageService messageService;

    @BeforeEach
    public void initEntityManagerFactory(){
        userService = UserService.getInstance();
        groupService = GroupService.getInstance();
        messageService = MessageService.getInstance();
    }

    @Test
    public void findUserByIdMocked_UsersFound() {
        int user_id = 5702;
        UserEntity realUser = userService.getUserById(user_id);
        int user_id2 = 9551;
        UserEntity realUser2 = userService.getUserById(user_id2);

        when(mockedUserService.getUserById(user_id)).thenReturn(realUser);
        Assert.assertEquals(realUser ,mockedUserService.getUserById(user_id));
        verify(mockedUserService, atLeastOnce()).getUserById(user_id);
        when(mockedUserService.getUserById(user_id2)).thenReturn(realUser2);
        Assert.assertEquals(realUser2 ,mockedUserService.getUserById(user_id2));
        verify(mockedUserService, atLeastOnce()).getUserById(user_id2);
    }

    @org.junit.Test(expected = NoResultException.class)
    public void findUserByIdTestWithMock_NotFound_ReturnException() {
        int user_id = 5702;
        when(mockedUserService.getUserById(anyInt())).thenThrow(NoResultException.class);
        mockedUserService.getUserById(user_id);
    }

    @Test
    public void getGroupsByCreatorWithMock() {
        int user_id = 4701;
        List<GroupEntity> realGroups = groupService.getGroupsByCreator(user_id);

        when(mockedGroupService.getGroupsByCreator(user_id)).thenReturn(realGroups);
        Assert.assertEquals(realGroups, mockedGroupService.getGroupsByCreator(user_id));
        verify(mockedGroupService, atLeastOnce()).getGroupsByCreator(user_id);
    }

    @Test
    public void getMessagesWithMock(){
       int group_id = 9155;
       List<MessageEntity> realMessages = messageService.getMessages(group_id);

       when(mockedMessageService.getMessages(group_id)).thenReturn(realMessages);
       Assert.assertEquals(realMessages, mockedMessageService.getMessages(group_id));
       verify(mockedMessageService, atLeastOnce()).getMessages(group_id);
    }

}
