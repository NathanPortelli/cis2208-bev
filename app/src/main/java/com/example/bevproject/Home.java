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
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
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
        articleList = db.getAllArticles();
        rvArticles = findViewById(R.id.rvArticles);
        rvArticles.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        setOnClickListener();
        rvArticles.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(this, articleList, rvArticles, listener);
        rvArticles.setAdapter(articleAdapter);

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), SubmitArticle.class));
            }
        });
    }

    private void setOnClickListener()
    {
        listener = new ArticleAdapter.ArticleViewClickListener() {
            @Override
            public void onClick(View v, int pos)
            {
                Bitmap bmp = articleList.get(pos).getImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);

                Intent intent = new Intent(getApplicationContext(), ArticleItem.class);
                intent.putExtra("articleTitle", articleList.get(pos).getTitle());
                intent.putExtra("articleCateg", articleList.get(pos).getCateg());
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
        recreate();
    }

    public void ClickProfile(View view)
    {
        //REDIRECT TO USER PROFILE
        //redirectActivity(this, );
    }

    public void ClickSavedArticles(View view) {
        //REDIRECT TO SAVED ARTICLES
        closeDrawer(drawerLayout);
        redirectActivity(this, PinnedArticles.class);
    }

    public void ClickPolitics(View view) {
        //REDIRECT TO POLITICS CATEGORY
        closeDrawer(drawerLayout);
        redirectActivity(this, Politics.class);
    }

    public void ClickSocial(View view) {
        //REDIRECT TO SOCIAL CATEGORY
        closeDrawer(drawerLayout);
        redirectActivity(this, Social.class);
    }

    public void ClickOpinion(View view) {
        //REDIRECT TO OPINION CATEGORY
        closeDrawer(drawerLayout);
        redirectActivity(this, Opinion.class);
    }

    public void ClickArticleCreate(View view)
    {
        //REDIRECT TO SUBMIT AN ARTICLE
        closeDrawer(drawerLayout);
        redirectActivity(this, SubmitArticle.class);
    }

    public static void redirectActivity(Activity activity, Class actClass)
    {
        Intent intent = new Intent(activity, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void ButtonSave(View view)
    {
        int pos = rvArticles.getChildLayoutPosition(view);
        String pinTitle = articleList.get(pos).getTitle();

        Boolean result = db.pinArticle(pinTitle);
        if(result == true)
        {
            Toast.makeText(Home.this, "Article has been pinned!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Home.this, "Article has already been pinned.", Toast.LENGTH_SHORT).show();
        }
    }
}