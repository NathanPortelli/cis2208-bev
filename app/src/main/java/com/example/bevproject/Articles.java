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

    public Articles(String title, String categ, Bitmap image, String content, int pin/*, String author*/) {
        this.title = title;
        this.categ = categ;
        this.image = image;
        this.content = content;
        this.pin = pin;
        //this.author = author;
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
    public int getPin() { return pin; }
    public String getAuthor() { return author; }
}
