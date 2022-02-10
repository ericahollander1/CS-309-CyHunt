package com.example.cyhunt.Model;

import com.android.volley.VolleyError;

import java.util.ArrayList;
/**
 * Allows for the use of asynchronous JSON requests for login screen
 */
public interface CustomVolleyListenerLogin {
    /**
     * if the login was successful, we check the returned string to see if the user was found
     * if it was, return true, if not, return false
     * @param s a string containing either the ID of the student or a fail message
     * @return a boolean
     */
    boolean onSuccess(String s);

    /**
     * Handles the failed completion of a JSON request
     * @param error
     */
    void onError(VolleyError error);


    /**
     * if the last JSON request was found, use the returned information to store the current users
     * info in sharedPreferences
     * @param id retrieved ID
     * @param role retreived role
     * @param score retrieved score
     * @param emailId retrieved emailID
     */
    void onSuccessStudentFound(int id, String role, int score, String emailId);

    void onGuestCreated(String response);
}
