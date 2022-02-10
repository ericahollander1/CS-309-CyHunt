package com.example.cyhunt.Model;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Allows for the use of asynchronous JSON requests
 * @author Lexi
 */
public interface CustomVolleyListenerDashboard {
    /**
     * Handles the successful completion of a JSON array request
     * @param result the JSONArray returned by the server
     */
    void onSuccess(JSONArray result);

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
