package chat_client.controller.request;

import chat_client.controller.Request;

public class GetGroupsRequest extends Request {
    public GetGroupsRequest() {
        this.action = "getGroups";
        this.token = null;
        this.data = new GetGroupsData();
    }
    public void setTitle(String title) {
        ((GetGroupsRequest.GetGroupsData)this.data).setTitle(title);
    }

    public class GetGroupsData{
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
