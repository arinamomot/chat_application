package chat_client.controller.request;

import chat_client.controller.Request;

public class GetSenderRequest extends Request {
    public GetSenderRequest() {
        this.action = "getSender";
        this.token = null;
        this.data = new GetSenderData();
    }

    public void setId(Integer id) {
        ((GetSenderRequest.GetSenderData) this.data).setId(id);
    }

    public class GetSenderData {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
