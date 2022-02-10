package com.example.cyhunt.Model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyhunt.Model.CustomVolleyListenerTrivia;

import org.json.JSONArray;

/**
 * Handles all server requests for the Trivia page
 * @author Lexi
 */
public class ServerRequestTrivia {
    private CustomVolleyListenerTrivia myListener;
    private Context context;

    /**
     * Creates a new ServerRequestTrivia
     * @param c the current Context
     * @param l the current CustomVolleyListenerTrivia interface
     */
    public ServerRequestTrivia(Context c, CustomVolleyListenerTrivia l) {
        this.context = c;
        this.myListener = l;
    }

    /**
     * Retrieves the user's current score
     * @param i the current userID
     */
    public void getCyscore(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/user/score/";
        url = url + String.valueOf(i);
        callServerGetScore(url);
    }

    /**
     * Retrieves a question from a list of trivia that the user has not answered correctly yet
     * @param i the current user ID
     */
    public void getTrivia(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia/not/answered/";
        url = url + String.valueOf(i);
        //url = "https://c1f90864-8557-4f9f-9f6a-149e1b3a7acf.mock.pstmn.io/trivia/not/answered/1";
        callServerGetJsonArray(url);
    }

    /**
     * Updates the user's score in the server
     * @param i the current user ID
     */
    public void putCyscore(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/users/";
        url = url + String.valueOf(i) + "/setCyscore";
        callServerPut(url);
    }

    /**
     * Updates the user's list of trivia they've answered correctly
     * @param i the current user ID
     * @param j the current question ID
     */
    public void putTriviaAnswered(int i, int j) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia/answered/";
        url = url + String.valueOf(i) + "/" + String.valueOf(j);
        callServerPut(url);
    }

    /**
     * Retrieves the user's list of trivia they've answered correctly
     * For use with achievements
     * @param i the current user ID
     */
    public void getTriviaAnswered(int i) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia/answered/";
        url = url + String.valueOf(i);
        callServerGetJsonArrayAchievement(url);
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

    private void callServerGetJsonArrayAchievement(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        myListener.onSuccessAchievement(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        myListener.onError(error);
                    }
                });

        Volley.newRequestQueue(context).add(jsonArrayRequest);
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
                        // TODO: Handle error
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
                        // TODO: Handle error
                        myListener.onError(error);
                    }
                });

        Volley.newRequestQueue(context).add(jsonArrayRequest);
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

    private void callServerPut(String url) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("***************************Question added to answered list******************************");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myListener.onError(error);
                    }
                });

        Volley.newRequestQueue(context).add(stringRequest);
    }

}
