package pk.edu.nu.smd.seekers.Models;

public class User {

    String userID;
    String fullname;
    String email;
    String gender;
    String password;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User() {

    }

    public User(String userID, String fullname, String email, String gender, String password) {

        this.userID = userID;
        this.fullname = fullname;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
