package chat_server.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AttachmentEntity {
    @Id
    @Column(name = "hash")
    private String hash;

    @Id
    @Column(name = "id")
    private Integer id;

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

}
