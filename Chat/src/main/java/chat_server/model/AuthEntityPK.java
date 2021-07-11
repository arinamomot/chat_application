package chat_server.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AuthEntityPK implements Serializable {
    @Id
    @Column(name = "mail")
    private String mail;

    @Id
    @Column(name = "password")
    private String  password;

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
