package chat_client.controller.request;

import chat_client.controller.Request;

public class GetMessagesRequest extends Request {
    public GetMessagesRequest() {
        this.action = "getMessages";
        this.token = null;
        this.data = new GetMessagesData();
    }

    public void setId(Integer id) {
        ((GetMessagesRequest.GetMessagesData)this.data).setId(id);
    }

    public class GetMessagesData{
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}

