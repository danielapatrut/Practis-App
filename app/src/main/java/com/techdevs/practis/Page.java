package com.techdevs.practis;

import java.io.File;

//page editor
public class Page {
    private static String userID; //current user
    private boolean hasCoverImage = false;
    private String title;
    private String content;
    private String uri = "";

    private boolean newPage=true;
    private int pageID;

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

    public Page(boolean hasCoverImage, String title, String content, String uri, boolean newPage, int pageID) {
        this.hasCoverImage = hasCoverImage;
        this.title = title;
        this.content = content;
        this.uri = uri;
        this.newPage = newPage;
        this.pageID = pageID;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
