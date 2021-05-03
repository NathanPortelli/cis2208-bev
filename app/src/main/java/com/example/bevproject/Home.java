package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Home extends AppCompatActivity
{
    DrawerLayout drawerLayout;

    DBHelper db;
    RecyclerView rvArticles;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Articles> articleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.homeLayout);

        db = new DBHelper(this);
        articleList = db.getAllArticles();
        rvArticles = findViewById(R.id.rvArticles);
        rvArticles.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvArticles.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(this, articleList, rvArticles);
        rvArticles.setAdapter(articleAdapter);

        //db.insertArticle("Test Article");
    }

    public void ClickMenu(View view)
    {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout)
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

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
        recreate();
    }

    public void ClickProfile(View view)
    {
        //REDIRECT TO USER PROFILE
        //redirectActivity(this, );
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

    public void addArticle(View view)
    {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.icProfile: return true;
            case R.id.setSettings: return true;
            case R.id.setClose:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}