package com.example.bevproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
                ImageView articleImage = imgUpload;
                //String articleCategory = spinner.toString();
                
                if(articletitle.equals("") || articletext.equals(""))
                    Toast.makeText(SubmitArticle.this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
                else if(imageData == null)
                    Toast.makeText(SubmitArticle.this, "Please upload an image.", Toast.LENGTH_SHORT).show();
                else
                {
                    Boolean titleCheck = db.checkTitle(articletitle);
                    if(titleCheck == true)
                        Toast.makeText(SubmitArticle.this, "An article with the same title already exists.", Toast.LENGTH_LONG).show();
                    else
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
                }
            }
        });
    }

    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
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
        closeDrawer(drawerLayout);
        Home.redirectActivity(this, Home.class);
    }

    public void ClickProfile(View view)
    {
        //REDIRECT TO USER PROFILE
        Home.redirectActivity(this, Profile.class);
    }

    public void ClickArticleCreate(View view)
    {
        recreate();
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
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.3), (int)(bitmap.getHeight()*0.3), true);
                imgUpload.setImageBitmap(resize);
                imageData = getByteArray(resize);
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

    private boolean hasImage(@NonNull ImageView view)
    {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);
        if (hasImage && (drawable instanceof BitmapDrawable))
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        return hasImage;
    }
}