package chat_server.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "smiles", schema = "public", catalog = "db20_momotari")
public class SmilesEntity {
    @Id
    @Column(name = "hash")
    private String hash;

    @Column(name = "content")
    private String content;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmilesEntity that = (SmilesEntity) o;
        return Objects.equals(hash, that.hash) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, content);
    }
}
