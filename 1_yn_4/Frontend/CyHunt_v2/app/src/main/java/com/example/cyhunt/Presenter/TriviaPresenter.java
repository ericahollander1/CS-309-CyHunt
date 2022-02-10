package com.example.cyhunt.Presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.CustomVolleyListenerTrivia;
import com.example.cyhunt.Model.ServerRequestTrivia;
import com.example.cyhunt.View.ITriviaView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles the logic involved in the Trivia page and sends calls
 * to the server
 * @author Lexi
 */
public class TriviaPresenter implements CustomVolleyListenerTrivia {
    private ServerRequestTrivia model;
    private ITriviaView view;
    private Context context;

    /**
     * Creates a new TriviaPresenter
     * @param view the current view
     * @param c the current context
     */
    public TriviaPresenter(ITriviaView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new ServerRequestTrivia(c, this);
    }

    /**
     * Set the model for the TriviaPresenter
     * @param m the model
     */
    public void setModel(ServerRequestTrivia m) {
        this.model = m;
    }

    /**
     * Calls a method from the model to start the process of loading a trivia
     * question from the server
     * @param i the current user ID
     */
    public void loadTrivia(int i) {
        model.getTrivia(i);
    }

    /**
     * Calls a method from the model to start the process of updating the user's
     * score inside the server
     * @param i the current user ID
     */
    public void updateCyscore(int i) {
        model.putCyscore(i);
    }

    /**
     * Calls a method from the model to start the process of updating the list of
     * trivia questions that the user has answered correctly. This removes the
     * question from the list of potential trivia to pull from next time.
     * @param i the current user ID
     * @param j the current question ID
     */
    public void updateTriviaAnswered(int i, int j) {
        model.putTriviaAnswered(i,j);
    }

    /**
     * Calls a method from the model to start the process of retrieving the user's score
     * from the server
     * @param i the current user ID
     */
    public void loadScore(int i) {
        model.getCyscore(i);
    }

    /**
     * Calls a method from the model to start the process of marking an achievement as completed
     * for the current user
     * @param i the current user ID
     * @param j the achievement ID
     */
    public void updateAchievements(int i, int j) {
        model.putAchievement(i, j);
    }

    /**
     * Calls a method from the model to retrieve the list of trivia questions that the user has
     * answered to be used for achievements
     * @param i the current user ID
     */
    //public void checkTriviaAnswered(int i) {
    //    model.getTriviaAnswered(i);
    //}

    /**
     * This handles the successful completion of a JSONArray request to retrieve a trivia question
     * from the server. It calls several methods from inside the Trivia fragment in order to
     * populate the UI.
     * @param response the JSONArray containing a trivia question
     */
    @Override
    public void onSuccess(JSONArray response) {
        try {
            JSONArray approvedQuestions = new JSONArray();
            for (int i = 0; i < response.length(); i++) {
                JSONObject triviaQ = (JSONObject)  response.get(i);
                if (triviaQ.getBoolean("approved") == true) {
                    approvedQuestions.put(response.get(i));
                }
            }
            if (approvedQuestions.length() == 0) {
                view.createSnackbar("User has no unanswered questions.");
            } else {
                Random rand = new Random();
                JSONObject triviaQuestion = (JSONObject) approvedQuestions.get(rand.nextInt(approvedQuestions.length()));

                JSONArray fakeAnswers = (JSONArray) triviaQuestion.getJSONArray("answers");
                String question = (String) triviaQuestion.getString("question");
                String answer = (String) triviaQuestion.getString("answer");
                int questionID = triviaQuestion.getInt("id");
                List<String> answers = new ArrayList<>();
                answers.add(answer);
                answers.add(fakeAnswers.getString(0));
                answers.add(fakeAnswers.getString(1));
                answers.add(fakeAnswers.getString(2));

                //fillQuestion(answers);
                view.fillQuestion(question);

                Random rand2 = new Random();
                int index2;
                List<String> randomAnswers = new ArrayList<>();

                //randomize the order of the answers
                for (int i = 0; i < 4; i++) {
                    index2 = rand2.nextInt(answers.size());
                    randomAnswers.add(answers.get(index2));
                    answers.remove(index2);
                }

                view.fillAnswers(randomAnswers);
                view.assignCorrectAnswer(answer);
                view.assignTriviaId(questionID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the successful completion of a JSON array request to update the user's
     * achievements in shared preferences
     * @param result the JSONArray containing a list of trivia questions
     */
    @Override
    public void onSuccessAchievement(JSONArray result) {
        if (result.length() == 0) {
            view.unlockAchievement();
        }
    }

    /**
     * Handles the successful completion of a string request.
     * Calls a method from view to update the user's score in shared preferences
     * @param result the string returned by the server
     */
    @Override
    public void onSuccess(String result) {
        view.setScore(result);
    }

    /**
     * Handles the unsuccessful attempt at a request.
     * Currently empty, will eventually be handled
     * @param error the VolleyError returned by the failed request
     */
    @Override
    public void onError(VolleyError error) {
        Log.d("ERROR", "Error occurred", error);
    }
}
