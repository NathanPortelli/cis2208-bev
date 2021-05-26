package com.example.bevproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bevproject.databinding.ActivityAuthorPageBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AuthorPage extends AppCompatActivity {

    DBHelper db = new DBHelper(this);
    RecyclerView rvArticles;
    Users user;
    List<Articles> articleList = new ArrayList<>();
    ArticleAdapter articleAdapter;
    RecyclerView.LayoutManager layoutManager;
    private ArticleAdapter.ArticleViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_author_page);

        rvArticles = findViewById(R.id.rvAuthorArticles);
        rvArticles.setHasFixedSize(true); //ensures change of size in recyclerview is constant
        layoutManager = new LinearLayoutManager(this);

        ImageView authorImage = findViewById(R.id.authorImage);
        TextView authorName = findViewById(R.id.authorName);
        TextView authorBio = findViewById(R.id.authorBio);

        Bundle b = getIntent().getExtras();
        String name = b.getString("authorName");

        user = db.findUserByName(name); //getting all user's information based on name from intent
        authorName.setText(name);
        authorBio.setText(user.getBio());
        articleList = db.getAllArticlesById(user.getId()); //gets all articles according to the user's id
        setOnClickListener();
        rvArticles.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(this, articleList, rvArticles, listener);
        rvArticles.setAdapter(articleAdapter);

        byte[] pic = user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length); //converting byte into bitmap
        Bitmap btm = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()),(int)(bitmap.getWidth()),true);
        authorImage.setImageBitmap(btm);
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

    public void ClickToHome(View view)
    {
        finish();
    }
}