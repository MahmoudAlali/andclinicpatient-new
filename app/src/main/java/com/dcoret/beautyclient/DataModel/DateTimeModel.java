package com.dcoret.beautyclient.DataModel;


public class DateTimeModel {
    String date, time;
    int position;

    public DateTimeModel(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public DateTimeModel(String date, String time, int position) {
        this.date = date;
        this.time = time;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
