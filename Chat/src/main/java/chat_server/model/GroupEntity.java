package chat_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@Table(name = "group", schema = "public", catalog = "db20_momotari")
public class GroupEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    @Column(name = "creator")
    private Integer creator;

    @Column(name = "type")
    private boolean type;

    @Column(name = "datecreated")
    private Date datecreated;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
//            CascadeType.REMOVE

    }, mappedBy = "groups", fetch = FetchType.EAGER)
    private Set<UserEntity> members = new HashSet<>();

    public Set<UserEntity> getMembers() {
//        Set<UserEntity> copy = new HashSet<>();
//        for(UserEntity member : members) {
//            member.setGroups(new HashSet<>());
//            System.out.println(member);
//            copy.add(member);
//        }
//        return copy;

        return members;
    }

    public void addMember(UserEntity user) {
        members.add(user);
        user.getGroups().add(this);
    }

    public void removeMember(UserEntity user) {
        members.remove(user);
    }
    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }


    @Override
    public String toString() {
        return "GroupEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", creator=" + creator +
                ", type=" + type +
                ", datecreated=" + datecreated +
                ", members=" + getMembers() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity that = (GroupEntity) o;
        return id == that.id &&
                type == that.type &&
                Objects.equals(description, that.description) &&
                Objects.equals(title, that.title) &&
                Objects.equals(datecreated, that.datecreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, title, type, datecreated);
    }
}

