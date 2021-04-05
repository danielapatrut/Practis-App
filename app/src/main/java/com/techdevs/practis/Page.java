package com.techdevs.practis;

//page editor
public class Page {
    private static int numberOfPages = 0; //stores the number of pages created by the user

    public Page() {
    }

    public static int getNumberOfPages() {
        return numberOfPages;
    }
    public void increaseNumberOfPages(){
        numberOfPages++;
    }
}
