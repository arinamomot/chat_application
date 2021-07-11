package chat_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

@Entity
@Table(name = "user", schema = "public", catalog = "db20_momotari")
public class UserEntity  {
    @Id
    @GeneratedValue
    private Integer id;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", country='" + country + '\'' + groups.toString() +
                '}';
    }

    @Column(name = "mail")
    private String mail;
    private String firstname;
    private String lastname;
    private String gender;
    private Date birthday;
    private String country;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
//            CascadeType.REMOVE
    })
    @JoinTable(name = "membership", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "groupid"))
    @Expose(serialize = false)
    private Set<GroupEntity> groups = new HashSet<>();

    public Set<GroupEntity> getGroups() {
        return groups;
    }
    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }


    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mail, firstname, lastname, gender, birthday, country);
    }
}
