package chat_client.controller.request;

import chat_client.controller.Request;

public class DeleteUserRequest extends Request {
    public DeleteUserRequest() {
        this.action = "deleteUser";
        this.token = null;
        this.data =null;
    }

}

