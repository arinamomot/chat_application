package chat_server.service;

import chat_client.controller.request.SendMessageRequest;
import chat_server.Client;
import chat_server.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import static chat_server.Main.em;

/**
 * The type Message service.
 */
public class MessageService {
    private static MessageService self;
    private static EntityManager em;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MessageService getInstance() {
        if(self == null) {
            self = new MessageService();
        }
        return self;
    }
    private MessageService() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("chatik");
        em = emf.createEntityManager();
    }

    /**
     * Send message message entity.
     *
     * @param data   the data
     * @param userid the userid
     * @return the message entity
     */
    public MessageEntity sendMessage(SendMessageRequest.SendMessageData data, Integer userid) {
        GroupEntity group = em.find(GroupEntity.class, data.getRecipient());
        if(group == null) return null;
        MembershipEntity membershipEntity = em.createQuery("select m from MembershipEntity m where (m.groupid="+ data.getRecipient()+") AND (m.userid="+userid+")", MembershipEntity.class).getSingleResult();
//        MembershipEntity membershipEntity = em.find(MembershipEntity.class, MembershipEntity(data.getRecipient(), userid));
        if(membershipEntity == null) return null;


        MessageEntity message = new MessageEntity();
        message.setText(data.getText());
        message.setRecipient(data.getRecipient());
        message.setSender(userid);
//        message.sender_user = em.find(UserEntity.class, userid);
//        message.sender_user.setGroups(new HashSet<>());
        message.setTimesend(new Timestamp(System.currentTimeMillis()));

        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(message);
        t.commit();

        message.sender_user = em.find(UserEntity.class, userid);
        em.detach(message.sender_user);
        message.sender_user.setGroups(new HashSet<>());

        return message;
    }

    /**
     * Gets messages.
     *
     * @param groupId the group id
     * @return the messages
     */
//test
    // old messages
    // send messages
    // new messages sort by date
    public List<MessageEntity> getMessages(Integer groupId) {
        List<MessageEntity>messages = em.createQuery("SELECT m FROM MessageEntity m WHERE m.recipient=" + groupId, MessageEntity.class).getResultList();
        for(MessageEntity messageEntity: messages) {
            messageEntity.sender_user = UserService.getInstance().getUserById(messageEntity.getSender());
            messageEntity.sender_user.setGroups(new HashSet<>());
        }
        return messages;
    }

}
