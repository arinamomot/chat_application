package chat_client.controller;

public class Request {
    public String action;
    public String token;
    public Object data;
    public String error;

    @Override
    public String toString() {
        return "Request{" +
                "action='" + action + '\'' +
                ", token='" + token + '\'' +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
