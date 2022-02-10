package com.example.cyhunt.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.example.cyhunt.Model.CustomVolleyListenerLogin;
import com.example.cyhunt.Model.LoginFuntionalityInterface;
import com.example.cyhunt.ForgotPasswordActivity;
import com.example.cyhunt.Presenter.LoginFunctionality;
import com.example.cyhunt.R;
import com.example.cyhunt.View.SignupActivity;

/**
 * this is the screen that allows the user to log in to the app
 */
public class LoginActivity extends AppCompatActivity implements CustomVolleyListenerLogin {
    private int userID = -1;
    private TextView username;
    private TextView password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = (TextView)findViewById(R.id.userUsername);
        password = (TextView)findViewById(R.id.userPassword);
        sharedPreferences = getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    /**
     * when the 'play as guest' button is clicked, give the user an ID of 999, and certain
     * other statistics to show they are a guest
     * @param v required view
     */
    public void onClickGuestButton(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        LoginFuntionalityInterface l = new LoginFunctionality(getApplicationContext());
        l.signInAsGuest(this);
        /*
        editor.putInt("USER_ID", 999);
        editor.putString("USER_ROLE", "GUEST");
        editor.putString("USERNAME", "Guest User");
        editor.apply();
        //editor.putInt("USER_SCORE", score);
        //intent.putExtra("USER_ID", id);
        startActivity(intent);
         */
    }

    /**
     * When the user clicks the 'sign up' button, take them to the signup screen
     * @param v requeired view
     */
    public void onClickSignUpButton(View v) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);

        //intent.putExtra("SIGN_UP", 1);
        startActivity(intent);
    }

    /**
     * When the user clicks the login button, first check if their username and password exist. If so,
     * see if they exist in the database and then either log them or reject them with an error message
     * @param v
     */
    public void onClickLoginButton(View v) {
        LoginFuntionalityInterface l = new LoginFunctionality(getApplicationContext());
        if (l.checkIfUserCredentialsExist(password.getText().toString(), username.getText().toString())) {
            l.checkIfLoginValid(this, username.getText().toString(), password.getText().toString()); //this will use the CustomVolleyListenerLogin methods implemented below to see if the user's info is valid or not.
        } else //username or password are not even in
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Please input both a username and password!").setTitle("Login failed!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    //nothing happens but the dialog closes
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void userLoginValid(String emailId)
    {
        LoginFuntionalityInterface l = new LoginFunctionality(getApplicationContext());
        l.getUserDataById(this, emailId);
    }

    private void userLoginInvalid()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Username or Password Invalid!").setTitle("Login failed!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //nothing happens but the dialog closes
            }
            });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

        /*{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            userID = 999;
            intent.putExtra("USER_ID", userID);
            startActivity(intent);
        }
        else
        {
            //TODO send popup notification to inform the user they are invalid
        }
        */


    /**
     * when the user clicks the 'forgot password' button, take them to the forgot password screen
     * @param v
     */
    /**
    public void onClickForgotPasswordButton(View v) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);

        //intent.putExtra("FORGOT_PASSWORD", 1);
        startActivity(intent);
    }
     */

    /**
     * if the login was successful, we check the returned string to see if the user was found
     * if it was, return true, if not, return false
     * @param s a string containing either the ID of the student or a fail message
     * @return a boolean
     */
    @Override
    public boolean onSuccess(String s) {
        if(s.contains("Wrong Password") || s.contains("User does not exist"))
        {
            userLoginInvalid();
            return false;
        }
        else
        {
            userLoginValid(s);
            return true;
        }
    }

    /**
     * Handles the failed completion of a JSON request
     * @param error
     */
    @Override
    public void onError(VolleyError error) {
        //try {
        //    String responseBody = new String(error.networkResponse.data, "utf-8");
            Log.d("TAG", error.toString());
        //} catch (UnsupportedEncodingException e) {
        //    Log.d("TAG", e.toString());
       // }
    }

    /**
     * if the last JSON request was found, use the returned information to store the current users
     * info in sharedPreferences
     * @param id retrieved ID
     * @param role retreived role
     * @param score retrieved score
     * @param emailId retrieved emailID
     */
    @Override
    public void onSuccessStudentFound(int id, String role, int score, String emailId)
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_ID", id);
        editor.putString("USER_ROLE", role);
        editor.putString("USERNAME", emailId);
        editor.putInt("USER_SCORE", score);
        editor.apply();
        Log.d("USER_INFO", "ID=" + id + ", role=" + role + ", score=" + score + ", emailID="+ emailId);
        //intent.putExtra("USER_ID", id);
        startActivity(intent);
    }

    @Override
    public void onGuestCreated(String response)
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_ID", Integer.parseInt(response));
        editor.putString("USER_ROLE", "GUEST");
        editor.putString("USERNAME", "GUEST" + Integer.parseInt(response));
        editor.putInt("USER_SCORE", 0);
        editor.apply();
        //Log.d("USER_INFO", "ID=" + id + ", role=" + role + ", score=" + score + ", emailID="+ emailId);
        //intent.putExtra("USER_ID", id);
        startActivity(intent);
    }
}