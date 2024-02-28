package com.checking.choaching_app.models;

public class Notification {

    private String imgUrl, title, date, message;

    public Notification(String imgUrl, String title, String date, String message) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.date = date;
        this.message = message;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
