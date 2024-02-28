package com.checking.choaching_app.models;

public class Teacher {
    private String name, email, teacherImage, phoneNumber, subject, about;

    public Teacher(String name, String email, String teacherImage, String phoneNumber, String subject, String about) {
        this.name = name;
        this.email = email;
        this.teacherImage = teacherImage;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
