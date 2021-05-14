package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity
{
    DrawerLayout drawerLayout;
    DBHelper db;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DBHelper(this);

        TextView name = findViewById(R.id.itemProfileName);
        ImageView image = findViewById(R.id.itemProfileImage);

        /*DBHelper db = new DBHelper(getApplicationContext());
        Users currentUser = db.findUser(user.getId());
        name.setText(currentUser.getName());*/
    }

    public static void redirectActivity(Activity activity, Class actClass)
    {
        Intent intent = new Intent(activity, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void ClickProfile(View view)
    {
        recreate();
    }

    public void ClickMenu(View view)
    {
        Home.openDrawer(drawerLayout);
    }

    public void ViewMyArticles(View view)
    {
       // redirectActivity(this, UserArticles.class);
    }

    public void ChangeDetails(View view)
    {
        redirectActivity(this, UserDetails.class);
    }

    public void ClickLogout(View view)
    {
        redirectActivity(this, MainActivity.class);
    }
}