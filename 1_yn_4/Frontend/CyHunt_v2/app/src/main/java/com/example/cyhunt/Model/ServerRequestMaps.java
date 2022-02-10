package com.example.cyhunt.Model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Handles all server requests for the Maps page
 * @author Lexi
 */
public class ServerRequestMaps {
    private CustomVolleyListenerMaps myListener;
    private Context context;
    private RequestQueue requestQueue;

    /**
     * Creates a new ServerRequestMaps
     * @param c the current Context
     * @param l the current CustomVolleyListenerMaps interface
     */
    public ServerRequestMaps(Context c, CustomVolleyListenerMaps l) {
        this.context = c;
        this.myListener = l;
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Retrieves the user's current location of Cy in the server
     * @param i the current user ID
     */
    public void getCurrentPlace(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/users/";
        url = url + String.valueOf(i) + "/CyLocation";
        callServerGetCurrentPlace(url);
    }

    /**
     * Generates a new location for Cy
     * @param i the current user ID
     */
    public void getNewPlace(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/places/generateNewLocation/user/";
        url = url + String.valueOf(i);
        callServerGetNewPlace(url);
    }

    /**
     * Updates the user's score in the server
     * @param i the current user ID
     */
    public void putCyscore(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/users/";
        url = url + String.valueOf(i) + "/setCyscore";
        callServerPutCyscore(url);
    }

    /**
     * Sets an achievement as completed in the server
     * @param i the current user ID
     * @param j the achievement ID
     */
    public void putAchievement(int i, int j) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/achievements/";
        url = url + String.valueOf(i) + "/" + String.valueOf(j);
        //url = "https://c1f90864-8557-4f9f-9f6a-149e1b3a7acf.mock.pstmn.io/achievements/1/1";
        callServerPutAchievement(url);
    }

    private void callServerGetCurrentPlace(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        response -> myListener.onSuccessLoadPlace(response),
                        error -> myListener.onError(error));

        requestQueue.add(jsonObjectRequest);
    }

    private void callServerGetNewPlace(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        response -> myListener.onSuccessLoadPlace(response),
                        error -> myListener.onError(error));

        requestQueue.add(jsonObjectRequest);
    }

    private void callServerPutAchievement(String url) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.PUT,
                        url,
                        response -> myListener.onSuccessUpdateAchievement(response),
                        error -> myListener.onError(error));

        requestQueue.add(stringRequest);
    }

    private void callServerPutCyscore(String url) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.PUT,
                        url,
                        response -> myListener.onSuccessUpdateCyscore(response),
                        error -> myListener.onError(error));

        requestQueue.add(stringRequest);
    }
}
