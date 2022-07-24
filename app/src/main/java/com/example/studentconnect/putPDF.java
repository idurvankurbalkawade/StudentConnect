package com.example.studentconnect;

public class putPDF {

    public String name;
    public String url;
    public String title;

    public putPDF() {
    }

    public putPDF(String name, String url, String title) {
        this.name = name;
        this.url = url;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
