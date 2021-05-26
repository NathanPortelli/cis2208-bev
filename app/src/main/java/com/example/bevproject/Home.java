package com.example.bevproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
{
    DrawerLayout drawerLayout;
    //Database reference
    DBHelper db;
    Users user;
    RecyclerView rvArticles;
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Articles> articleList = new ArrayList<>();

    private ArticleAdapter.ArticleViewClickListener listener;

    //On creation of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.homeLayout); //for sidebar menu

        db = new DBHelper(this);
        articleList = db.getAllArticles();
        rvArticles = findViewById(R.id.rvArticles);
        rvArticles.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        user = (Users) getIntent().getSerializableExtra("userFromAccount");
        if (user == null) // Important check to see which user information is being passed depending on the user's activity
            user = (Users) getIntent().getSerializableExtra("userFromLogin");

        setOnClickListener();
        rvArticles.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(this, articleList, rvArticles, listener);
        rvArticles.setAdapter(articleAdapter);

        //For Bottom Navigator Tab
        BottomNavigationView btnNav = findViewById(R.id.bottomnavview);
        btnNav.setOnNavigationItemSelectedListener(bottomNavListener);

        //Floating Action Button accessing 'Submit an article' activity on click
        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), SubmitArticle.class);
                intent.putExtra("userId", user);
                startActivity(intent);
            }
        });
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

    //When user selects a category from bottom navigation tabs
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment = null;
            switch(item.getItemId())
            {
                case R.id.homeItem:
                    selectFragment = new HomeFragment();
                    break;
                case R.id.politicsItem:
                    selectFragment = new PoliticsFragment();
                    break;
                case R.id.socialItem:
                    selectFragment = new SocialFragment();
                    break;
                case R.id.opinionItem:
                    selectFragment = new OpinionFragment();
                    break;
                case R.id.pinItem:
                    selectFragment = new PinnedFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, selectFragment).commit(); //Sets fragment in Home to selected fragment
            return true;
        }
    };

    //When tapping the profile icon from the top right
    public void ClickProfile(View view) {
        Intent intent = new Intent(Home.this, Profile.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    //Links to activities from sidebar menu
    public void ClickMenu(View view) //Opening of sidebar when clicking on Burger Menu icon
    {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }

    public void ClickBack(View view) //Closing of sidebar when clicking on Burger Menu icon
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

    public void ClickHome(View view) //Refreshing of activity when clicking on 'Articles'
    {
        recreate();
    }

    //Redirect user to 'Submit an Article' Activity
    public void ClickArticleCreate(View view)
    {
        closeDrawer(drawerLayout);
        Intent intent = new Intent(getApplicationContext(), SubmitArticle.class);
        intent.putExtra("userId", user);
        startActivity(intent);
    }

    public static void redirectActivity(Activity activity, Class actClass) //Used to redirect user to another activity
    {
        Intent intent = new Intent(activity, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    //Used by 'Pin Article' button
    public void ButtonSave(View view)
    {
        int pos = rvArticles.getChildLayoutPosition(view);
        String pinTitle = articleList.get(pos).getTitle();

        Boolean result = db.pinArticle(pinTitle); //Checks if article is currently pinned/unpinned and sets as opposite
        if(result == true) //Article is being pinned
        {
            Toast.makeText(Home.this, "Article has been pinned!", Toast.LENGTH_SHORT).show();
        }
        else //Article is being unpinned
        {
            Toast.makeText(Home.this, "Article has already been pinned.", Toast.LENGTH_SHORT).show();
        }
    }
}