package chat_server.service;

import chat_client.controller.code.CodeStatus;
import chat_client.controller.request.*;
import chat_server.Main;
import chat_server.model.*;

import javax.persistence.*;

import java.util.List;

import static chat_server.Client.md5Custom;

/**
 * The type User service.
 */
public class UserService {

    private static UserService self;
    private static EntityManager em;


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserService getInstance() {
        if (self == null) {
            self = new UserService();
        }
        return self;
    }

    /**
     * Instantiates a new User service.
     */
    public UserService()  {
        try {
            Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("chatik");
        em = emf.createEntityManager();
    }

    /**
     * Count of users integer.
     *
     * @return the integer
     */
    public Integer countOfUsers() {
        List<UserEntity> users = em.createQuery("Select u from UserEntity u", UserEntity.class).getResultList();
        return users.size();
    }

    /**
     * Gets users by name.
     *
     * @param fname the fname
     * @return the users by name
     */
    public List<UserEntity> getUsersByName(String fname) {
        List<UserEntity> users =  em.createQuery("SELECT u FROM UserEntity u WHERE u.firstname like '%" + fname + "%'", UserEntity.class).getResultList();
        if (users == null) return null;
        return users;
    }

    /**
     * Gets user by mail.
     *
     * @param mail the mail
     * @return the user by mail
     */
    public UserEntity getUserByMail(String mail) {
        try {
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.mail = '" + mail + "'", UserEntity.class).getSingleResult();
            if (user == null) return null;
            return user;
        } catch (Exception ignore) {}
        return null;
    }

    /**
     * Gets users by last name.
     *
     * @param lname the lname
     * @return the users by last name
     */
    public List<UserEntity> getUsersByLastName(String lname) {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.lastname like '%" + lname + "%'", UserEntity.class).getResultList();
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public UserEntity getUserById(int id) {
        return em.createQuery("select u from UserEntity u where u.id="+id, UserEntity.class).getSingleResult();
    }

    /**
     * Gets creator.
     *
     * @param data the data
     * @return the creator
     */
    public UserEntity getCreator(GetCreatorRequest.GetCreatorData data) {
        return em.createQuery("select u from UserEntity u join GroupEntity g on u.id = g.creator where g.id=" + data.getId(), UserEntity.class).getSingleResult();
    }


    /**
     * Create user user entity.
     *
     * @param data the data
     * @return the user entity
     */
    public UserEntity createUser(CreateUserRequest.RegistrationData data) {
        UserEntity user = new UserEntity();
        user.setFirstname(data.getFname());
        user.setLastname(data.getLname());
        Query sql = em.createQuery("SELECT u FROM UserEntity as u where u.mail = '" + data.getMail() + "'", UserEntity.class);
        UserEntity userOld = null;
        try {
            userOld = (UserEntity) sql.getSingleResult();
        } catch (NoResultException e) {
            CodeStatus.get(408).toString();
        }

        if (userOld != null) {
            return null;
        }
        user.setMail(data.getMail());
        user.setCountry(data.getCountry());
        user.setBirthday(data.getBd());
        user.setGender(data.getGender());

        AuthEntity auth = new AuthEntity();
        auth.setMail(data.getMail());
        auth.setPassword(md5Custom(data.getPassword()));

        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(user);
        t.commit();
        t.begin();
        em.persist(auth);
        t.commit();

        return user;
    }

    /**
     * Change pass int.
     *
     * @param data    the data
     * @param user_id the user id
     * @return the int
     */
    public int changePass(ChangePasswordRequest.ChangePasswordData data, Integer user_id) {
        UserEntity user = em.createQuery("Select u from UserEntity u where u.id=" + user_id, UserEntity.class).getSingleResult();
        AuthEntity auth = em.createQuery("select a from AuthEntity a where a.mail='" + user.getMail() + "'", AuthEntity.class).getSingleResult();

        if (!md5Custom(data.getPassword()).equals(auth.getPassword())) {
            return 416;
        }

        AuthEntity authNew = new AuthEntity();
        authNew.setMail(user.getMail());
        authNew.setPassword(md5Custom(data.getPasswordNew()));

        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(authNew);
        t.commit();
        t.begin();
        em.remove(auth);
        t.commit();

        return 201;
    }

    /**
     * Change user info user entity.
     *
     * @param data    the data
     * @param user_id the user id
     * @return the user entity
     */
    public UserEntity changeUserInfo(ChangeUserInfoRequest.ChangeUserInfoData data, Integer user_id) {
        UserEntity user = em.createQuery("Select u from UserEntity u where u.id=" + user_id, UserEntity.class).getSingleResult();
        user.setFirstname(data.getFname());
        user.setLastname(data.getLname());
        user.setCountry(data.getCountry());
        user.setBirthday(data.getBd());
        user.setGender(data.getGender());

        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(user);
        t.commit();

        return user;
    }

    /**
     * Delete user integer.
     *
     * @param userId the user id
     * @return the integer
     */
    public Integer deleteUser(Integer userId) {
        List<GroupEntity> groups = em.createQuery("select g from GroupEntity g where g.creator=" + userId, GroupEntity.class).getResultList();
        List<MembershipEntity> list = em.createQuery("select m from MembershipEntity m where m.userid = " + userId + " ", MembershipEntity.class).getResultList();
        List<MessageEntity> messages = em.createQuery("select m from MessageEntity m where m.sender = " + userId + " ", MessageEntity.class).getResultList();

        for (GroupEntity group : groups) {
            list.addAll(em.createQuery("select m from MembershipEntity m where m.groupid =" + group.getId(), MembershipEntity.class).getResultList());
            messages.addAll(em.createQuery("select m from MessageEntity m where m.recipient =" + group.getId(), MessageEntity.class).getResultList());
        }
        UserEntity user = em.find(UserEntity.class, userId);
        EntityTransaction t = em.getTransaction();
        t.begin();
        for (MembershipEntity entity : list) em.remove(entity);
        t.commit();
        t.begin();
        for (MessageEntity entity : messages) em.remove(entity);
        t.commit();
        t.begin();
        for (GroupEntity entity : groups) em.remove(entity);

        t.commit();
        t.begin();
        em.remove(user);
        t.commit();
        return 204;
    }

}
