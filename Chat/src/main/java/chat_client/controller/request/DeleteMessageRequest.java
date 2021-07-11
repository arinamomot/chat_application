package chat_client.controller.request;

import chat_client.controller.Request;

public class DeleteMessageRequest extends Request {
    public DeleteMessageRequest() {
        this.action = "deleteMessage";
        this.token = null;
        this.data = new DeleteMessageData();
    }
    public void setId(Integer id) {
        ((DeleteMessageRequest.DeleteMessageData)this.data).setId(id);
    }
    public void setGroup_id(Integer group_id) {
        ((DeleteMessageRequest.DeleteMessageData)this.data).setGroup_id(group_id);
    }

    public class DeleteMessageData{
        private Integer id;
        private Integer group_id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getGroup_id() { return group_id; }

        public void setGroup_id(Integer group_id) {this.group_id = group_id;}
    }
}
