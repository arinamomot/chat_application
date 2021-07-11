package chat_client.controller.request;

import chat_client.controller.Request;

public class GetRightsRequest extends Request {
    public GetRightsRequest() {
        this.action = "getRights";
        this.token = null;
        this.data = new GetRightsData();
    }

    public void setUser_id(Integer user_id) {
        ((GetRightsRequest.GetRightsData) this.data).setUser_id(user_id);
    }

    public void setGroup_id(Integer group_id) {
        ((GetRightsRequest.GetRightsData) this.data).setGroup_id(group_id);
    }


    public static class GetRightsData {
        private Integer user_id;
        private Integer group_id;

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
    }
}
