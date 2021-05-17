package com.techdevs.practis;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageTest {

    @Test
    public void getUri() {
       Page page = new Page(true, "title", "content", "uri", false, 1);
        String uri = page.getUri();
        assertEquals("uri",uri);
    }

    @Test
    public void setUri() {
        Page page = new Page();
        page.setUri("uri");
        assertEquals("uri",page.getUri());
    }

    @Test
    public void getPageID() {
        Page page = new Page(true, "title", "content", "uri", false, 1);
        int pageID = page.getPageID();
        assertEquals(1,pageID);
    }

    @Test
    public void setPageID() {
        Page page = new Page();
        page.setPageID(1);
        assertEquals(1,page.getPageID());
    }

    @Test
    public void isNewPage() {
        Page page = new Page(true, "title", "content", "uri", false, 1);
        boolean newpage = page.isNewPage();
        assertEquals(false,newpage);
    }

    @Test
    public void setNewPage() {
        Page page = new Page();
        page.setNewPage(false);
        assertEquals(false,page.isNewPage());
    }

    @Test
    public void isHasCoverImage() {
        Page page = new Page(true, "title", "content", "uri", false, 1);
        boolean newpage = page.isHasCoverImage();
        assertEquals(true,newpage);
    }

    @Test
    public void setHasCoverImage() {
        Page page = new Page();
        page.setHasCoverImage(true);
        assertEquals(true,page.isHasCoverImage());
    }

    @Test
    public void getTitle() {
        Page page = new Page(true, "title", "content", "uri", false, 1);
        String title = page.getTitle();
        assertEquals("title",title);
    }

    @Test
    public void setTitle() {
        Page page = new Page();
        page.setTitle("title");
        assertEquals("title",page.getTitle());
    }

    @Test
    public void getContent() {
        Page page = new Page(true, "title", "content", "uri", false, 1);
        String content = page.getContent();
        assertEquals("content",content);
    }

    @Test
    public void setContent() {
        Page page = new Page();
        page.setContent("content");
        assertEquals("content",page.getContent());
    }
}