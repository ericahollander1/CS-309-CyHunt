package com.example.cyhunt.Model;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Allows for the use of asynchronous JSON requests
 * @author Lexi
 */
public interface CustomVolleyListenerMaps {

    /**
     * Handles the successful completion of a JSON object request for the current location of Cy
     * @param result the JSONObject returned by the server containing the current location of Cy
     */
    void onSuccessLoadPlace(JSONObject result);

    /**
     * Handles the successful completion of a PUT request to update the user's achievements
     * @param result the String returned by the server
     */
    void onSuccessUpdateAchievement(String result);

    /**
     * Handles the successful completion of a PUT request to update the user's score
     * @param result the string returned by the server
     */
    void onSuccessUpdateCyscore(String result);

    /**
     * Handles the unsuccessful attempt at a request
     * @param error the VolleyError returned by the failed request
     */
    void onError(VolleyError error);
}
