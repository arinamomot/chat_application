package chat_client.controller.request;

import chat_client.controller.Request;

public class EditMessageRequest extends Request {
    public EditMessageRequest() {
        this.action = "editMessage";
        this.token = null;
        this.data = new EditMessageData();
    }
    public void setId(Integer id) { ((EditMessageRequest.EditMessageData)this.data).setId(id); }

    public void setGroup_id(Integer group_id) { ((EditMessageRequest.EditMessageData)this.data).setGroup_id(group_id); }

    public void setText(String text){ ((EditMessageRequest.EditMessageData)this.data).setText(text); }

    public class EditMessageData{
        private Integer id;
        private Integer group_id;
        private String text;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getGroup_id() { return group_id; }

        public void setGroup_id(Integer group_id) {this.group_id = group_id;}

        public String getText(){return text;}

        public void setText(String text){this.text = text;}
    }
}
