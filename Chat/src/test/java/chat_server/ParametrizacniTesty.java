package chat_server;

import chat_client.controller.request.CreateUserRequest;
import chat_server.model.UserEntity;
import chat_server.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;


public class ParametrizacniTesty {
    public static UserService userService;

    @BeforeEach
    public void initEntityManagerFactory() {
        userService = UserService.getInstance();
    }

    @ParameterizedTest()
    @CsvSource({
            "marina@mail.com, Marina, Topot, 12345678, f, 2020-05-04, RU, true",
            "kekjack@mail.com, Jack, Kek, 12345678, m, 1999-03-23, CZ, false",
            "kate@mail.com, Kate, Kot, 12345678, f , 2020-09-02, PL, true",
    })
    void createUserTest_TwoNewUsers_expectedResult(String mail, String first_name, String last_name, String password, String gender, String bd, String country, boolean ext) {
        boolean result = false;

        CreateUserRequest.RegistrationData data = new CreateUserRequest.RegistrationData();
        data.setFname(first_name);
        data.setLname(last_name);
        data.setMail(mail);
        data.setPassword(password);
        data.setCountry(country);
        data.setBd(bd);
        data.setGender(gender);
        Integer size1 = userService.countOfUsers();

        UserEntity userCheck = userService.getUserByMail(mail);
        System.out.println(userCheck);
        if (userCheck == null) {
            userService.createUser(data);
            result = true;
            Integer size2 = userService.countOfUsers();
            System.out.println(size1 + " " + size2);
            //ASSERT
            Assert.assertNotEquals(size1, size2);
        }
        //ASSERT
        Assert.assertEquals(ext, result);
    }

    @ParameterizedTest
    @CsvSource({
            "4701, arina@mail.com, Arina, Momot, f, 2020-05-04, RU, true",
            "1943, john@mail.com, John, Tomas, m, 1998-03-22, CZ, false",
            "5702, gera@mail.com, Gera, Klegg, m, 2000-11-17, RU, true",
    })
    void findUserByNameTest(Integer id, String mail, String first_name, String last_name, String gender, Date
            bd, String country, boolean ext) {
        boolean result = false;

        UserEntity user = new UserEntity();
        user.setId(id);
        user.setFirstname(first_name);
        user.setLastname(last_name);
        user.setMail(mail);
        user.setCountry(country);
        user.setBirthday(bd);
        user.setGender(gender);
        user.setGroups(new HashSet<>());

        List<UserEntity> users = userService.getUsersByName(first_name);
        for (UserEntity userEntity : users) {
            userEntity.setGroups(new HashSet<>());
            if (userEntity.getId().equals(user.getId())) result = true;
        }

        //ASSERT
        Assert.assertEquals(ext, result);
    }

}
