package com.example.bevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String ARTICLES_TABLE = "articles";
    private static final String ARTICLES_COLUMN_TITLE = "title";
    private static final String ARTICLES_COLUMN_IMAGE = "img";
    private static final String ARTICLES_COLUMN_TEXT = "content";
    private static final String ARTICLES_COLUMN_CATEG = "category";
    private static final String ARTICLES_COLUMN_PIN = "pin";

    List<Articles> articleList = new ArrayList<>();

    public DBHelper(@Nullable Context context)
    {
        super(context, "BEVDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create Table users(email Text primary key, name Text, password Text, bio Text, img Blob)");
        db.execSQL("create Table articles(title Text primary key, content Text, category Text, img Blob, pin integer default 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists articles");
    }

    public Boolean insertArticle(String title, String content, String category, byte[] img)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("category", category);
        contentValues.put("img", img);

        long articleDetails = db.insert("articles", null, contentValues);
        if(articleDetails == -1)
            return false;
        else
            return true;
    }

    public Boolean pinArticle(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " where " + ARTICLES_COLUMN_TITLE + " =? ",  new String[]{title});
        int checkPin = 1;
        while(cursor.moveToNext())
        {
            checkPin = cursor.getInt(cursor.getColumnIndex(ARTICLES_COLUMN_PIN));
        }
        if(checkPin == 1)
            return false;

        contentValues.put("pin", 1);
        db.update(ARTICLES_TABLE, contentValues, "title = ?", new String[]{title});
        return true;
    }

    public List<Articles> getAllPinnedArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " where " + ARTICLES_COLUMN_PIN + " =1 order by " + ARTICLES_COLUMN_TITLE, null);

        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_IMAGE);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] image = cursor.getBlob(imgIndex);
            Bitmap bmImage = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);

            Articles article = new Articles(title, categ, bmImage, text, pin);
            articleList.add(article);
        }
        return articleList;
    }

    public List<Articles> getAllArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " order by " + ARTICLES_COLUMN_TITLE, null);

        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_IMAGE);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] image = cursor.getBlob(imgIndex);
            Bitmap bmImage = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);

            Articles article = new Articles(title, categ, bmImage, text, pin);
            articleList.add(article);
        }
        return articleList;
    }

    public List<Articles> getPoliticsArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " where category=?", new String[] {"Politics"});

        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_IMAGE);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] image = cursor.getBlob(imgIndex);
            Bitmap bmImage = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);

            Articles article = new Articles(title, categ, bmImage, text, pin);
            articleList.add(article);
        }
        return articleList;
    }

    public List<Articles> getSocialArticles()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " where category=?", new String[] {"Social"});

        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_IMAGE);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] image = cursor.getBlob(imgIndex);
            Bitmap bmImage = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);

            Articles article = new Articles(title, categ, bmImage, text, pin);
            articleList.add(article);
        }
        return articleList;
    }

    public List<Articles> getOpinionArticles()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " where category=?", new String[] {"Opinion"});

        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_IMAGE);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] image = cursor.getBlob(imgIndex);
            Bitmap bmImage = BitmapFactory.decodeByteArray(image, 0 ,image.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);

            Articles article = new Articles(title, categ, bmImage, text, pin);
            articleList.add(article);
        }
        return articleList;
    }

    public Boolean insertUser(String email, String name, String password, String bio, byte[] img)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("bio", bio);
        contentValues.put("img", img);

        long userDetails = db.insert("users", null, contentValues);
        if(userDetails == -1)
            return false;
        else
            return true;
    }

    //if exists
    public Boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email = ?", new String[] {email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //check login
    public Boolean checkLogin(String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email = ? and password = ?", new String[] {email, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //if exists
    public Boolean checkTitle(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from articles where title = ?", new String[] {title});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}