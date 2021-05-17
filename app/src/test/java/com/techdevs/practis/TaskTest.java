package com.techdevs.practis;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void getUserID() {
        Task task = new Task("userID","name",true, false, "12.07.2021", 1);
        String userID = task.getUserID();
        assertEquals("userID",userID);
    }

    @Test
    public void setUserID() {
        Task task = new Task();
        task.setUserID("userID");
        assertEquals("userID",task.getUserID());
    }


    @Test
    public void getName() {
        Task task = new Task("userID","name",true, false, "12.07.2021", 1);
        String name = task.getName();
        assertEquals("name",name);
    }

    @Test
    public void setName() {
        Task task = new Task();
        task.setName("name");
        assertEquals("name",task.getName());
    }

    @Test
    public void isDone() {
        Task task = new Task("userID","name",true, false, "12.07.2021", 1);
        boolean done = task.isDone();
        assertEquals(true,done);
    }

    @Test
    public void setDone() {
        Task task = new Task();
        task.setDone(true);
        assertEquals(true,task.isDone());
    }

    @Test
    public void isUrgent() {
        Task task = new Task("userID","name",true, false, "12.07.2021", 1);
        boolean urgent = task.isUrgent();
        assertEquals(false, urgent);
    }

    @Test
    public void setUrgent() {
        Task task = new Task();
        task.setUrgent(false);
        assertEquals(false,task.isUrgent());
    }

    @Test
    public void getDueDate() {
        Task task = new Task("userID","name",true, false, "12.07.2021", 1);
        String dueDate = task.getDueDate();
        assertEquals("12.07.2021",dueDate);
    }

    @Test
    public void setDueDate() {
        Task task = new Task();
        task.setDueDate("12.07.2021");
        assertEquals("12.07.2021",task.getDueDate());
    }

    @Test
    public void getTaskID() {
        Task task = new Task("userID","name",true, false, "12.07.2021", 1);
        int taskID = task.getTaskID();
        assertEquals(1,taskID);
    }

    @Test
    public void setTaskID() {
        Task task = new Task();
        task.setTaskID(1);
        assertEquals(1,task.getTaskID());
    }
}