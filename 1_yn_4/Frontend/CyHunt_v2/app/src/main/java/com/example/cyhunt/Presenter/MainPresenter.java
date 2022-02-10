package com.example.cyhunt.Presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.cyhunt.View.IMainView;
import com.example.cyhunt.Model.CustomVolleyListenerMain;
import com.example.cyhunt.Model.ServerRequestMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles the logic involved in the Main page and sends calls
 * to the server.
 * @author Lexi
 */
public class MainPresenter implements CustomVolleyListenerMain {
    private ServerRequestMain model;
    private IMainView view;
    private Context context;

    /**
     * Creates a new MainPresenter
     * @param view the current view
     * @param c the current context
     */
    public MainPresenter(IMainView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new ServerRequestMain(c, this);
    }

    /**
     * Set the model for the MainPresenter
     * @param m the model
     */
    public void setModel(ServerRequestMain m) {
        this.model = m;
    }

    /**
     * Calls a method from the model to start the process of retrieving the user's score
     * from the server
     * @param i the current user ID
     */
    public void loadCyscore(int i) {
        model.getCyscore(i);
    }

    public void loadTriviaAuthored(String username) {
        model.getTriviaAuthored(username);
    }

    /**
     * Calls a method from the model to start the process of loading the
     * current user's completed achievements from the server
     * @param i the current user ID
     */
    public void loadAchievements(int i) {
        model.getAchievements(i);
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
     * Handles the successful completion of a JSONArray request
     * Used for achievements
     * @param result the JSONArray returned by the server
     */
    @Override
    public void onSuccess(JSONArray result) {
        try {
            for (int i = 0; i < result.length(); i++) {
                JSONObject jsonObject = (JSONObject) result.getJSONObject(i);
                int achievementID = jsonObject.getInt("id");
                view.checkAchievements(achievementID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
     * Handles the successful completion of a JSON array request to retrieve the user's
     * authored trivia questions
     * @param result the JSONArray returned by the server
     */
    @Override
    public void onSuccessTriviaAuthored(JSONArray result, String username) {
        try {
            JSONArray approvedQuestions = new JSONArray();
            for (int i = 0; i < result.length(); i++) {
                JSONObject triviaQ = (JSONObject) result.get(i);
                if ((triviaQ.getBoolean("approved") == true) && (triviaQ.getString("emailId")) == username) {
                    approvedQuestions.put(result.get(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
