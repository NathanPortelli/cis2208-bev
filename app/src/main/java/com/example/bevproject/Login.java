package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    EditText useremail, userpass;
    Button btnLogin;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        useremail = (EditText) findViewById(R.id.edLoginEmail);
        userpass = (EditText) findViewById(R.id.edLoginPass);
        btnLogin = (Button) findViewById(R.id.btnLoginEnter);

        db = new DBHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = useremail.getText().toString().toLowerCase();
                String pass = userpass.getText().toString();

                if(user.equals("") || pass.equals(""))
                    Toast.makeText(Login.this, "Please fill in all the information.", Toast.LENGTH_SHORT).show();
                else
                {
                    Boolean result = db.checkLogin(user, pass);
                    if(result == true)
                    {
                        openHomePage();
                        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                        Toast.makeText(Login.this, "Invalid Information.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openHomePage() {
        Intent intent = new Intent(this, Home.class);
        Login.this.startActivity(intent);
    }
}
