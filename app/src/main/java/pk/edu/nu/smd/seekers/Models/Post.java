package pk.edu.nu.smd.seekers.Models;

import java.io.Serializable;

public class Post implements Serializable {
    String postID;
    String postTitle;
    String userID;
    String userName;
    String postDescription;
    String location;
    int totalAmount;
    int raisedAmount;
    int days;
    int backedBy;


    public Post() {
    }

    public Post(String postID, String postTitle, String userID, String userName, String postDescription, String location, int totalAmount, int raisedAmount, int days, int backedBy) {
        this.postID = postID;
        this.postTitle = postTitle;
        this.userID = userID;
        this.userName = userName;
        this.postDescription = postDescription;
        this.location = location;
        this.totalAmount = totalAmount;
        this.raisedAmount = raisedAmount;
        this.days = days;
        this.backedBy = backedBy;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getRaisedAmount() {
        return raisedAmount;
    }

    public void setRaisedAmount(int raisedAmount) {
        this.raisedAmount = raisedAmount;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getBackedBy() {
        return backedBy;
    }

    public void setBackedBy(int backedBy) {
        this.backedBy = backedBy;
    }
}
