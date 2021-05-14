package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

<<<<<<< Updated upstream
=======
import java.io.ByteArrayOutputStream;
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.List;

public class Politics extends AppCompatActivity {

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
        setContentView(R.layout.activity_politics);
        drawerLayout = findViewById(R.id.politicsLayout);
        db = new DBHelper(this);
        rvArticles = findViewById(R.id.rvArticles);
        articleList = db.getPoliticsArticles();
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
                intent.putExtra("articleCateg", articleList.get(pos).getCateg());
                intent.putExtra("articleText", articleList.get(pos).getContent());
                intent.putExtra("articleImage", baos.toByteArray());
                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(new Intent(getApplicationContext(), SubmitArticle.class));
            }
        });
<<<<<<< Updated upstream
=======
    }

    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
>>>>>>> Stashed changes
    }

    public void ClickMenu(View view)
    {
        Home.openDrawer(drawerLayout);
    }
    public void ClickBack(View view)
    {
        Home.closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view)
    {
        //REDIRECT TO USER PROFILE
        Home.redirectActivity(this, Profile.class);
    }

    public void ClickHome(View view) {
        //REDIRECT TO HOME PAGE
        finish();
        Home.redirectActivity(this, Home.class);
    }

    public void ClickArticleCreate(View view)
    {
        //REDIRECT TO SUBMIT AN ARTICLE
        finish();
        Home.redirectActivity(this, SubmitArticle.class);
    }

    public void ClickSavedArticles(View view) {
        //REDIRECT TO SAVED ARTICLES
        finish();
        Home.redirectActivity(this, PinnedArticles.class);
    }

    public void ClickPolitics(View view) { recreate(); }

    public void ClickSocial(View view) {
        //REDIRECT TO SOCIAL CATEGORY
        finish();
        Home.redirectActivity(this, Social.class);
    }

    public void ClickOpinion(View view) {
        //REDIRECT TO OPINION CATEGORY
        finish();
        Home.redirectActivity(this, Opinion.class);
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