package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.GroupEntity;
import chat_server.model.UserEntity;

public class GetCreatorRequest extends Request {
    public GetCreatorRequest() {
        this.action = "getCreator";
        this.token = null;
        this.data = new GetCreatorData();
    }

    public void setId(Integer id) {
        ((GetCreatorRequest.GetCreatorData)this.data).setId(id);
    }

    public class GetCreatorData {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

}
