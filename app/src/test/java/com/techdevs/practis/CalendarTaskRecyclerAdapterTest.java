package com.techdevs.practis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalendarTaskRecyclerAdapterTest {

    @Test
    public void getItemCount() {
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task("userID","name",true,true,"dueDate",1);
        tasks.add(task);
        CalendarTaskRecyclerAdapter calendarTaskRecyclerAdapter = new CalendarTaskRecyclerAdapter(tasks);
        assertEquals(1,calendarTaskRecyclerAdapter.getItemCount());
    }
}