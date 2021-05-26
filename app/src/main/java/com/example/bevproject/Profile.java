package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        TextView bio = findViewById(R.id.itemProfileBio);
        Button viewMyArticles = findViewById(R.id.btnMyArticles);
        ImageView image = findViewById(R.id.itemProfileImage);

        user = (Users) getIntent().getSerializableExtra("user");

        name.setText(user.getName());
        bio.setText(user.getBio());

        byte[] pic = user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length); //Converting byte to bitmap
        Bitmap btm = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()),(int)(bitmap.getWidth()),true);
        image.setImageBitmap(btm);

        viewMyArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MyArticles.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    public void ClickProfile(View view)
    {
        recreate();
    }

    public void ClickMenu(View view)
    {
        Home.openDrawer(drawerLayout);
    }

    //When user wants to change their details
    public void ChangeDetails(View view)
    {
        Intent intent = new Intent(Profile.this, UserDetails.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    //Button to log out of the account
    public void ClickLogout(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //When tapping the back button
    public void ClickToHome(View view)
    {
        user = db.findUser(user.getId());
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("userFromAccount", user);
        startActivity(intent);
        finish();
    }
}