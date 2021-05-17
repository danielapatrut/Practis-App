package com.techdevs.practis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GalleryRecyclerAdapterTest {

    @Test
    public void getItemCount() {
        List<Image> images = new ArrayList<Image>();
        Image image = new Image("uri","userID",1);
        images.add(image);
        GalleryRecyclerAdapter galleryRecyclerAdapter = new GalleryRecyclerAdapter(images);
        assertEquals(1,galleryRecyclerAdapter.getItemCount());
    }
}