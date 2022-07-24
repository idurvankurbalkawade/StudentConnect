package com.example.studentconnect;
public class MainModel {

    String email, studentClass, studentName;

    public MainModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public MainModel(String email, String studentClass, String studentName) {
        this.email = email;
        this.studentClass = studentClass;
        this.studentName = studentName;

    }
}