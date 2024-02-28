package com.checking.choaching_app.models;

public class Video {
    private String videoUrl, title, subject, description;

    public Video(String videoUrl, String title, String subject, String description) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.subject = subject;
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
