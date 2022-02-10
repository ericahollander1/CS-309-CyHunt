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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.CustomVolleyListenerSignup;
import com.example.cyhunt.Model.SignupCredentialCheckerInterface;
import com.example.cyhunt.Presenter.SignupCredentialChecker;
import com.example.cyhunt.R;

/**
 * this is the screen that allows the user to sign up for the app
 */
public class SignupActivity extends AppCompatActivity implements CustomVolleyListenerSignup {

    private TextView req1;
    private TextView req2;
    private TextView req3;
    private TextView password;
    private TextView repeatPassword;
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private String green = "#00ff3c";
    private String red = "#ff0000";
    private SignupCredentialCheckerInterface s;
    /**
     * shared preferences
     */
    SharedPreferences sharedPreferences;

    //private Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CyHunt");
        //timer.schedule(task, 500, 500);
        req1 = (TextView)findViewById(R.id.PasswordRequirement1);
        req2 = (TextView)findViewById(R.id.PasswordRequirement2);
        req3 = (TextView)findViewById(R.id.PasswordRequirement3);
        password = (TextView)findViewById(R.id.Password);
        repeatPassword = (TextView)findViewById(R.id.RepeatPassword);
        lastName = (TextView)findViewById(R.id.LastName);
        firstName = (TextView)findViewById(R.id.FirstName);
        email = (TextView)findViewById(R.id.UserEmail);
        sharedPreferences = getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);
    }

    /**
     * if the user clicks the signup button, check their username and password for existence
     * and completeness, then check their chosen username and password against the database
     * @param v required view
     */
    public void onClickSignUpButton(View v) {
        s = new SignupCredentialChecker(this);
        boolean PasswordValid = false;
        boolean UserDoesNotAlreadyExist = true;
        boolean UsernameIsNotAlreadyTaken = true;
        //Check that the password has met the requirements first
        String PassMessage = s.checkPasswordRequirements(password.getText().toString(), repeatPassword.getText().toString());
        String NameMessage = s.checkNameRequirements(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
        Log.d("PASSWORD_MESSAGE", PassMessage);
        Log.d("NAME_MESSAGE", NameMessage);
        if(PassMessage == "valid") {
            if(NameMessage == "valid")
            {
                s.addNewUserIfPossible(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), this);
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setMessage(NameMessage).setTitle("Signup failed!");
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
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setMessage(PassMessage).setTitle("Signup failed!");
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

    /**
     * when the user clicks the log in button, return them to the login screen
     * @param v required view
     */
    public void onClickLoginButton(View v) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        //timer.cancel();
        //intent.putExtra("PLAY_AS_GUEST", 1);
        startActivity(intent);
    }
    /**
     *
     * if the JSON request creating the new user was successful, either call another function or
     * display a failure message
     * @param r the message returned by the database
     */
    @Override
    public void onNewUserCreated(String r)
    {
        if(r.contains("success"))
        {
            s = new SignupCredentialChecker(this);
            s.getUserDataById(this, email.getText().toString());
        }
        if(r.contains("failure"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setMessage("New user could not be created...").setTitle("Signup failed!");
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

    /**
     * if the last JSON request was found, use the returned information to store the current users
     * info in sharedPreferences
     * @param id retrieved ID
     * @param role retreived role
     * @param score retrieved score
     * @param emailId retrieved emailID
     */
    @Override
    public void onNewUserDataRetrieved(int id, String role, int score, String emailId)
    {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_ID", id);
        editor.putString("USER_ROLE", "USER");
        editor.putString("USERNAME", emailId);
        editor.putInt("USER_SCORE", score);
        editor.apply();
        //intent.putExtra("USER_ID", id);
        startActivity(intent);
    }

    /**
     * Handles the failed completion of a JSON request
     * @param error
     */
    @Override
    //this will be called when the user already exists
    public void onError(VolleyError error)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setMessage("New User could not be created!").setTitle("Signup failed!");
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