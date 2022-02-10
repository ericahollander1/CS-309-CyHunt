package com.example.cyhunt.Model;

import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Allows for the use of asynchronous JSON requests
 */
public interface CustomVolleyListenerArrayList {
    /**
     * Handles the successful completion of a JSON request
     * @param l the arrayList returned by the JSON request
     */
    void onSuccess(ArrayList<String> l);

    /**
     * Handles the failed completion of a JSON request
     * @param error
     */
    void onError(VolleyError error);
}
