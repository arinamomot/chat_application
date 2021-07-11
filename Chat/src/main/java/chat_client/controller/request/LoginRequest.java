package chat_client.controller.request;

import chat_client.controller.Request;

public class LoginRequest extends Request {
   public LoginRequest() {
       this.action = "login";
       this.token = null;
       this.data = new LoginData();
   }
   public void setMail(String mail) {
       ((LoginData)this.data).setMail(mail);
   }
   public void setPassword(String password) {
       ((LoginData)this.data).setPassword(password);
   }

   public static class LoginData{
       private String mail;
       private String password;

       public String getMail() {
           return mail;
       }

       public void setMail(String mail) {
           this.mail = mail;
       }

       public String getPassword() {
           return password;
       }

       public void setPassword(String password) {
           this.password = password;
       }
   }
}
