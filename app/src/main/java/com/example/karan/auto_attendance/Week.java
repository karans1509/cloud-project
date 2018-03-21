package com.example.karan.auto_attendance;

import java.util.List;

/**
 * Created by Karan on 12-03-2018.
 */

public class Week {
    String week;
    String date;

    public Week() {

    }

    public Week(String week, String date) {
        this.week = week;
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

