package com.example.bevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.Nullable;
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
        db.execSQL("create Table users(id integer primary key autoincrement, email Text, name Text, password Text, bio Text, img Blob)");
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
        {
            contentValues.put("pin", 0);
            db.update(ARTICLES_TABLE, contentValues, "title = ?", new String[]{title});
            return false;
        }
        else{
            contentValues.put("pin", 1);
            db.update(ARTICLES_TABLE, contentValues, "title = ?", new String[]{title});
            return true;
        }
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

    /*public Boolean insertUser(Users user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user.getEmail());
        contentValues.put("name", user.getName());
        contentValues.put("password", user.getPassword());
        contentValues.put("bio", user.getBio());
        contentValues.put("img", user.getImg());

        long userDetails = db.insert("users", null, contentValues);
        if(userDetails == -1)
            return false;
        else
            return true;
    }*/

    public Boolean insertUser(Users user)
    {
        boolean result = true;
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("email", user.getEmail());
            contentValues.put("name", user.getName());
            contentValues.put("password", user.getPassword());
            contentValues.put("bio", user.getBio());
            contentValues.put("img", user.getImg());

            result = db.insert("users", null, contentValues) > 0;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Users findUser(int id)
    {
        Users user = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users where id = ?", new String[] { String.valueOf(id) });
            if(cursor.moveToFirst()) {
                user = new Users();
                user.setId((cursor.getInt(0)));
                user.setEmail(cursor.getString(1));
                user.setName(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setBio(cursor.getString(4));
                user.setImg(cursor.getBlob(5));
            }
        } catch (Exception e){
            user = null;
        }
        return user;
    }

    public Boolean updateUser(Users user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user.getEmail());
        contentValues.put("name", user.getName());
        contentValues.put("password", user.getPassword());
        contentValues.put("bio", user.getBio());
        contentValues.put("img", user.getImg());

        long userDetails = db.update("users", contentValues, "id" + " = ?", new String[] { String.valueOf(user.getId()) });
        if(userDetails == -1)
            return false;
        else
            return true;
    }

    //Check Login
    public Users checkLogin(String email, String password)
    {
        Users user = null;
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users where email = ? and password = ?", new String[] { email, password });
            if(cursor.moveToFirst()) {
                user = new Users();
                user.setId((cursor.getInt(0)));
                user.setEmail(cursor.getString(1));
                user.setName(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setBio(cursor.getString(4));
                user.setImg(cursor.getBlob(5));
            }
        } catch (Exception e){
            user = null;
        }
        return user;
    }

    //Check Email
    public Users checkEmail(String email)
    {
        Users user = null;
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users where email = ?", new String[] { email });
            if(cursor.moveToFirst()) {
                user = new Users();
                user.setId((cursor.getInt(0)));
                user.setEmail(cursor.getString(1));
                user.setName(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setBio(cursor.getString(4));
                user.setImg(cursor.getBlob(5));
            }
        } catch (Exception e){
            user = null;
        }
        return user;
    }

   /* public Boolean updateUser(String email, String name, String password, String bio, byte[] img)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("bio", bio);
        contentValues.put("img", img);

        long userDetails = db.update("users", contentValues, null);
        if(userDetails == -1)
            return false;
        else
            return true;
    } */

   /* //if exists
    public Boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email = ?", new String[] {email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    } */

    //check login
    /*public Boolean checkLogin(String email, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email = ? and password = ?", new String[] {email, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }*/

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