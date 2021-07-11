package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.GroupEntity;

import java.sql.Date;

public class CreateGroupRequest extends Request {
    public CreateGroupRequest() {
        this.action = "createGroup";
        this.token = null;
        this.data = new CreateGroupData();
    }

    public void setTitle(String title) {
        ((CreateGroupRequest.CreateGroupData) this.data).setTitle(title);
    }

    public void setDescription(String description) {
        ((CreateGroupRequest.CreateGroupData) this.data).setDescription(description);
    }

    public void setType(Boolean type) {
        ((CreateGroupRequest.CreateGroupData) this.data).setType(type);
    }
    public void setGroup(GroupEntity group) {
        ((CreateGroupRequest.CreateGroupData) this.data).setGroup(group);
    }

    public static class CreateGroupData {
        private GroupEntity group = new GroupEntity();

        public String getTitle() {
            return group.getTitle();
        }

        public void setTitle(String title) {
            this.group.setTitle(title);
        }

        public String getDescription() {
            return group.getDescription();
        }

        public void setDescription(String description) {
            this.group.setDescription(description);
        }

        public Boolean getType() {
            return group.getType();
        }

        public void setType(Boolean type) {
            this.group.setType(type);
        }

        public GroupEntity getGroup() {
            return group;
        }

        public void setGroup(GroupEntity group) {
            this.group = group;
        }
    }
}
