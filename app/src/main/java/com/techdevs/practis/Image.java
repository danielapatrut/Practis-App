package com.techdevs.practis;

import android.net.Uri;

public class Image {
    private String uri;
    private String userID;
    private int imageID;

    public Image() {
    }

    public Image(String uri) {
        this.uri = uri;
    }

    public Image(String uri, String userID, int imageID) {
        this.uri = uri;
        this.userID = userID;
        this.imageID = imageID;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
