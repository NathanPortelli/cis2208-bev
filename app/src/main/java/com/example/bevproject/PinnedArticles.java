package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class PinnedArticles extends AppCompatActivity
{
    DrawerLayout drawerLayout;

    DBHelper db;
    RecyclerView rvArticles;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Articles> articleList = new ArrayList<>();

    private ArticleAdapter.ArticleViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.homeLayout);

        db = new DBHelper(this);
        articleList = db.getAllPinnedArticles();
        rvArticles = findViewById(R.id.rvArticles);
        rvArticles.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        setOnClickListener();
        rvArticles.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(this, articleList, rvArticles, listener);
        rvArticles.setAdapter(articleAdapter);
    }

    private void setOnClickListener()
    {
        listener = new ArticleAdapter.ArticleViewClickListener() {
            @Override
            public void onClick(View v, int pos)
            {
                Bitmap bmp = articleList.get(pos).getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 30, baos);

                Intent intent = new Intent(getApplicationContext(), ArticleItem.class);
                intent.putExtra("articleTitle", articleList.get(pos).getTitle());
                intent.putExtra("articleText", articleList.get(pos).getContent());
                intent.putExtra("articleImage", baos.toByteArray());
                startActivity(intent);
            }
        };
    }

    public void ClickMenu(View view)
    {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }

    public void ClickBack(View view)
    {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view)
    {
        redirectActivity(this, Home.class);
    }

    public void ClickProfile(View view)
    {
        //REDIRECT TO USER PROFILE
        //redirectActivity(this, );
    }

    public void ClickSavedArticles(View view) {
        recreate();
    }

    public void ClickPolitics(View view) {
        //REDIRECT TO POLITICS CATEGORY
        redirectActivity(this, Politics.class);
    }

    public void ClickSocial(View view) {
        //REDIRECT TO SOCIAL CATEGORY
        redirectActivity(this, Social.class);
    }

    public void ClickOpinion(View view) {
        //REDIRECT TO OPINION CATEGORY
        redirectActivity(this, Opinion.class);
    }

    public void ClickArticleCreate(View view)
    {
        //REDIRECT TO SUBMIT AN ARTICLE
        redirectActivity(this, SubmitArticle.class);
    }

    public void ClickLogout(View view)
    {
        finish();
    }

    public static void redirectActivity(Activity activity, Class actClass)
    {
        Intent intent = new Intent(activity, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}