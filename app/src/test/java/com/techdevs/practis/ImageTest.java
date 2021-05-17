package com.techdevs.practis;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageTest {

    @Test
    public void getUri() {
        Image image = new Image("uri");
        String uri = image.getUri();
        assertEquals("uri",uri);
    }

    @Test
    public void setUri() {
        Image image = new Image();
        image.setUri("uri");
        assertEquals("uri",image.getUri());
    }

    @Test
    public void getUserID() {
        Image image = new Image("uri","userID",1);
        String userID = image.getUserID();
        assertEquals("userID",userID);
    }

    @Test
    public void setUserID() {
        Image image = new Image();
        image.setUserID("userID");
        assertEquals("userID", image.getUserID());
    }

    @Test
    public void getImageID() {
        Image image = new Image("uri","userID",1);
        int imageID = image.getImageID();
        assertEquals(1,imageID);
    }

    @Test
    public void setImageID() {
        Image image = new Image();
        image.setImageID(1);
        assertEquals(1, image.getImageID());
    }
}