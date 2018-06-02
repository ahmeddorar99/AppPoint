package com.project.geo.finalproject.Model;

/**
 * Created by A7med on 24/01/2018.
 */
public class user {

    private String gender, first_name, last_name, email, password, phone, date, activation, image_url;

    public user() {

    }

    public user(String first_name, String last_name, String email, String password, String phone, String date, String gender, String activation) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.date = date;
        this.gender = gender;
        this.activation = activation;
    }
    public user(String first_name, String last_name, String email, String password, String phone, String date, String gender, String activation, String image_url) {
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.date = date;
        this.activation = activation;
        this.image_url = image_url;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getActivation() {
        return activation;
    }

    public void Activation() {
        this.activation = "true";
    }

    public String getFirst_name() {
        return first_name;
    }


    public String getLast_name() {
        return last_name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
