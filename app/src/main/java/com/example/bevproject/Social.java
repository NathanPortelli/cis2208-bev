package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Social extends AppCompatActivity {

    DBHelper db;
    RecyclerView rvArticles;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Articles> articleList = new ArrayList<>();
    DrawerLayout drawerLayout;
    private ArticleAdapter.ArticleViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        drawerLayout = findViewById(R.id.socialLayout);
        db = new DBHelper(this);
        rvArticles = findViewById(R.id.rvArticles);
        articleList = db.getSocialArticles();
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
            public void onClick(View v, int pos) {
                Intent intent = new Intent(getApplicationContext(), ArticleItem.class);
                intent.putExtra("articleTitle", articleList.get(pos).getTitle());
                startActivity(intent);
            }
        };
    }

    public static void redirectActivity(Activity activity, Class actClass)
    {
        Intent intent = new Intent(activity, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void ClickMenu(View view)
    {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout)
    { drawerLayout.openDrawer(GravityCompat.START); }

    public void ClickBack(View view)
    {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        { drawerLayout.closeDrawer(GravityCompat.START); }
    }

    public void ClickHome(View view) {
        //REDIRECT TO HOME PAGE
        redirectActivity(this, Home.class);
    }

    public void ClickArticleCreate(View view)
    {
        //REDIRECT TO SUBMIT AN ARTICLE
        redirectActivity(this, SubmitArticle.class);
    }

    public void ClickLogout(View view) { finish(); }

    public void ClickPolitics(View view) {
        //REDIRECT TO POLITICS CATEGORY
        redirectActivity(this, Politics.class);
    }

    public void ClickSocial(View view) { recreate(); }

    public void ClickOpinion(View view){
        //REDIRECT TO OPINION CATEGORY
        redirectActivity(this, Opinion.class);
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