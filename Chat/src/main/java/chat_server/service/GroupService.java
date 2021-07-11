package chat_server.service;

import chat_client.controller.request.ChangeGroupInfoRequest;
import chat_client.controller.request.CreateGroupRequest;
import chat_server.model.GroupEntity;
import chat_server.model.MembershipEntity;
import chat_server.model.MessageEntity;
import chat_server.model.UserEntity;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;

import static chat_server.Main.em;

/**
 * The type Group service.
 */
public class GroupService {
    /**
     * The constant self.
     */
    public static GroupService self;
    private static EntityManager em;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GroupService getInstance() {
        if(self == null) {
            self = new GroupService();
        }
        return self;
    }

    /**
     * Instantiates a new Group service.
     */
    public GroupService() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("chatik");
        em = emf.createEntityManager();
    }

    /**
     * Gets members.
     *
     * @param group_id the group id
     * @return the members
     */
    public List<UserEntity> getMembers(Integer group_id) {
        return em.createQuery("SELECT g FROM MembershipEntity m LEFT JOIN UserEntity g ON g.id = m.userid WHERE m.groupid= '" + group_id + "'", UserEntity.class).getResultList();

    }
    public List<MembershipEntity> getMemberships(Integer group_id) {
        return em.createQuery("SELECT m FROM MembershipEntity m LEFT JOIN GroupEntity g ON g.id = m.groupid WHERE m.groupid= '" + group_id + "'", MembershipEntity.class).getResultList();

    }

    /**
     * Gets groups by title.
     *
     * @param title the title
     * @return the groups by title
     */
    public List<GroupEntity> getGroupsByTitle(String title) {
        return em.createQuery("SELECT g FROM GroupEntity g WHERE g.title like '%" + title + "%'", GroupEntity.class).getResultList();
    }

    /**
     * Gets user groups.
     *
     * @param user_id the user id
     * @return the user groups
     */
    public List<GroupEntity> getUserGroups(Integer user_id) {
        return em.createQuery("SELECT g FROM MembershipEntity m LEFT JOIN GroupEntity g ON g.id = m.groupid WHERE m.userid= '" + user_id + "'", GroupEntity.class).getResultList();
    }

    /**
     * Create group group entity.
     *
     * @param data the data
     * @param user the user
     * @return the group entity
     */
    public GroupEntity createGroup(CreateGroupRequest.CreateGroupData data, UserEntity user) {
        GroupEntity group = new GroupEntity();
        group.setTitle(data.getTitle());
        group.setDescription(data.getDescription());
        group.setType(data.getType());
        group.setDatecreated(new Date(System.currentTimeMillis()));
        group.setCreator(user.getId());

        Query sql = em.createQuery("SELECT g FROM GroupEntity as g where g.title = '" + data.getTitle() + "'", GroupEntity.class);
        GroupEntity groupOld = null;
        try {
            groupOld = (GroupEntity) sql.getSingleResult();
        } catch (NoResultException e) {

        }
        if (groupOld != null) {
            return null;
        }

        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(group);
        t.commit();

        return group;
    }

    /**
     * Gets groups by creator.
     *
     * @param userId the user id
     * @return the groups by creator
     */
    public List<GroupEntity> getGroupsByCreator(Integer userId) {
        return em.createQuery("Select g from GroupEntity g where g.creator="+userId, GroupEntity.class).getResultList();
    }

    /**
     * Gets group by id.
     *
     * @param id the id
     * @return the group by id
     */
    public GroupEntity getGroupById(int id) {
        return em.find(GroupEntity.class, id);
    }

    /**
     * Gets group by title.
     *
     * @param title   the title
     * @param user_id the user id
     * @return the group by title
     */
    public List<GroupEntity> getGroupByTitle(String title , Integer user_id) {
        return em.createQuery("SELECT g FROM GroupEntity g WHERE (g.title like '%" + title + "%') AND (g.creator=" +user_id + ")", GroupEntity.class).getResultList();
    }

    /**
     * Change group info group entity.
     *
     * @param data     the data
     * @param group_id the group id
     * @return the group entity
     */
    public GroupEntity changeGroupInfo(ChangeGroupInfoRequest.ChangeGroupData data, Integer group_id) {
        GroupEntity group = em.createQuery("Select g from GroupEntity g where g.id=" + group_id, GroupEntity.class).getSingleResult();
        group.setTitle(data.getTitle());
        group.setDescription(data.getDescription());

        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(group);
        t.commit();

        return group;
    }

    /**
     * Delete group int.
     *
     * @param data   the data
     * @param userId the user id
     * @return the int
     */
    public int deleteGroup(GroupEntity data, int userId) {
        GroupEntity group = em.find(GroupEntity.class, data.getId());
        if(group == null) return 413;
        if(group.getCreator() != userId) return 413;
        List<MembershipEntity> list = em.createQuery("select m from MembershipEntity m where m.groupid = "+data.getId(), MembershipEntity.class).getResultList();
        List<MessageEntity> messageEntities = em.createQuery("select m from MessageEntity m where m.recipient = "+data.getId(), MessageEntity.class).getResultList();

        EntityTransaction t = em.getTransaction();
        t.begin();
//        if(group.getMembers() == null) return 413;
//        for (UserEntity user : group.getMembers()) {
//            group.removeMember(user);
//        }
        //5,6,7,8,9
//        for(UserEntity user: group.getMembers()) group.removeMember(user);
        em.persist(group);
        em.remove(group);
        for(MembershipEntity entity: list) em.remove(entity);
        t.commit();
        t.begin();
        for(MessageEntity entity: messageEntities) em.remove(entity);
        t.commit();
        t.begin();
        t.commit();
        return 204;

//         em.createQuery("DELETE FROM GroupEntity g WHERE g.id='" + data.getId() + "'", GroupEntity.class);

    }
}