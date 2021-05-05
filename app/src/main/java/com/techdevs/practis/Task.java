package com.techdevs.practis;

public class Task {
    private String userID;
    private String name;
    private boolean done;
    private boolean urgent;
    private String dueDate;
    private String taskID;

    public Task(String userID, String name, boolean done, boolean urgent, String dueDate, String taskID) {
        this.userID = userID;
        this.name = name;
        this.done = done;
        this.urgent = urgent;
        this.dueDate = dueDate;
        this.taskID = taskID;
    }

    public Task() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
}
