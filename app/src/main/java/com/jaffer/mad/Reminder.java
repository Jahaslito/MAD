package com.jaffer.mad;

public class Reminder {
    private int id;
    private String message;
    private String title;
    private String time;

    public Reminder(int id, String title, String message, String time) {
        this.id = id;
        this.message = message;
        this.title = title;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
