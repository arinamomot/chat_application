package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.GroupEntity;

public class ChangeGroupInfoRequest extends Request {
    public ChangeGroupInfoRequest() {
        this.action = "changeGroupInfo";
        this.data = new ChangeGroupData();
    }

    public void setTitle(String title) {
        ((ChangeGroupInfoRequest.ChangeGroupData) this.data).setTitle(title);
    }

    public void setDescription(String description) {
        ((ChangeGroupInfoRequest.ChangeGroupData) this.data).setDescription(description);
    }

    public void setId(Integer id) {
        ((ChangeGroupInfoRequest.ChangeGroupData) this.data).setId(id);
    }

    public static class ChangeGroupData {
        private String title;
        private String description;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        private Integer id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
