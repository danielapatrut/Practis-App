package com.techdevs.practis;

import java.io.File;

//page editor
public class Page {
    private static String userID; //current user
    private static int numberOfPages = 0; //stores the number of pages created by the user
    private boolean hasCoverImage = false;
    private String title;
    //private String content;
    private File content; //store content as markdown

    public Page() {
    }
    public Page(String userID, boolean hasCoverImage, String title) {
        this.hasCoverImage = hasCoverImage;
        this.title = title;
    }
    public Page(String userID, boolean hasCoverImage, String title, File content) {
        this.userID = userID;
        this.hasCoverImage = hasCoverImage;
        this.title = title;
        this.content = content;
    }

    public static int getNumberOfPages() {
        return numberOfPages;
    }
    public static void increaseNumberOfPages(){
        numberOfPages++;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isHasCoverImage() {
        return hasCoverImage;
    }

    public void setHasCoverImage(boolean hasCoverImage) {
        this.hasCoverImage = hasCoverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getContent() {
        return content;
    }

    public void setContent(File content) {
        this.content = content;
    }
}
