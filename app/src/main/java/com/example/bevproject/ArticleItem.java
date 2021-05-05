package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import javax.xml.transform.TransformerConfigurationException;

public class ArticleItem extends AppCompatActivity
{
    private static final String TAG = "ArticleItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        TextView title = findViewById(R.id.itemTitle);

        String articleTitle = "Title was not submitted.";
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            articleTitle = extras.getString("articleTitle");
        }
        title.setText(articleTitle);
    }
/*
    private void incomingIntent()
    {
        if(getIntent().hasExtra("itemTitle") && getIntent().hasExtra("itemImage"))
        {
            String title = getIntent().getStringExtra("itemTitle");
            String img = getIntent().getStringExtra("itemImage");

            setArticle(title);
        }
    }

    private void setArticle(String title)
    {
        TextView artTitle = findViewById(R.id.itemTitle);
        artTitle.setText(title);

        ImageView artImage = findViewById(R.id.itemImage);
        //SET IMAGE
    }*/
}