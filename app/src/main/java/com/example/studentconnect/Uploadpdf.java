package com.example.studentconnect;

public class Uploadpdf {

    private String name;
    private String url;
    private String Title;
    private String Instructions;
    private String StartTime;
    private String EndTime;

    public Uploadpdf() {
    }

    public Uploadpdf(String name, String url, String title, String instructions, String startTime, String endTime) {
        this.name = name;
        this.url = url;
        Title = title;
        Instructions = instructions;
        StartTime = startTime;
        EndTime = endTime;
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
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
