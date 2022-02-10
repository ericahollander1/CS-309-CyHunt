package com.example.cyhunt.View;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyhunt.Model.ServerRequestTrivia;
import com.example.cyhunt.Presenter.TriviaPresenter;
import com.example.cyhunt.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * Handles all android related code inside the Trivia page.
 * @author Lexi
 */
public class TriviaActivity extends AppCompatActivity implements ITriviaView {

    private TextView textView;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private Button button;
    private String answer;
    private int questionID = -1;
    private TriviaPresenter presenter;
    private ServerRequestTrivia model;
    private SharedPreferences sharedPreferences;
    private View view;
    private final int ACHIEVEMENT_FIRST_TRIVIA_CORRECT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        view = this.findViewById(android.R.id.content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CyHunt");

        sharedPreferences = getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);

        textView = (TextView) findViewById(R.id.triviaQuestion);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        button = (Button) findViewById(R.id.button);


        presenter = new TriviaPresenter(this, getApplicationContext());

        presenter.loadTrivia(sharedPreferences.getInt("USER_ID", -1));

        // when button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(view);
            }
        });


    }

    /**
     * Fills the appropriate TextView on the UI with the trivia question
     * @param s the trivia question
     */
    @Override
    public void fillQuestion(String s) {
        textView.setText(s);
    }

    /**
     * Fills the RadioButtons on the UI with the answer choices
     * @param list a list of four possible randomized answers
     */
    //populate UI with answers
    @Override
    public void fillAnswers(List<String> list) {
        radioButton1.setText(list.get(0));
        radioButton2.setText(list.get(1));
        radioButton3.setText(list.get(2));
        radioButton4.setText(list.get(3));
    }

    /**
     * Saves the current correct answer from the server to be compared against later
     * @param s the correct answer to the current trivia question
     */
    @Override
    public void assignCorrectAnswer(String s) {
        answer = s;
    }

    /**
     * Saves the current question ID to be used later
     * @param qID the current question ID
     */
    @Override
    public void assignTriviaId(int qID) {
        questionID = qID;
    }

    /**
     * Creates a Snackbar with a given message
     * @param s a message to display
     */
    @Override
    public void createSnackbar(String s) {
        Snackbar.make(view, s, Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * Returns the user to the map page after answering the question
     */
    @Override
    public void returnToMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /**
     * Updates the user's score inside shared preferences
     * @param s the user's new score
     */
    @Override
    public void setScore(String s) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_SCORE", parseInt(s));
        editor.apply();
    }

    /**
     * Updates the user's achievements inside shared preferences and the server
     */
    @Override
    public void unlockAchievement() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("ACHIEVEMENT_FIRST_TRIVIA_CORRECT", 1);
//        editor.apply();
//        presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_FIRST_TRIVIA_CORRECT);
    }


    private void checkAnswer(View view) {
        String userGuess = "";
        //Get user's guess
        if (radioButton1.isChecked()) {
            userGuess = (String) radioButton1.getText();
        }
        else if (radioButton2.isChecked()) {
            userGuess = (String) radioButton2.getText();
        }
        else if (radioButton3.isChecked()) {
            userGuess = (String) radioButton3.getText();
        }
        else if (radioButton4.isChecked()){
            userGuess = (String) radioButton4.getText();
        }
        else {
            Snackbar.make(view, R.string.answer_not_selected, Snackbar.LENGTH_SHORT).show();
            return;
        }

        //check if user's guess is correct
        if (userGuess == answer) {
            //handles an achievement
            if (sharedPreferences.getInt("ACHIEVEMENT_FIRST_TRIVIA_CORRECT", -1) != 1) {
                //presenter.checkTriviaAnswered(sharedPreferences.getInt("USER_ID", -1));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("ACHIEVEMENT_FIRST_TRIVIA_CORRECT", 1);
                editor.apply();
                presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_FIRST_TRIVIA_CORRECT);
            }

            //give user points
            presenter.updateCyscore(sharedPreferences.getInt("USER_ID", -1));
            presenter.loadScore(sharedPreferences.getInt("USER_ID", -1));
            //add question id to list of questions the user has answer correctly
            presenter.updateTriviaAnswered(sharedPreferences.getInt("USER_ID", -1), questionID);


            createPopup("Correct!");
        } else {
            createPopup("Incorrect");
        }
    }

    private void createPopup(String s) {
        String message = "";
        if (s == "Correct!") {
            message = "That was correct! You earned 5 points.";
        }
        else if (s == "Incorrect") {
            message = "That was incorrect. You did not earn any points.";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(TriviaActivity.this);
        builder.setMessage(message).setTitle(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Take user to back to landing page
                // Tried taking user back to map but then the back button goes to trivia
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Used for testing purposes only. Sets the TriviaPresenter
     * @param p a TriviaPresenter
     */
    public void testSetPresenter(TriviaPresenter p) {
        this.presenter = p;
    }

    /**
     * Used for testing purposes only. Returns the current TriviaPresenter
     * @return the current TriviaPresenter
     */
    public TriviaPresenter testGetPresenter() {
        return presenter;
    }
}