package com.example.bevproject;

import android.graphics.Bitmap;

public class Articles
{
    private String title;
    private String content;
    private String categ;
    private String author;
    private Bitmap image;
    private int pin;
    private String id;

    //Constructor
    public Articles(String title, String categ, Bitmap image, String content, int pin, String id) {
        this.title = title;
        this.categ = categ;
        this.image = image;
        this.content = content;
        this.pin = pin;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public String getCateg(){ return categ; }
    public Bitmap getImage()
    {
        return image;
    }
    public String getContent() { return content; }

    public String getAuthor() { return id; }
}
