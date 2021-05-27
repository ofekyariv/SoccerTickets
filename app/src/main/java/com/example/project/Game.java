package com.example.project;


public class Game {

    private int id;
    private String city, date, groupA, groupB;

    public Game(String city, String date, String groupA, String groupB) {
        this.city=city;
        this.date=date;
        this.groupA=groupA;
        this.groupB=groupB;
    }

    public Game() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroupA() {
        return groupA;
    }

    public void setGroupA(String groupA) {
        this.groupA = groupA;
    }

    public String getGroupB() {
        return groupB;
    }

    public void setGroupB(String groupB) {
        this.groupB = groupB;
    }
}
