package com.project.geo.finalproject.Model;

public class FeedbackClass {

   private String user,message;

    public FeedbackClass() {
    }

    public FeedbackClass(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
