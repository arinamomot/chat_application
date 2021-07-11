package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.UserEntity;

public class GetMeRequest extends Request {
    public GetMeRequest() {
        this.action = "getMe";
        this.token = null;
        this.data = new UserEntity();
    }
}
