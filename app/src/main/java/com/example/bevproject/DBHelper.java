package com.example.bevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.IOException;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(@Nullable Context context)
    {
        super(context, "BEVDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create Table users(email Text primary key, name Text, password Text, bio Text, img Blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop Table if exists users");
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
}