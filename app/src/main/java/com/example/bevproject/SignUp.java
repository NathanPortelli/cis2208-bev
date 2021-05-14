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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity
{
    int id;
    ImageButton imgUpload;
    EditText name, email, pass, bio;
    Button btnSignUpNext;
    //Database reference
    DBHelper db;
    //Image information
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    byte[] imageData;
    //Validation
    Pattern patt;
    Matcher match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        name = (EditText) findViewById(R.id.edName);
        email = (EditText) findViewById(R.id.edEmail);
        pass = (EditText) findViewById(R.id.edPassword);
        bio = (EditText) findViewById(R.id.edBio);
        db = new DBHelper(this);

        btnSignUpNext = (Button) findViewById(R.id.btnSignUpRegister);

        imgUpload = (ImageButton) findViewById(R.id.imgUpload);

        imgUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), PICK_IMAGE);
            }
        });

        btnSignUpNext.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Users user = new Users();
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString().toLowerCase());
                user.setPassword(pass.getText().toString());
                user.setBio(bio.getText().toString());
                user.setImg(imageData);

                if(name.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("") || bio.getText().toString().equals(""))
                    Toast.makeText(SignUp.this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
                else if(!validateName(name.getText().toString()))
                    Toast.makeText(SignUp.this, "Please enter a valid name (No numbers).", Toast.LENGTH_SHORT).show();
                else if(!validateEmail(email.getText().toString()))
                    Toast.makeText(SignUp.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                else if(pass.getText().toString().length()<8 && !validatePass(pass.getText().toString()))
                    Toast.makeText(SignUp.this, "Please follow the password guidelines.", Toast.LENGTH_SHORT).show();
                else if(imageData == null)
                    Toast.makeText(SignUp.this, "Please upload a profile image.", Toast.LENGTH_SHORT).show();
                else
                {
                    Users userCheck = db.checkEmail(email.getText().toString().toLowerCase());
                    if(userCheck == null)
                    {
                        Boolean result = db.insertUser(user);
                        if(result != null)
                        {
                            openHomePage();
                            Toast.makeText(SignUp.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Registration has failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(SignUp.this, "Email is already registered.\nPlease sign in.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Home.class);
        SignUp.this.startActivity(intent);
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

    //Validations

    public boolean validateName(String name)
    {
        patt = Pattern.compile("^[A-Za-z ]+$");
        match = patt.matcher(name);
        return match.matches();
    }

    public boolean validateEmail(String email)
    {
        patt = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        match = patt.matcher(email);
        return match.matches();
    }

    public boolean validatePass(String pass)
    {
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        patt = Pattern.compile(PASSWORD_PATTERN);
        match = patt.matcher(pass);
        return match.matches();
    }

    public void ClickToHome(View view)
    {
        finish();
    }
}
