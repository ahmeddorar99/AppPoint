package com.project.geo.finalproject.Model;

/**
 * Created by A7med on 23/03/2018.
 */

public class PostClass {

    private String username, date, content, fname, lname,startAge, endAge, targetPeople, productType, price, image_url, image_user_url , type;
    private Double longa,lati;

    public PostClass() {

    }

    public PostClass(String username, String date, String content, String fname, String lname, Double aLong, Double lati, String type, String image_user_url) {
        this.username = username;
        this.date = date;
        this.content = content;
        this.fname = fname;
        this.lname = lname;
        this.longa = aLong;
        this.lati = lati;
        this.type = type;
        this.image_user_url = image_user_url;
    }

    public PostClass(String username, String date, String content, String fname, String lname, Double aLong, Double lati, String Type, String image, String image_user_url) {
        this.username = username;
        this.date = date;
        this.content = content;
        this.fname = fname;
        this.lname = lname;
        this.longa = aLong;
        this.lati = lati;
        this.type = Type;
        this.image_url = image;
        this.image_user_url = image_user_url;
    }

    public PostClass(String username, String date, String content, String fname, String lname, Double aLong, Double lati, String startAge, String endAge,
                     String targetPeople, String productType, String price, String type, String image_user_url) {
        this.username = username;
        this.date = date;
        this.content = content;
        this.fname = fname;
        this.lname = lname;
        this.longa = aLong;
        this.lati = lati;
        this.startAge = startAge;
        this.endAge = endAge;
        this.targetPeople = targetPeople;
        this.productType = productType;
        this.price = price;
        this.type = type;
        this.image_user_url = image_user_url;

    }

    public PostClass(String username, String date, String content, String fname, String lname, Double aLong, Double lati, String startAge, String endAge,
                     String targetPeople, String productType, String price, String image, String Type, String image_user_url) {
        this.username = username;
        this.date = date;
        this.content = content;
        this.fname = fname;
        this.lname = lname;
        this.longa = aLong;
        this.lati = lati;
        this.startAge = startAge;
        this.endAge = endAge;
        this.targetPeople = targetPeople;
        this.productType = productType;
        this.price = price;
        this.image_url = image;
        this.type = Type;
        this.image_user_url = image_user_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_user_url() {
        return image_user_url;
    }

    public void setImage_user_url(String image_user_url) {
        this.image_user_url = image_user_url;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Double getLonga() {
        return longa;
    }

    public void setLonga(Double longa) {
        this.longa = longa;
    }

    public Double getLati() {
        return lati;
    }

    public void setLati(Double lati) {
        this.lati = lati;
    }

    public String getStartAge() {
        return startAge;
    }

    public void setStartAge(String startAge) {
        this.startAge = startAge;
    }

    public String getEndAge() {
        return endAge;
    }

    public void setEndAge(String endAge) {
        this.endAge = endAge;
    }

    public String getTargetPeople() {
        return targetPeople;
    }

    public void setTargetPeople(String targetPeople) {
        this.targetPeople = targetPeople;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
