package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignupPage extends AppCompatActivity {

    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup_page);
        continueBtn = (Button) findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(v -> signupPage2());
    }

    public void signupPage2() {
        Intent intent = new Intent(this, SignupPage2.class);
        startActivity(intent);
    }
}