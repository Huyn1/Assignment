package com.example.assignment.Model;

public class DieuBietOn {
    private String date;
    private String dieu1;
    private String dieu2;
    private String dieu3;
    private String dieu4;
    private String dieu5;

    // No-argument constructor needed for Firestore
    public DieuBietOn() {}

    public DieuBietOn(String date, String dieu1, String dieu2, String dieu3, String dieu4, String dieu5) {
        this.date = date;
        this.dieu1 = dieu1;
        this.dieu2 = dieu2;
        this.dieu3 = dieu3;
        this.dieu4 = dieu4;
        this.dieu5 = dieu5;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDieu1() {
        return dieu1;
    }

    public void setDieu1(String dieu1) {
        this.dieu1 = dieu1;
    }

    public String getDieu2() {
        return dieu2;
    }

    public void setDieu2(String dieu2) {
        this.dieu2 = dieu2;
    }

    public String getDieu3() {
        return dieu3;
    }

    public void setDieu3(String dieu3) {
        this.dieu3 = dieu3;
    }

    public String getDieu4() {
        return dieu4;
    }

    public void setDieu4(String dieu4) {
        this.dieu4 = dieu4;
    }

    public String getDieu5() {
        return dieu5;
    }

    public void setDieu5(String dieu5) {
        this.dieu5 = dieu5;
    }
}
