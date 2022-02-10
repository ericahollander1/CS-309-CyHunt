
package com.example.cyhunt.Model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

/**
 * Handles all server requests for the Main page
 * @author Lexi
 */
public class ServerRequestMain {
    private CustomVolleyListenerMain myListener;
    private Context context;

    /**
     * Creates a new ServerRequestMain
     * @param c the current Context
     * @param l the current CustomVolleyListenerMain interface
     */
    public ServerRequestMain(Context c, CustomVolleyListenerMain l) {
        this.context = c;
        this.myListener = l;
    }

    /**
     * Retrieves the user's current score
     * @param i the current user ID
     */
    public void getCyscore(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/user/score/";
        url = url + String.valueOf(i);
        callServerGetScore(url);
    }

    public void getTriviaAuthored(String username) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia";
        callServerGetTriviaAuthored(url, username);
    }

    /**
     * Retrieves a list of the current user's completed achievements
     * @param i the current user ID
     */
    public void getAchievements(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/achievement/user/";
        url = url + String.valueOf(i);
        //url = "https://c1f90864-8557-4f9f-9f6a-149e1b3a7acf.mock.pstmn.io/achievements/completed/1";
        callServerGetJsonArray(url);
    }

    /**
     * Sets an achievement as completed
     * @param i the current user ID
     * @param j the achievement ID
     */
    public void putAchievement(int i, int j) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/achievements/";
        url = url + String.valueOf(i) + "/" + String.valueOf(j);
        //url = "https://c1f90864-8557-4f9f-9f6a-149e1b3a7acf.mock.pstmn.io/achievements/1/1";
        callServerPutAchievement(url);
    }

    private void callServerGetScore(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                myListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myListener.onError(error);
            }
        });
        Volley.newRequestQueue(context).add(stringRequest);
    }

    private void callServerPutAchievement(String url) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Achievement unlocked!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myListener.onError(error);
                    }
                });

        Volley.newRequestQueue(context).add(stringRequest);
    }

    private void callServerGetJsonArray(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        myListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myListener.onError(error);
                    }
                });

        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    private void callServerGetTriviaAuthored(String url, String username) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        myListener.onSuccessTriviaAuthored(response, username);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myListener.onError(error);
                    }
                });

        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }
}
