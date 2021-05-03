package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubmitArticle extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ImageButton imgUpload;
    EditText title, text;
    Button btnSubmit;
    //Database reference
    DBHelper db;
    //Image information
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    byte[] imageData;
    //Validation
    Pattern patt;
    Matcher match;

    Spinner spinner;
    String categoryChoice;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        drawerLayout = findViewById(R.id.homeLayout);
        spinner = (Spinner) findViewById(R.id.spArticleCategory);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        title = (EditText) findViewById(R.id.edArticleTitle);
        text = (EditText) findViewById(R.id.edArticleText);
        db = new DBHelper(this);

        btnSubmit = (Button) findViewById(R.id.btnSignUpRegister);

        Intent intent = getIntent();
        imgUpload = (ImageButton) findViewById(R.id.articleImgUpload);

        imgUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Article Thumbnail"), PICK_IMAGE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String articletitle = title.getText().toString();
                String articletext = text.getText().toString();
                //String articleCategory = spinner.toString();

                if(articletitle.equals("") || articletext.equals(""))
                    Toast.makeText(SubmitArticle.this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
                else
                {
                    Boolean articleCheck = db.checkTitle(articletitle); //ADD CHECK FOR TITLE
                    if(articleCheck == false)
                    {
                        Boolean result = db.insertArticle(articletitle, articletext, categoryChoice, imageData);
                        if(result == true)
                        {
                            openHomePage();
                            Toast.makeText(SubmitArticle.this, "Successfully Posted!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SubmitArticle.this, "Submission has failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(SubmitArticle.this, "An article with the same title already exists.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Home.class);
        SubmitArticle.this.startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryChoice = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }

    public void ClickMenu(View view)
    {
        Home.openDrawer(drawerLayout);
    }
    public void ClickBack(View view)
    {
        Home.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view)
    {
        Home.redirectActivity(this, Home.class);
    }
    public void ClickProfile(View view)
    {
        //REDIRECT TO USER PROFILE
        //redirectActivity(this, );
    }

    public void ClickArticleCreate(View view)
    {
        recreate();
    }

    public void ClickLogout(View view)
    {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgUpload.setImageBitmap(bitmap);
                imageData = getByteArray(bitmap);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static byte[] getByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}