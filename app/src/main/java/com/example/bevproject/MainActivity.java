package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        signUpBtn = (Button) findViewById(R.id.btnSignUp);
        loginBtn = (Button) findViewById(R.id.btnLogin);

        signUpBtn.setOnClickListener(v -> openSignUpPage());
        loginBtn.setOnClickListener(v -> openLoginPage());
    }

    public void openSignUpPage() {
        Intent intent = new Intent(this, SignUp.class);
        MainActivity.this.startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        MainActivity.this.startActivity(intent);
    }
}