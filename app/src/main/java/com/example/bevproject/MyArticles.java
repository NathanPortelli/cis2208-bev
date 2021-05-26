package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyArticles extends AppCompatActivity {

    DrawerLayout drawerLayout;
    //Database reference
    DBHelper db;
    Users user;
    RecyclerView rvArticles;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Articles> articleList = new ArrayList<>();
    private ArticleAdapter.ArticleViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_articles);

        drawerLayout = findViewById(R.id.myArticlesLayout);
        user = (Users) getIntent().getSerializableExtra("user");

        db = new DBHelper(this);
        articleList = db.getAllArticlesById(user.getId()); //gets all articles based on the current user's id
        rvArticles = findViewById(R.id.rvAuthorArticles);
        rvArticles.setHasFixedSize(true); //ensures change of size in recyclerview is constant
        layoutManager = new LinearLayoutManager(this);

        setOnClickListener();
        rvArticles.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(this, articleList, rvArticles, listener);
        rvArticles.setAdapter(articleAdapter);
    }

    //Once user clicks on article
    private void setOnClickListener()
    {
        listener = new ArticleAdapter.ArticleViewClickListener() {
            @Override
            public void onClick(View v, int pos)
            {
                //Sends data related to article to article activity
                Bitmap bmp = articleList.get(pos).getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);

                Intent intent = new Intent(getApplicationContext(), ArticleItem.class);
                intent.putExtra("articleTitle", articleList.get(pos).getTitle());
                intent.putExtra("articleCateg", articleList.get(pos).getCateg());
                intent.putExtra("articleText", articleList.get(pos).getContent());
                intent.putExtra("articleAuthor", articleList.get(pos).getAuthor());
                intent.putExtra("articleImage", baos.toByteArray());
                startActivity(intent);
            }
        };
    }

    //When tapping the back button
    public void ClickToHome(View view)
    {
        finish();
    }
}