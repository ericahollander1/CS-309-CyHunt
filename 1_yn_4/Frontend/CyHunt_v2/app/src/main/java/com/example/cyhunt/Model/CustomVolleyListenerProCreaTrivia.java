package com.example.cyhunt.Model;

import com.android.volley.VolleyError;

public interface CustomVolleyListenerProCreaTrivia {
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

    void onSuccessComplete(String s);

}
