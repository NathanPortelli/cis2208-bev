package com.example.bevproject;

import android.graphics.Bitmap;

public class Articles
{
    private String title;
    private String content;
    private String author;
    private Bitmap image;

    public Articles(String title, Bitmap image/*, String content, String author*/) {
        this.title = title;
        this.image = image;
        //this.content = content;
        //this.author = author;
    }

    public String getTitle() {
        return title;
    }
    public Bitmap getImage() { return image; }
    /*
    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
     */
}
