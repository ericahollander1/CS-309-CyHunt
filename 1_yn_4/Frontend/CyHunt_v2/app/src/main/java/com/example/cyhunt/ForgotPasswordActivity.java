package com.example.cyhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cyhunt.View.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }


    public void onClickRetievePasswordButton(View v) {
        if(retrievePassword()) {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);

            //intent.putExtra("RETRIEVE_PASSWORD", 1);
            startActivity(intent);
        }
        else
        {
            //TODO send the user a popup informing them that thier credentials are invalid.
        }
    }

    //TODO have this confer with the database to confirm the user is real, and then send them a retrieval email.
    public boolean retrievePassword()
    {
        return true;
    }

    public void onNevermindButton(View v) {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);

        //intent.putExtra("NEVERMIND", 1);
        startActivity(intent);
    }
}