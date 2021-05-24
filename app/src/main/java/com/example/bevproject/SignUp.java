package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity
{
<<<<<<< Updated upstream
=======
    TextView headername, headeremail, headerpass, headerimage;
>>>>>>> Stashed changes
    ImageButton imgUpload;
    EditText name, email, pass, bio;
    Button btnSignUpNext;
    //Database reference
    DBHelper db;
    //Image information
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    byte[] imageData;
    //For validation of input
    Pattern patt;
    Matcher match;

    //On creation of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Request permission for access to external storage files needed for upload of image
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //Titles on top of input
        headername = findViewById(R.id.txName);
        headeremail = findViewById(R.id.txEmail);
        headerpass = findViewById(R.id.txPassword);
        headerimage = findViewById(R.id.txImage);

        //Actual input of details
        name = findViewById(R.id.edName);
        email = findViewById(R.id.edEmail);
        pass = findViewById(R.id.edPassword);
        bio = findViewById(R.id.edBio);
        db = new DBHelper(this);

        btnSignUpNext = findViewById(R.id.btnSignUpRegister);

<<<<<<< Updated upstream
        Intent intent = getIntent();
        imgUpload = (ImageButton) findViewById(R.id.imgUpload);
=======
        //Input for upload of image
        imgUpload = findViewById(R.id.imgUpload);
>>>>>>> Stashed changes

        //When user clicks on ImageButton to add their profile image
        imgUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gallery = new Intent();
                gallery.setType("image/*"); //sets gallery information to images only
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), PICK_IMAGE);
            }
        });

        //When user click on 'Sign Up' button
        btnSignUpNext.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
<<<<<<< Updated upstream
                String username = name.getText().toString();
                String useremail = email.getText().toString().toLowerCase();
                String userpass = pass.getText().toString();
                String userbio = bio.getText().toString();

                if(username.equals("") || useremail.equals("") || userpass.equals("") || userbio.equals(""))
                    Toast.makeText(SignUp.this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
                else if(!validateName(username))
                    Toast.makeText(SignUp.this, "Please enter a valid name (No numbers).", Toast.LENGTH_SHORT).show();
                else if(!validateEmail(useremail))
                    Toast.makeText(SignUp.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                else if(userpass.length()<8 && !validatePass(userpass))
=======
                //Setting user details to as inputted by user
                Users user = new Users();
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString().toLowerCase()); //email set to lowercase regardless of user method of input
                user.setPassword(pass.getText().toString());
                user.setBio(bio.getText().toString());
                user.setImg(imageData);
                //Resetting colors of text
                headername.setTextColor(0xFFFFFFFF);
                headeremail.setTextColor(0xFFFFFFFF);
                headerpass.setTextColor(0xFFFFFFFF);
                headerimage.setTextColor(0xFFFFFFFF);

                //If any of the inputs are left empty
                if(name.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("") || bio.getText().toString().equals("")) {
                    Toast.makeText(SignUp.this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
                }
                //If name has any incorrect validation (see method below)
                else if(!validateName(name.getText().toString())) {
                    //Change text colors to red as 'invalid'
                    headername.setTextColor(0xFFAE0700);
                    Toast.makeText(SignUp.this, "Please enter a valid name (No numbers).", Toast.LENGTH_SHORT).show();
                }
                //If email has any incorrect validation (see method below)
                else if(!validateEmail(email.getText().toString()))
                {
                    //Change text colors to red as 'invalid'
                    headeremail.setTextColor(0xFFAE0700);
                    Toast.makeText(SignUp.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                }
                //If password has any incorrect validation (see method below), or is less than 8 characters long
                else if(pass.getText().toString().length()<8 && !validatePass(pass.getText().toString()))
                {
                    //Change text colors to red as 'invalid'
                    headerpass.setTextColor(0xFFAE0700);
>>>>>>> Stashed changes
                    Toast.makeText(SignUp.this, "Please follow the password guidelines.", Toast.LENGTH_SHORT).show();
                }
                //In case user uploads a completely corrupted image or user forgot to set an image
                else if(imageData == null)
                {
                    //Change text colors to red as 'invalid'
                    headerimage.setTextColor(0xFFAE0700);
                    Toast.makeText(SignUp.this, "Please upload a profile image.", Toast.LENGTH_SHORT).show();
                }
                //If all validation requirements are met
                else
                {
<<<<<<< Updated upstream
                    Boolean userCheck = db.checkEmail(useremail);
                    if(userCheck == false)
                    {
                        Boolean result = db.insertUser(useremail, username, userpass, userbio, imageData);
                        if(result == true)
=======
                    //DB check to determine whether a user with the same email already exists
                    Users userCheck = db.checkEmail(email.getText().toString().toLowerCase());
                    //If email is not already in use
                    if(userCheck == null)
                    {
                        Boolean result = db.insertUser(user); //Addition of user into users table in db
                        if(result != null)
>>>>>>> Stashed changes
                        {
                            openHomePage();
                            Toast.makeText(SignUp.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        //Error to add user
                        else
                        {
                            Toast.makeText(SignUp.this, "Registration has failed.\nPlease try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //If email is already in use
                    else
                        Toast.makeText(SignUp.this, "Email is already registered.\nPlease sign in.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //On successful submission of details
    public void openHomePage() {
        Intent intent = new Intent(this, Home.class);
        SignUp.this.startActivity(intent);
    }

    //For upload of image
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

    //Validations
    //Validates name input to check that it starts with a capital and contains no numbers
    public boolean validateName(String name)
    {
        patt = Pattern.compile("^[A-Za-z ]+$");
        match = patt.matcher(name);
        return match.matches(); //!validateName if fails
    }

    //Validates email input to check that it is written in a valid email format
    public boolean validateEmail(String email)
    {
        patt = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        match = patt.matcher(email);
        return match.matches(); //!validateEmail if fails
    }

    //Validates password input to check that it contains a minimum of 8 characters, including lowercase and uppercase letters, a special character and a number
    public boolean validatePass(String pass)
    {
        String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$"; //Regex taken from a Stackoverflow query: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
        patt = Pattern.compile(PASSWORD_PATTERN);
        match = patt.matcher(pass);
        return match.matches(); //!validatePass if fails
    }

    //Returns back to main screen from toolbar back button
    public void ClickToHome(View view)
    {
        finish();
    }
}
