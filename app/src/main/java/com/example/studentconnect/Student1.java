package com.example.studentconnect;

public class Student1 {
    public String name;
    public String url;

    public Student1() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Student1(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
