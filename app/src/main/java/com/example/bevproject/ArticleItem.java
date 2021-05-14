package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.transform.TransformerConfigurationException;

public class ArticleItem extends AppCompatActivity
{
    DBHelper db;
    RecyclerView rvArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        db = new DBHelper(this);

        TextView title = findViewById(R.id.itemTitle);
        TextView category = findViewById(R.id.itemCateg);
        ImageView image = findViewById(R.id.itemImage);
        TextView text = findViewById(R.id.itemContent);

        String articleTitle = "Title was not submitted.";
        String articleCateg = "Category was not submitted.";
        String articleText = "Text was not submitted.";

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            articleTitle = extras.getString("articleTitle");
            articleText = extras.getString("articleText");
            articleCateg = extras.getString("articleCateg");
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("articleImage"), 0, getIntent().getByteArrayExtra("articleImage").length);
        image.setImageBitmap(bitmap);

        category.setText(articleCateg);
        title.setText(articleTitle);
        text.setText(articleText);
    }

    public void ButtonSave(View view)
    {
        TextView title = findViewById(R.id.itemTitle);
        String saveTitle = title.getText().toString();

        Boolean result = db.pinArticle(saveTitle);
        if(result == true)
        {
            Toast.makeText(ArticleItem.this, "Article has been pinned!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ArticleItem.this, "Article has been unpinned.", Toast.LENGTH_SHORT).show();
        }
    }

    public void ButtonCopy(View view)
    {
        TextView content = findViewById(R.id.itemContent);
        String contentString = content.getText().toString();

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Article", contentString);
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(ArticleItem.this, "Article copied to clipboard.", Toast.LENGTH_SHORT).show();
    }

    public void ClickToHome(View view)
    {
        finish();
    }

    public void ClickProfile(View view) {
        Home.redirectActivity(this, Profile.class);
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