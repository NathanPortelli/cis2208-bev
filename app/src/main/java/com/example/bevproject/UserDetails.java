package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class UserDetails extends AppCompatActivity {

    ImageButton imgUpload;
    EditText name, email, pass, bio;
    TextView txImage, txName, txEmail, txPassword, txBio;
    byte[] imageData;
    private Users user;
    Pattern patt;
    Matcher match;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //EditText declarations
        name = findViewById(R.id.edName);
        email = findViewById(R.id.edEmail);
        pass = findViewById(R.id.edPassword);
        bio = findViewById(R.id.edBio);

        //TextView declarations
        txImage = findViewById(R.id.txImage);
        txName = findViewById(R.id.txName);
        txEmail = findViewById(R.id.txEmail);
        txPassword = findViewById(R.id.txPassword);
        txBio = findViewById(R.id.txBio);

        user = (Users) getIntent().getSerializableExtra("user");
        //Setting EditTexts to display current user's details
        name.setText(user.getName());
        email.setText(user.getEmail());
        pass.setText(user.getPassword());
        bio.setText((user.getBio()));

        //Input for upload of article image
        imgUpload = findViewById(R.id.imgUpload);
        byte[] pic = user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        Bitmap btm = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()),(int)(bitmap.getWidth()),true);
        imgUpload.setImageBitmap(btm);
        imageData = getByteArray(btm);

        imgUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gallery = new Intent();
                gallery.setType("image/*"); //sets gallery information to images only
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Profile Picture"), PICK_IMAGE);
            }
        });
    }

    //Method to save the current user's newly inputted details
    public void btnSaveUserDetails(View view)
    {
        DBHelper db = new DBHelper(getApplicationContext());
        Users currentUser = db.findUser(user.getId());

        currentUser.setName(name.getText().toString());
        currentUser.setEmail(email.getText().toString());
        currentUser.setPassword(pass.getText().toString());
        currentUser.setBio(bio.getText().toString());
        currentUser.setImage(imageData);

        //Resetting colors of text
        txName.setTextColor(0xFFFFFFFF);
        txEmail.setTextColor(0xFFFFFFFF);
        txPassword.setTextColor(0xFFFFFFFF);
        txBio.setTextColor(0xFFFFFFFF);

        if(name.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("") || bio.getText().toString().equals(""))
            Toast.makeText(this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
        else if(!validateName(name.getText().toString())) {
            txName.setTextColor(0xFFAE0700);
            Toast.makeText(this, "Please enter a valid name (No numbers).", Toast.LENGTH_SHORT).show();
        }
        else if(!validateEmail(email.getText().toString())){
            txEmail.setTextColor(0xFFAE0700);
            Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
        }
        else if(pass.getText().toString().length()<8 && !validatePass(pass.getText().toString())){
            txPassword.setTextColor(0xFFAE0700);
            Toast.makeText(this, "Please follow the password guidelines.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (db.updateUser(currentUser)) { //Checking whether update user was successful
                Intent intent = new Intent(UserDetails.this, Profile.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
                Toast.makeText(UserDetails.this, "Updates successful!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(this, "Changes were unsuccessful.\nPlease try again.", Toast.LENGTH_SHORT).show();
            }
        }
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

    //Sets image output to byte array form
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

    //When tapping the back button
    public void ClickToHome(View view)
    {
        finish();
    }
}