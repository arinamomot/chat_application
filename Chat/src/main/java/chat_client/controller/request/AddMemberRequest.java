package chat_client.controller.request;

import chat_client.controller.Request;

public class AddMemberRequest extends Request {
    public AddMemberRequest() {
        this.action = "addMember";
        this.token = null;
        this.data = new AddMemberData();
    }

    public void setUser_id(Integer user_id) {
        ((AddMemberRequest.AddMemberData)this.data).setUser_id(user_id);
    }

    public void setGroup_id(Integer group_id) {
        ((AddMemberRequest.AddMemberData)this.data).setGroup_id(group_id);
    }

    public void setGroup_type(Boolean group_type) { ((AddMemberRequest.AddMemberData)this.data).setGroup_type(group_type); }

    public class AddMemberData{
        private Integer user_id;
        private Integer group_id;
        private Boolean group_type;

        public Boolean getGroup_type() {
            return group_type;
        }

        public void setGroup_type(Boolean group_type) {
            this.group_type = group_type;
        }


        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getGroup_id() { return group_id; }

        public void setGroup_id(Integer group_id) {this.group_id = group_id;}
    }
}

