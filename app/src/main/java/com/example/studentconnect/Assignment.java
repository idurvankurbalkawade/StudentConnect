package com.example.studentconnect;

public class Assignment {
    private String Title;
    private String Instructions;
    private String StartTime;
    private String EndTime;

    public Assignment() {
    }

    public Assignment(String title, String instructions, String startTime, String endTime) {
        this.Title = title;
        this.Instructions = instructions;
        this.StartTime = startTime;
        this.EndTime = endTime;
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
