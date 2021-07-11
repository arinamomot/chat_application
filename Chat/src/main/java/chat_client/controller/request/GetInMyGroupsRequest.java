package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.GroupEntity;

public class GetInMyGroupsRequest extends Request {
    public GetInMyGroupsRequest() {
        this.action = "getInMyGroups";
        this.token = null;
        this.data = new GetInMyGroupsData();
    }

    public void setTitle(String title) {
        ((GetInMyGroupsRequest.GetInMyGroupsData) this.data).setTitle(title);
    }

    public static class GetInMyGroupsData {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
