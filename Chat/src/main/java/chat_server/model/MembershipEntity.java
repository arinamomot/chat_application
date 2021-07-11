package chat_server.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "membership", schema = "public", catalog = "db20_momotari")
@IdClass(MembershipEntityPK.class)
public class MembershipEntity {
    @Id
    @Column(name = "userid")
    private Integer userid;

    @Id
    @Column(name = "groupid")
    private Integer groupid;

    @Column(name = "rights")
    private String rights;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public MembershipEntity() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembershipEntity that = (MembershipEntity) o;
        return userid == that.userid &&
                groupid == that.groupid &&
                Objects.equals(rights, that.rights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, groupid, rights);
    }
}
