package com.example.cyhunt.Model;

import com.android.volley.VolleyError;
/**
 * Allows for the use of asynchronous JSON requests
 */
public interface CustomVolleyListenerSignup {
    /**
     * if the JSON request creating the new user was successful, either call another function or
     * display a failure message
     * @param s the message returned by the database
     */
    void onNewUserCreated(String s);

    /**
     * if the last JSON request was found, use the returned information to store the current users
     * info in sharedPreferences
     * @param id retrieved ID
     * @param role retreived role
     * @param score retrieved score
     * @param emailId retrieved emailID
     */
    void onNewUserDataRetrieved(int id, String role, int score, String emailId);

    /**
     * Handles the failed completion of a JSON request
     * @param error
     */
    void onError(VolleyError error);
}
