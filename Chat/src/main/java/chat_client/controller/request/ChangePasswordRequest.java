package chat_client.controller.request;

import chat_client.controller.Request;

import java.sql.Date;

public class ChangePasswordRequest extends Request {
    public ChangePasswordRequest() {
        this.action = "changePass";
        this.data = new ChangePasswordData();
    }

    public void setPassword(String password) {
        ((ChangePasswordRequest.ChangePasswordData) this.data).setPassword(password);
    }

    public void setPasswordNew(String password) {
        ((ChangePasswordRequest.ChangePasswordData) this.data).setPasswordNew(password);
    }

    public class ChangePasswordData {
        private String password;

        public String getPasswordNew() {
            return passwordNew;
        }

        public void setPasswordNew(String passwordNew) {
            this.passwordNew = passwordNew;
        }

        private String passwordNew;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
