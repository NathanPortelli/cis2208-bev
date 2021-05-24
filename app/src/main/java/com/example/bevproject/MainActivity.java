package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

//Used by initial screen of application
public class MainActivity extends AppCompatActivity {
    //Initialisation of buttons
    private Button loginBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpBtn = findViewById(R.id.btnSignUp);
        loginBtn = findViewById(R.id.btnLogin);

        //see methods below
        signUpBtn.setOnClickListener(v -> openSignUpPage());
        loginBtn.setOnClickListener(v -> openLoginPage());
    }

    //Opens 'Sign Up' Activity when clicked
    public void openSignUpPage() {
        Intent intent = new Intent(this, SignUp.class);
        MainActivity.this.startActivity(intent);
    }

    //Opens 'Log In' Activity when clicked
    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        MainActivity.this.startActivity(intent);
    }
}