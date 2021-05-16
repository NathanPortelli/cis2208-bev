package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDetails extends AppCompatActivity {

    ImageButton imgUpload;
    EditText name, email, pass, bio;
    byte[] imageData;
    private Users user;
    Pattern patt;
    Matcher match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name = findViewById(R.id.edName);
        email = findViewById(R.id.edEmail);
        pass = findViewById(R.id.edPassword);
        bio = findViewById(R.id.edBio);

        fetchUserDetails();
    }

    private void fetchUserDetails()
    {
        name.setText(user.getName());
        email.setText(user.getEmail());
        pass.setText(user.getPassword());
        bio.setText((user.getBio()));
    }

    public static void redirectActivity(Activity activity, Class actClass)
    {
        Intent intent = new Intent(activity, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void btnSaveUserDetails(View view)
    {
        DBHelper db = new DBHelper(getApplicationContext());
        Users currentUser = db.findUser(user.getId());
        Users tempUser = db.checkEmail(email.getText().toString());

        if (tempUser == null)
        {
            currentUser.setName(name.getText().toString());
            currentUser.setEmail(email.getText().toString());
            currentUser.setPassword(pass.getText().toString());
            currentUser.setBio(bio.getText().toString());
            currentUser.setImg(imageData);

            if(name.getText().toString().equals("") && email.getText().toString().equals("") && pass.getText().toString().equals("") && bio.getText().toString().equals(""))
                Toast.makeText(this, "No changes were made.", Toast.LENGTH_SHORT).show();
            else if(!validateName(name.getText().toString()))
                Toast.makeText(this, "Please enter a valid name (No numbers).", Toast.LENGTH_SHORT).show();
            else if(!validateEmail(email.getText().toString()))
                Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
            else if(pass.getText().toString().length()<8 && !validatePass(pass.getText().toString()))
                Toast.makeText(this, "Please follow the password guidelines.", Toast.LENGTH_SHORT).show();
            else {
                boolean result = db.updateUser(currentUser);
                if (result = true) {
                    Toast.makeText(UserDetails.this, "Your details have been successfully updated!", Toast.LENGTH_SHORT).show();
                    redirectActivity(this, Profile.class);
                }
                else
                {
                    Toast.makeText(this, "Changes were unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

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
}