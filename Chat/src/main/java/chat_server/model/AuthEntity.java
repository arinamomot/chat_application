package chat_server.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth", schema = "public", catalog = "db20_momotari")
@IdClass(AuthEntityPK.class)

public class AuthEntity {
    @Id
    private String mail;
    @Id
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}