package chat_client.controller.request;

import chat_client.controller.Request;

public class RemoveMemberRequest extends Request {
    public RemoveMemberRequest() {
        this.action = "removeMember";
        this.token = null;
        this.data = new RemoveMemberData();
    }
    public void setUser_id(Integer user_id) {
        ((RemoveMemberData)this.data).setUser_id(user_id);
    }
    public void setGroup_id(Integer group_id) {
        ((RemoveMemberData)this.data).setGroup_id(group_id);
    }

    public class RemoveMemberData {
        private Integer user_id;
        private Integer group_id;

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getGroup_id() { return group_id; }

        public void setGroup_id(Integer group_id) {this.group_id = group_id;}

        @Override
        public String toString() {
            return "RemoveMemberData{" +
                    "user_id=" + user_id +
                    ", group_id=" + group_id +
                    '}';
        }
    }
}

