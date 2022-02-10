package com.example.cyhunt.Model;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Allows for the use of asynchronous JSON requests
 * @author Lexi
 */
public interface CustomVolleyListenerTrivia {
    /**
     * Handles the successful completion of a JSON array request
     * to retrieve a trivia question
     * @param result the JSONArray containing a trivia question
     */
    void onSuccess(JSONArray result);

    /**
     * Handles the successful completion of a JSON array request
     * to retrieve the list of answered trivia questions
     * @param result the JSONArray containing a list of trivia questions
     */
    void onSuccessAchievement(JSONArray result);

    /**
     * Handles the successful completion of a string request
     * @param result the string returned by the server
     */
    void onSuccess(String result);

    /**
     * Handles the unsuccessful attempt at a request
     * @param error the VolleyError returned by the failed request
     */
    void onError(VolleyError error);
}
