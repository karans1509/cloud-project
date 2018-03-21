package com.example.karan.auto_attendance;

/**
 * Created by Karan on 14-03-2018.
 */

public class Student {
    String name;
    String id;
    String presence;

    public Student() {

    }

    public Student(String name, String id, String presence) {
        this.name = name;
        this.id = id;
        this.presence = presence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }
}
