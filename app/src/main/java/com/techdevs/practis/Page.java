package com.techdevs.practis;

import java.io.File;

//page editor
public class Page {
    private static String userID; //current user
    private boolean hasCoverImage = false;
    private String title;
    private String content;
    //private File content; //store content as markdown
    private boolean newPage=true;

    public Page() {
    }
    public Page(String userID, boolean hasCoverImage, String title) {
        this.hasCoverImage = hasCoverImage;
        this.title = title;
    }
    public Page(boolean hasCoverImage, String title,String content){
        this.hasCoverImage=hasCoverImage;
        this.content=content;
        this.title=title;
    }
    /*public Page(String userID, boolean hasCoverImage, String title, File content) {
        this.userID = userID;
        this.hasCoverImage = hasCoverImage;
        this.title = title;
        this.content = content;
    }*/


    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
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

    /*public File getContent() {
        return content;
    }

    public void setContent(File content) {
        this.content = content;
    }*/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
