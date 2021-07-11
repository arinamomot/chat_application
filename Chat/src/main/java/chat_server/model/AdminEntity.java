package chat_server.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AdminEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "adminrights")
    private String adminrights;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminrights() {
        return adminrights;
    }

    public void setAdminrights(String adminrights) {
        this.adminrights = adminrights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminEntity that = (AdminEntity) o;
        return id == that.id &&
                Objects.equals(adminrights, that.adminrights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adminrights);
    }
}
