package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.UserEntity;

public class GetUserInfoRequest extends Request {
    public GetUserInfoRequest() {
        this.action = "getUserInfo";
        this.token = null;
        this.data = new GetUserInfoData();
    }

    public void setId(Integer id) {((GetUserInfoRequest.GetUserInfoData) this.data).setId(id);}

    public static class GetUserInfoData{
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
