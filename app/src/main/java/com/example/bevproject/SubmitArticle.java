package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SubmitArticle extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ImageButton imgUpload;
    TextView headertitle, headertext, headerimage;
    EditText title, text;
    Button btnSubmit;
    //Database reference
    DBHelper db;
    //Image information
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    byte[] imageData;
    //Category choice
    Spinner spinner;
    String categoryChoice;
    //Sidebar menu
    DrawerLayout drawerLayout;
    //Current user
    Users currentUser;

    //On creation of activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        drawerLayout = findViewById(R.id.homeLayout);
        spinner = findViewById(R.id.spArticleCategory); //For use by list of all categories
        //Initialisation of adapted for categories spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        //Dropdown for full list of all available items
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);//Set selected item

        //Request permission for access to external storage files needed for upload of image
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //Titles on top of input
        headertitle = findViewById(R.id.txArticleTitle);
        headertext = findViewById(R.id.txArticleText);
        headerimage = findViewById(R.id.articleImage);

        //Actual input of details
        title = findViewById(R.id.edArticleTitle);
        text = findViewById(R.id.edArticleText);
        db = new DBHelper(this);

        //Set current user
        currentUser = (Users)getIntent().getSerializableExtra("userId");

        btnSubmit = findViewById(R.id.btnSignUpRegister);

        //Input for upload of article image
        imgUpload = findViewById(R.id.articleImgUpload);
        imgUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gallery = new Intent();
                gallery.setType("image/*"); //sets gallery information to images only
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Article Thumbnail"), PICK_IMAGE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Setting user details to as inputted by user
                String articletitle = title.getText().toString();
                String articletext = text.getText().toString();
                //Resetting colors of text
                headertitle.setTextColor(0xFFFFFFFF);
                headertext.setTextColor(0xFFFFFFFF);
                headerimage.setTextColor(0xFFFFFFFF);

                if(articletitle.equals("")) //If no title input was set
                {
                    //Change text colors to red as 'invalid'
                    headertitle.setTextColor(0xFFAE0700);
                    Toast.makeText(SubmitArticle.this, "Please enter a title.", Toast.LENGTH_SHORT).show();
                }
                else if(articletext.equals("")) //If no article text input was set
                {
                    //Change text colors to red as 'invalid'
                    headertext.setTextColor(0xFFAE0700);
                    Toast.makeText(SubmitArticle.this, "Please enter your article text.", Toast.LENGTH_SHORT).show();
                }
                else if(imageData == null) //If no image was set
                {
                    //Change text colors to red as 'invalid'
                    headerimage.setTextColor(0xFFAE0700);
                    Toast.makeText(SubmitArticle.this, "Please upload an image.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean titleCheck = db.checkTitle(articletitle); //Checks if title already exists in DB
                    if(titleCheck == true) {
                        //Change text colors to red as 'invalid'
                        headertitle.setTextColor(0xFFAE0700);
                        Toast.makeText(SubmitArticle.this, "An article with the same title already exists.", Toast.LENGTH_LONG).show();
                    }
                    //If title is unique
                    else
                    {
                        Boolean result = db.insertArticle(articletitle, articletext, categoryChoice, imageData, currentUser.getId()); //Insert article into DB
                        if(result == true) //if successful
                        {
                            openMyArticles(); //Return to My Articles Activity (method below)
                            Toast.makeText(SubmitArticle.this, "Successfully Posted!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else //if failed
                        {
                            Toast.makeText(SubmitArticle.this, "Submission has failed.\nPlease try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    //When submission is successful
    public void openMyArticles() {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("userFromLogin", currentUser);
        startActivity(intent);
    }

    //Sets selected category to the one clicked on by user
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoryChoice = parent.getItemAtPosition(position).toString();
    }

    //Inserted as standard, a category is always set, 'Politics' by default as it is currently the first on list
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }

    //Sidemenu buttons
    public void ClickMenu(View view) //On clicking burger menu icon
    {
        Home.openDrawer(drawerLayout);
    }
    public void ClickBack(View view) //On clicking burger menu icon when menu is open
    {
        Home.closeDrawer(drawerLayout);
    }

    //On clicking 'Articles' to return to Home Activity
    public void ClickHome(View view)
    {
        Home.closeDrawer(drawerLayout);
        Home.redirectActivity(this, Home.class);
    }

    public void ClickProfile(View view) {
        Intent intent = new Intent(SubmitArticle.this, Profile.class);
        intent.putExtra("user", currentUser);
        startActivity(intent);
    }

    //On clicking 'Submit Article', refreshes Activity
    public void ClickArticleCreate(View view)
    {
        recreate();
    }

    //For image upload
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
                //Scales down image quality due to the maximum size that they will be shown in
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.3), (int)(bitmap.getHeight()*0.3), true);
                imgUpload.setImageBitmap(resize);
                imageData = getByteArray(resize); //check method below
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //sets image output to byte array form
    public static byte[] getByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}