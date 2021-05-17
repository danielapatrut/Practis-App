package com.techdevs.practis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TmrUTRecyclerAdapterTest {

    @Test
    public void getItemCount() {
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task("userID","name",true,true,"dueDate",1);
        tasks.add(task);
        TmrUTRecyclerAdapter tmrUTRecyclerAdapter = new TmrUTRecyclerAdapter(tasks);
        assertEquals(1,tmrUTRecyclerAdapter.getItemCount());
    }
}