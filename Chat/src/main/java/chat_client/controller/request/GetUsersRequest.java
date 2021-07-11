package chat_client.controller.request;

import chat_client.controller.Request;

public class GetUsersRequest extends Request {
    public GetUsersRequest() {
        this.action = "getUsers";
        this.token = null;
        this.data = new GetUsersData();
    }

    public void setFName(String fname) {
        ((GetUsersRequest.GetUsersData) this.data).setFname(fname);
    }

    public void setLName(String lname) {
        ((GetUsersRequest.GetUsersData) this.data).setLname(lname);
    }


    public class GetUsersData {
        private String fname;
        private String lname;

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }
    }
}

