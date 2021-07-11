package chat_client.controller.request;

import chat_client.controller.Request;

public class ChangeRightsRequest extends Request {
    public ChangeRightsRequest() {
        this.action = "changeRights";
        this.token = null;
        this.data = new ChangeRightsData();
    }
    public void setUser_id(Integer user_id) {
        ((ChangeRightsRequest.ChangeRightsData)this.data).setUser_id(user_id);
    }
    public void setGroup_id(Integer group_id) {
        ((ChangeRightsRequest.ChangeRightsData)this.data).setGroup_id(group_id);
    }
    public void setRights(String rights) {
        ((ChangeRightsRequest.ChangeRightsData)this.data).setRights(rights);
    }

    public class ChangeRightsData{
        private Integer user_id;
        private Integer group_id;
        private String rights;

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getGroup_id() {
            return group_id;
        }

        public void setGroup_id(Integer group_id) {
            this.group_id = group_id;
        }

        public String getRights() {
            return rights;
        }

        public void setRights(String rights) {
            this.rights = rights;
        }
    }
}
