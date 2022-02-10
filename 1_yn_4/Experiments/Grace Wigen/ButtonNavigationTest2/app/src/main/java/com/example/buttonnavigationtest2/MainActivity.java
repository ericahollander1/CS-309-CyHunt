package com.example.buttonnavigationtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button submit;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button)findViewById(R.id.submitButton);
        password = (EditText)findViewById(R.id.PasswordField);

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(login(v)) {
                    openScrollingScreen();
                }
            }
        });
    }

    public boolean login(View view)
    {
        if(password.getText().toString().equals("example"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void openScrollingScreen()
    {
        Intent i = new Intent(this, ScrollingScreen.class);
        startActivity(i);
    }
}