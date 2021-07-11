package chat_server.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class MembershipEntityPK implements Serializable {

    @Id
    @Column(name = "userid")
    private Integer userid;

    @Id
    @Column(name = "groupid")
    private Integer groupid;

    public MembershipEntityPK(Integer groupid, Integer userid) {
        this.groupid = groupid;
        this.userid = userid;
    }
    public MembershipEntityPK() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembershipEntityPK that = (MembershipEntityPK) o;
        return userid == that.userid &&
                groupid == that.groupid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, groupid);
    }
}
