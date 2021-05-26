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
    //Tables and column names in articles
    private static final String ARTICLES_TABLE = "articles";
    private static final String ARTICLES_COLUMN_TITLE = "title";
    private static final String ARTICLES_COLUMN_img = "img";
    private static final String ARTICLES_COLUMN_TEXT = "content";
    private static final String ARTICLES_COLUMN_CATEG = "category";
    private static final String ARTICLES_COLUMN_PIN = "pin";
    private static final String ARTICLES_COLUMN_ID = "id";

    private static final String USERS_TABLE = "users";
    private static final String USERS_TABLE_ID = "id";
    private static final String USERS_TABLE_NAME = "name";
    private static final String USERS_TABLE_EMAIL = "email";
    private static final String USERS_TABLE_PASSWORD = "password";
    private static final String USERS_TABLE_BIO = "bio";
    private static final String USERS_TABLE_IMG = "image";

    //ArrayList of all articles in the db
    List<Articles> articleList = new ArrayList<>();
    //Constructor
    public DBHelper(@Nullable Context context)
    {
        super(context, "BEVDB.db", null, 1);
    }

    //Creation of tables on create of activity using it
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create Table users(id integer primary key autoincrement, email Text, name Text, password Text, bio Text, image Blob)");
        db.execSQL("create Table articles(title Text primary key, content Text, category Text, img Blob, pin integer default 0, id integer, foreign key (id) references users(id))");
    }

    //When a new version of the db is set above
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists articles");
    }

    //On submission of a new articles
    public Boolean insertArticle(String title, String content, String category, byte[] img, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //Write to db
        ContentValues contentValues = new ContentValues();
        contentValues.put(ARTICLES_COLUMN_TITLE, title);
        contentValues.put(ARTICLES_COLUMN_TEXT, content);
        contentValues.put(ARTICLES_COLUMN_CATEG, category);
        contentValues.put(ARTICLES_COLUMN_img, img);
        contentValues.put(ARTICLES_COLUMN_ID, id);

        long articleDetails = db.insert(ARTICLES_TABLE, null, contentValues);
        if(articleDetails == -1) //In case of error
            return false;
        else //Success
            return true;
    }

    //When user pins/unpins an article
    public Boolean pinArticle(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //Write to db
        ContentValues contentValues = new ContentValues();
        // Querying for particular article record
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " where " + ARTICLES_COLUMN_TITLE + " =? ",  new String[]{title});
        int checkPin = 1;
        while(cursor.moveToNext())
        {
            checkPin = cursor.getInt(cursor.getColumnIndex(ARTICLES_COLUMN_PIN));
        }
        if(checkPin == 1)
        {
            //Unpinned
            contentValues.put(ARTICLES_COLUMN_PIN, 0);
            db.update(ARTICLES_TABLE, contentValues, "title = ?", new String[]{title});
            return false;
        }
        else
        {
            //Pinned
            contentValues.put(ARTICLES_COLUMN_PIN, 1);
            db.update(ARTICLES_TABLE, contentValues, "title = ?", new String[]{title});
            return true;
        }
    }

    //When user accesses 'Pinned Articles' from bottom nav bar
    public List<Articles> getAllPinnedArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Read from db
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " inner join " + USERS_TABLE + " on " + ARTICLES_TABLE + "." + ARTICLES_COLUMN_ID + " = " + USERS_TABLE + "." + USERS_TABLE_ID + " where " + ARTICLES_COLUMN_PIN + " =1 order by " + ARTICLES_COLUMN_TITLE, null);

        //Adding all records from above query
        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_img);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);
            int idIndex = cursor.getColumnIndex(ARTICLES_COLUMN_ID);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] img = cursor.getBlob(imgIndex);
            Bitmap bmimg = BitmapFactory.decodeByteArray(img, 0 ,img.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);
            String id = cursor.getString(idIndex);

            Articles article = new Articles(title, categ, bmimg, text, pin, id);
            articleList.add(article);
        }
        return articleList;
    }

    //Method to retrieve user's articles
    public List<Articles> getAllArticlesById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Read from db
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " inner join " + USERS_TABLE + " on " + ARTICLES_TABLE + "." + ARTICLES_COLUMN_ID + " = " + USERS_TABLE + "." + USERS_TABLE_ID + " where " + USERS_TABLE + "." + USERS_TABLE_ID+"=?" + " order by " + ARTICLES_COLUMN_TITLE, new String[] {String.valueOf(id)});

        //Adding all records from above query
        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_img);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);
            int idIndex = cursor.getColumnIndex(USERS_TABLE_NAME);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] img = cursor.getBlob(imgIndex);
            Bitmap bmimg = BitmapFactory.decodeByteArray(img, 0, img.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);
            String id1 = cursor.getString(idIndex);

            Articles article = new Articles(title, categ, bmimg, text, pin, id1);
            articleList.add(article);
        }
        return articleList;
    }

    //When user accesses 'Home' from bottom nav bar
    public List<Articles> getAllArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Read from db
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " inner join " + USERS_TABLE + " on " + ARTICLES_TABLE + "." + ARTICLES_COLUMN_ID + " = " + USERS_TABLE + "." + USERS_TABLE_ID + " order by " + ARTICLES_COLUMN_TITLE, null);

        //Adding all records from above query
        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_img);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);
            int idIndex = cursor.getColumnIndex(USERS_TABLE_NAME);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] img = cursor.getBlob(imgIndex);
            Bitmap bmimg = BitmapFactory.decodeByteArray(img, 0, img.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);
            String id = cursor.getString(idIndex);

            Articles article = new Articles(title, categ, bmimg, text, pin, id);
            articleList.add(article);
        }
        return articleList;
    }

    //When user accesses 'Politics' from bottom nav bar
    public List<Articles> getPoliticsArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Read from db
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " inner join " + USERS_TABLE + " on " + ARTICLES_TABLE + "." + ARTICLES_COLUMN_ID + " = " + USERS_TABLE + "." + USERS_TABLE_ID + " where category=?" + " order by " + ARTICLES_COLUMN_TITLE, new String[] {"Politics"});

        //Adding all records from above query
        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_img);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);
            int idIndex = cursor.getColumnIndex(USERS_TABLE_NAME);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] img = cursor.getBlob(imgIndex);
            Bitmap bmimg = BitmapFactory.decodeByteArray(img, 0 ,img.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);
            String id = cursor.getString(idIndex);

            Articles article = new Articles(title, categ, bmimg, text, pin, id);
            articleList.add(article);
        }
        return articleList;
    }

    //When user accesses 'Social' from bottom nav bar
    public List<Articles> getSocialArticles()
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Read from db
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " inner join " + USERS_TABLE + " on " + ARTICLES_TABLE + "." + ARTICLES_COLUMN_ID + " = " + USERS_TABLE + "." + USERS_TABLE_ID + " where category=?" + " order by " + ARTICLES_COLUMN_TITLE, new String[] {"Social"});

        //Adding all records from above query
        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_img);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);
            int idIndex = cursor.getColumnIndex(USERS_TABLE_NAME);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] img = cursor.getBlob(imgIndex);
            Bitmap bmimg = BitmapFactory.decodeByteArray(img, 0 ,img.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);
            String id = cursor.getString(idIndex);

            Articles article = new Articles(title, categ, bmimg, text, pin, id);
            articleList.add(article);
        }
        return articleList;
    }

    //When user accesses 'Opinion' from bottom nav bar
    public List<Articles> getOpinionArticles()
    {

        SQLiteDatabase db = this.getReadableDatabase(); //Read from db
        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " inner join " + USERS_TABLE + " on " + ARTICLES_TABLE + "." + ARTICLES_COLUMN_ID + " = " + USERS_TABLE + "." + USERS_TABLE_ID + " where category=?" + " order by " + ARTICLES_COLUMN_TITLE, new String[] {"Opinion"});

        //Adding all records from above query
        while(cursor.moveToNext())
        {
            int titleIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TITLE);
            int imgIndex = cursor.getColumnIndex(ARTICLES_COLUMN_img);
            int textIndex = cursor.getColumnIndex(ARTICLES_COLUMN_TEXT);
            int categIndex = cursor.getColumnIndex(ARTICLES_COLUMN_CATEG);
            int pinIndex = cursor.getColumnIndex(ARTICLES_COLUMN_PIN);
            int idIndex = cursor.getColumnIndex(USERS_TABLE_NAME);

            String title = cursor.getString(titleIndex);
            String categ = cursor.getString(categIndex);
            byte[] img = cursor.getBlob(imgIndex);
            Bitmap bmimg = BitmapFactory.decodeByteArray(img, 0, img.length);
            String text = cursor.getString(textIndex);
            int pin = cursor.getInt(pinIndex);
            String id = cursor.getString(idIndex);

            Articles article = new Articles(title, categ, bmimg, text, pin, id);
            articleList.add(article);
        }
        return articleList;
    }

    //When adding a user through sign up
    public Users insertUser(Users user)
    {
        try
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USERS_TABLE_EMAIL, user.getEmail());
            contentValues.put(USERS_TABLE_NAME, user.getName());
            contentValues.put(USERS_TABLE_PASSWORD, user.getPassword());
            contentValues.put(USERS_TABLE_BIO, user.getBio());
            contentValues.put(USERS_TABLE_IMG, user.getImage());

            db.insert(USERS_TABLE, null, contentValues);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    //When querying user's information through id
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
                user.setImage(cursor.getBlob(5));
            }
        } catch (Exception e){
            user = null;
        }
        return user;
    }

    //Querying user's details by name
    public Users findUserByName(String name)
    {
        Users user = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from users where name = ?", new String[] { name });
            if(cursor.moveToFirst()) {
                user = new Users();
                user.setId((cursor.getInt(0)));
                user.setEmail(cursor.getString(1));
                user.setName(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setBio(cursor.getString(4));
                user.setImage(cursor.getBlob(5));
            }
        } catch (Exception e){
            user = null;
        }
        return user;
    }

    //When updating user's information
    public Boolean updateUser(Users user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_TABLE_EMAIL, user.getEmail());
        contentValues.put(USERS_TABLE_NAME, user.getName());
        contentValues.put(USERS_TABLE_PASSWORD, user.getPassword());
        contentValues.put(USERS_TABLE_BIO, user.getBio());
        contentValues.put(USERS_TABLE_IMG, user.getImage());

        long userDetails = db.update(USERS_TABLE, contentValues, "id" + " = ?", new String[] { String.valueOf(user.getId()) });
        if(userDetails == -1)
            return false;
        else
            return true;
    }

    //Check Login information from users table information
    public Users checkLogin(String email, String password)
    {
        Users user = null;
        try{
            SQLiteDatabase db = getReadableDatabase(); //Read from db
            Cursor cursor = db.rawQuery("select * from users where email = ? and password = ?", new String[] { email, password });
            if(cursor.moveToFirst()) {
                user = new Users();
                user.setId((cursor.getInt(0)));
                user.setEmail(cursor.getString(1));
                user.setName(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setBio(cursor.getString(4));
                user.setImage(cursor.getBlob(5));
            }
        } catch (Exception e){
            user = null;
        }
        return user;
    }

    //Check Email information from users table information
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
                user.setImage(cursor.getBlob(5));
            }
        } catch (Exception e){
        }
        return user;
    }

    //Checks if an article with the same title already exists
    public Boolean checkTitle(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from articles where title = ?", new String[] {title});
        if(cursor.getCount()>0) //if there are same titles
            return true;
        else
            return false;
    }
}