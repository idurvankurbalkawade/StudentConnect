package com.example.studentconnect;

public class MainModel2 {

    String email,fullName,subject;

    public MainModel2() {
    }

    public MainModel2(String email, String fullname, String subject) {
        this.email = email;
        this.fullName = fullName;
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
