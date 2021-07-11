package chat_server.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AttachmentEntityPK implements Serializable {
    @Id
    @Column(name = "hash")
    private String hash;

    @Id
    @Column(name = "id")
    private Integer id;

    private AttachmentEntityPK(String hash, Integer id) {
        this.hash = hash;
        this.id = id;
    }

    public AttachmentEntityPK() {}

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentEntityPK that = (AttachmentEntityPK) o;
        return id == that.id &&
                Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, id);
    }
}
