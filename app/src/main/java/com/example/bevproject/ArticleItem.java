package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleItem extends AppCompatActivity
{
    //Database reference
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        db = new DBHelper(this);

        TextView title = findViewById(R.id.itemTitle);
        TextView category = findViewById(R.id.itemCateg);
        ImageView image = findViewById(R.id.itemImage);
        TextView text = findViewById(R.id.itemContent);

        //Initialisation of text components
        String articleTitle = "Title was not submitted.";
        String articleCateg = "Category was not submitted.";
        String articleText = "Text was not submitted.";

        Bundle extras = getIntent().getExtras();
        if(extras != null) //In case an issue happened with submission and one of the fields was left null
        {
            articleTitle = extras.getString("articleTitle");
            articleText = extras.getString("articleText");
            articleCateg = extras.getString("articleCateg");
        }
        //Setting image through bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("articleImage"), 0, getIntent().getByteArrayExtra("articleImage").length);
        image.setImageBitmap(bitmap);
        //Setting text components from intent
        category.setText(articleCateg);
        title.setText(articleTitle);
        text.setText(articleText);
    }

    //Used to pin article
    public void ButtonSave(View view)
    {
        TextView title = findViewById(R.id.itemTitle);
        String saveTitle = title.getText().toString();

        Boolean result = db.pinArticle(saveTitle); //Checks if article is currently pinned/unpinned and sets as opposite
        if(result == true) //Article is being pinned
        {
            Toast.makeText(ArticleItem.this, "Article has been pinned!", Toast.LENGTH_SHORT).show();
        }
        else //Article is being unpinned
        {
            Toast.makeText(ArticleItem.this, "Article has already been pinned.", Toast.LENGTH_SHORT).show();
        }
    }

    //Used to copy article text to clipboard
    public void ButtonCopy(View view)
    {
        TextView content = findViewById(R.id.itemContent);
        String contentString = content.getText().toString();

        //Setting clipboard
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Article", contentString); //Setting content of clipboard
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(ArticleItem.this, "Article copied to clipboard.", Toast.LENGTH_SHORT).show();
    }

    public void ClickToHome(View view) //Closes article item activity and returns to Article List
    {
        finish();
    }
<<<<<<< Updated upstream


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
=======
>>>>>>> Stashed changes
}