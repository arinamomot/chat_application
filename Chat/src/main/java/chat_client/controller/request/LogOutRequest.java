package chat_client.controller.request;

import chat_client.controller.Request;

public class LogOutRequest extends Request {
    public LogOutRequest() {
        this.action = "logout";
        this.token = null;
    }
}