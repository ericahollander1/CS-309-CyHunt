package com.example.cyhunt_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //instantiate variables
    private Button loginButton;
    private Button signUpButton;
    private Button retrievePasswordButton;

    private TextView passwordField;
    private TextView usernameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign all the variables
        loginButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        retrievePasswordButton = (Button)findViewById(R.id.forgotPasswordButton);

        passwordField = (TextView)findViewById(R.id.passwordField);
        usernameField = (TextView)findViewById(R.id.usernameField);

        //button listeners
        loginButton.setOnClickListener(new View.OnClickListener(){//user clicks the login button
            public void onClick(View v)
            {
                if(validateUsername() && validatePassword())//if username and password are correct
                {
                    Intent i = new Intent(MainActivity.this, AboutTheApp.class);
                    startActivity(i); //go to AboutTheApp
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){//user clicks the sign up button
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, NewUserSignup.class);
                startActivity(i); //go to NewUSerSignup
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){//user clicks the forgot password button
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(i); //go to ForgotPassword
            }
        });

    }
    //other functions
    /*
    check if the username is a valid part of the database
     */
    public boolean validateUsername()
    {
        //TODO check the contents of the username field against the database
        return true; //temp
    }

    /*
    check if the password is a valid part of the database
     */
    public boolean validatePassword()
    {
        //TODO check the contents of the username field against the database
        return true; //temp
    }
}