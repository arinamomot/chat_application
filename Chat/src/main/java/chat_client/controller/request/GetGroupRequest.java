package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.GroupEntity;
import chat_server.model.MembershipEntity;

import java.util.HashSet;
import java.util.Set;

public class GetGroupRequest extends Request {
    public Set<MembershipEntity> members = new HashSet<>();
    public GetGroupRequest() {
        this.action = "getGroup";
        this.token = null;
        this.error = null;
        this.data = new GroupEntity();
    }

    public void setId(Integer id) {
        ((GroupEntity)this.data).setId(id);
    }
}
