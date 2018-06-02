package com.project.geo.finalproject.Model;

public class Tech_Problem {

    private String contentText, username, date;

    public Tech_Problem() {

    }

    public Tech_Problem(String content, String username, String date) {
        this.contentText = content;
        this.username = username;
        this.date = date;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
