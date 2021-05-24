package com.example.bevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    EditText useremail, userpass;
    TextView headeremail, headerpass;
    Button btnLogin;

    DBHelper db;

    //On creation of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Titles on top of input
        headeremail = findViewById(R.id.txLoginEmail);
        headerpass = findViewById(R.id.txLoginPass);

        //Actual input of details
        useremail = findViewById(R.id.edLoginEmail);
        userpass = findViewById(R.id.edLoginPass);
        btnLogin = findViewById(R.id.btnLoginEnter);

        db = new DBHelper(this);
        //When 'Login' button is pressed
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String user = useremail.getText().toString().toLowerCase(); //Since all emails are set to lowercase in sign up
                String pass = userpass.getText().toString();
                //Resetting colors of text
                headeremail.setTextColor(0xFFFFFFFF);
                headerpass.setTextColor(0xFFFFFFFF);
                useremail.setTextColor(0xFFFFFFFF);

                //Validations
                if(user.equals("")) //If no email input was set
                {
                    //Change text colors to red as 'invalid'
                    headeremail.setTextColor(0xFFAE0700);
                    Toast.makeText(Login.this, "Please fill in your email.", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals("")) //If no password input was set
                {
<<<<<<< Updated upstream
                    Boolean result = db.checkLogin(user, pass);
                    if(result == true)
=======
                    //Change text colors to red as 'invalid'
                    headerpass.setTextColor(0xFFAE0700);
                    Toast.makeText(Login.this, "Please fill in your password.", Toast.LENGTH_SHORT).show();
                }
                else //If all inputs are inserted
                {
                    //Checks login
                    Users result = db.checkLogin(user, pass);
                    if(result != null)
>>>>>>> Stashed changes
                    {
                        openHomePage();
                        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    //Database does not find the user with the inputted details
                    else
                    {
                        //Change text colors to red as 'invalid'
                        headeremail.setTextColor(0xFFAE0700);
                        headerpass.setTextColor(0xFFAE0700);
                        useremail.setTextColor(0xFFAE0700);
                        Toast.makeText(Login.this, "Invalid Information.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //On successful submission of details
    public void openHomePage() {
        Intent intent = new Intent(this, Home.class);
        Login.this.startActivity(intent);
    }

    //Returns back to main screen from toolbar back button
    public void ClickToHome(View view)
    {
        finish();
    }
}
