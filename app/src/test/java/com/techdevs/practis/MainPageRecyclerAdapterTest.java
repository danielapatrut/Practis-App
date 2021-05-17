package com.techdevs.practis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MainPageRecyclerAdapterTest {

    @Test
    public void getItemViewType() {
        List<Page> pageList = new ArrayList<Page>();
        Page page = new Page(true,"title","content","uri",false,1);
        pageList.add(page);
        MainPageRecyclerAdapter mainPageRecyclerAdapter = new MainPageRecyclerAdapter(pageList, new MainPageRecyclerAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {

            }
        });
        assertEquals(0,mainPageRecyclerAdapter.getItemViewType(0));
    }

    @Test
    public void getItemCount() {
        List<Page> pageList = new ArrayList<Page>();
        Page page = new Page(true,"title","content","uri",false,1);
        pageList.add(page);
        MainPageRecyclerAdapter mainPageRecyclerAdapter = new MainPageRecyclerAdapter(pageList, new MainPageRecyclerAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {

            }
        });
        assertEquals(1,mainPageRecyclerAdapter.getItemCount());
    }

}
