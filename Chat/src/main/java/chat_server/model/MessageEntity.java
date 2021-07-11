package chat_server.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "public", catalog = "db20_momotari")
public class MessageEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "timesend")
    private Timestamp timesend;

    @Column(name = "text")
    private String text;

    @Column(name="recipient")
    private Integer recipient;

    @Column(name="sender")
    private Integer sender;

    @Column(name="deletedby")
    private Integer deletedBy;

    @Column(name="reply")
    private Integer reply;

    @Transient
    public UserEntity sender_user;


    public Integer getRecipient() {
        return recipient;
    }

    public void setRecipient(Integer recipient) {
        this.recipient = recipient;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", timesend=" + timesend +
                ", text='" + text + '\'' +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", deletedBy=" + deletedBy +
                ", reply=" + reply +
                ", sender_user=" + sender_user +
                '}';
    }

    public Timestamp getTimesend() {
        return timesend;
    }

    public void setTimesend(Timestamp timesend) {
        this.timesend = timesend;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return id == that.id &&
                Objects.equals(timesend, that.timesend) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timesend, text);
    }
}
