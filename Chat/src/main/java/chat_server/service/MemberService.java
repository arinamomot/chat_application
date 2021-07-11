package chat_server.service;

import chat_client.controller.request.AddMemberRequest;
import chat_client.controller.request.CreateUserRequest;
import chat_server.model.*;

import javax.persistence.EntityTransaction;
import java.sql.Date;
import java.util.Set;

import static chat_server.Client.md5Custom;
import static chat_server.Main.em;

/**
 * The type Member service.
 */
public class MemberService {

    private static MemberService self;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MemberService getInstance() {
        if(self == null) {
            self = new MemberService();
        }
        return self;
    }
    private MemberService() {}

    /**
     * Gets membership by id.
     *
     * @param user_id  the user id
     * @param group_id the group id
     * @return the membership by id
     */
    public MembershipEntity getMembershipById(Integer user_id, Integer group_id) {
        return em.createQuery("select m from MembershipEntity m where (m.userid=" + user_id + ") and (m.groupid =" + group_id + ")", MembershipEntity.class).getSingleResult();
    }

    /**
     * Add member int.
     *
     * @param userId  the user id
     * @param groupId the group id
     * @param rights  the rights
     * @return the int
     */
    public int addMember(Integer userId, Integer groupId, String rights) {
        try {
            MembershipEntity membershipEntity = em.createQuery("select m from MembershipEntity m where (m.userid=" + userId + ") and (m.groupid =" + groupId + ")", MembershipEntity.class).getSingleResult();
            if (membershipEntity != null) return 420;
        } catch (Exception ignore) {}
        MembershipEntity membership = new MembershipEntity();
        membership.setGroupid(groupId);
        membership.setUserid(userId);
        membership.setRights(rights);
        GroupEntity group = em.find(GroupEntity.class, groupId);
//        UserEntity user = em.find(UserEntity.class, userId);
        EntityTransaction t = em.getTransaction();
        t.begin();
//        group.addMember(user);
        em.persist(membership);
        em.refresh(group);
//        //todo

//        em.createNativeQuery("insert into MembershipEntity (userid, grouid, rights) values(:userid, :groupid, :rights)", MembershipEntity.class)
//            .setParameter("userid", userId)
//            .setParameter("groupid", groupId)
//            .setParameter("rights",rights).executeUpdate();
        t.commit();

        //setParamater
        return 201;

    }

    /**
     * Change rights integer.
     *
     * @param user_id  the user id
     * @param group_id the group id
     * @param rights   the rights
     * @return the integer
     */
    public static Integer changeRights(Integer user_id, Integer group_id, String rights){
        MembershipEntity membership = em.createQuery("Select m from MembershipEntity m where (m.userid=" + user_id + ") AND (m.groupid=" + group_id + ")", MembershipEntity.class ).getSingleResult();
        if (membership == null) {
            return 429;
        }

//        MembershipEntity membershipNew = new MembershipEntity();
//        membershipNew.setUserid(user_id);
//        membershipNew.setGroupid(group_id);
        membership.setRights(rights);

        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(membership);
        t.commit();
//        t.begin();
//        em.remove(membership);
//        t.commit();

        return 201;
    }

    /**
     * Remove member.
     *
     * @param user_id  the user id
     * @param group_id the group id
     */
    public void removeMember(Integer user_id, Integer group_id) {
        GroupEntity group = em.find(GroupEntity.class, group_id);
//        UserEntity user = em.find(UserEntity.class, user_id);
        MembershipEntity member = em.createQuery("select m from MembershipEntity m where (m.userid="+user_id+") AND (m.groupid="+group_id+")", MembershipEntity.class).getSingleResult();
//        em.createQuery("delete from MembershipEntity  where groupid = :group and userid = :user").setParameter("group", group_id).set
//        MembershipEntityPK pk = new MembershipEntityPK(group_id, user_id);
//        MembershipEntity member = em.find(MembershipEntity.class, pk);
//        if(!group.getMembers().contains(user) || member == null) return;
        EntityTransaction t = em.getTransaction();
        t.begin();
//        group.removeMember(user);
        em.remove(member);
        em.refresh(group);
        t.commit();
    }
}
