package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.GroupEntity;

import javax.persistence.criteria.CriteriaBuilder;

public class DeleteGroupRequest extends Request {
    public DeleteGroupRequest() {
        this.action = "deleteGroup";
        this.token = null;
        this.data = new GroupEntity();
    }
    public void setId(Integer id) {
        ((GroupEntity)this.data).setId(id);
    }

}
