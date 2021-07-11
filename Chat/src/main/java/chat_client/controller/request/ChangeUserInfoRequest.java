package chat_client.controller.request;

import chat_client.controller.Request;

import java.sql.Date;
import java.time.LocalDate;

public class ChangeUserInfoRequest extends Request {
    public ChangeUserInfoRequest() {
        this.action = "changeUserInfo";
        this.data = new ChangeUserInfoData();
    }
    public void setFname(String fname) {
        ((ChangeUserInfoRequest.ChangeUserInfoData)this.data).setFname(fname);
    }
    public void setLname(String lname) {
        ((ChangeUserInfoRequest.ChangeUserInfoData)this.data).setLname(lname);
    }
    public void setCountry(String country) {
        ((ChangeUserInfoRequest.ChangeUserInfoData)this.data).setCountry(country);
    }
    public void setBd(LocalDate bd) {
        ((ChangeUserInfoRequest.ChangeUserInfoData)this.data).setBd(bd);
    }
    public void setGender(String gender) {
        ((ChangeUserInfoRequest.ChangeUserInfoData)this.data).setGender(gender);
    }

    public static class ChangeUserInfoData {
        private String fname;
        private String lname;
        private String country;
        private LocalDate bd;
        private String gender;

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

        public void setBd(LocalDate bd) {
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
