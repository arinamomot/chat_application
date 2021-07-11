package chat_client.controller.request;

import chat_client.controller.Request;

import java.sql.Date;

public class CreateUserRequest extends Request {
    public CreateUserRequest() {
        this.action = "createUser";
        this.data = new RegistrationData();
    }
    public void setFname(String fname) {
        ((CreateUserRequest.RegistrationData)this.data).setFname(fname);
    }
    public void setLname(String lname) {
        ((CreateUserRequest.RegistrationData)this.data).setLname(lname);
    }
    public void setMail(String mail) {
        ((CreateUserRequest.RegistrationData)this.data).setMail(mail);
    }
    public void setPassword(String password) {
        ((CreateUserRequest.RegistrationData)this.data).setPassword(password);
    }
    public void setCountry(String country) {
        ((CreateUserRequest.RegistrationData)this.data).setCountry(country);
    }
    public void setBd(String bd) {
        ((CreateUserRequest.RegistrationData)this.data).setBd(bd);
    }
    public void setGender(String gender) {
        ((CreateUserRequest.RegistrationData)this.data).setGender(gender);
    }

    public static class RegistrationData{
        private String fname;
        private String lname;
        private String mail;
        private String password;
        private String country;
        private String bd;
        private String gender;

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Date getBd() {
//            System.out.println(bd);
//            String newBD = bd.substring(6) + "-" + bd.substring(3,5) + "-" + bd.substring(0,2);
//            System.out.println(newBD);
            return Date.valueOf(bd);
        }

        public void setBd(String bd) {
            this.bd = bd;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
