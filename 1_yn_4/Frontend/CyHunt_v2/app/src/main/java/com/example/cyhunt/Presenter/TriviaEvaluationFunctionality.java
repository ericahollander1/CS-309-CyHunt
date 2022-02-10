package com.example.cyhunt.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyhunt.Model.CustomVolleyListenerEvaluateTrivia;
import com.example.cyhunt.triviaData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class TriviaEvaluationFunctionality {
    /**
     * A queue for the JSON requests made within this class
     */
    public RequestQueue queue;
    private SharedPreferences sharedPreferences;
    /**
     * create the queue with the proper contex
     * @param context context
     */
    public TriviaEvaluationFunctionality(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    public void getTrivia(CustomVolleyListenerEvaluateTrivia CVL, int ID)
    {
        triviaData t = new triviaData();
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia/" + ID;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Parsing json array response
                // loop through each json object
                try {
                    t.ID = ID;
                    t.answer = response.getString("answer");
                    t.question = response.getString("question");
                    t.decoy1 = response.getJSONArray("answers").get(0).toString();
                    t.decoy2 = response.getJSONArray("answers").get(1).toString();
                    t.decoy3 = response.getJSONArray("answers").get(2).toString();
                    //t.author = response.getString("author");
                }
                catch(JSONException e)
                {
                    Log.d("JSON_ERROR", e.toString());
                }
                CVL.onTriviaRetrieved(t);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CVL.onError(error);
            }
        });
        queue.add(req);
    }

    public void rejectTrivia(CustomVolleyListenerEvaluateTrivia CVL, int ID)
    {
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia/" + ID;
        StringRequest req = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    CVL.onEvaluatioComplete("Trivia deleted! Better luck next time.");
                } catch (Exception e) {
                    Log.d("JSON_ERROR", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String statusCode = String.valueOf(response.statusCode);
                //Handling logic
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(req);
    }

    public void approveTrivia(CustomVolleyListenerEvaluateTrivia CVL, int ID)
    {
        String url = "http://coms-309-020.cs.iastate.edu:8080/approve/trivia/" + ID;
        StringRequest req = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    CVL.onEvaluatioComplete("Trivia approved and sent to trivia pool!");
                } catch (Exception e) {
                    Log.d("JSON_ERROR", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String statusCode = String.valueOf(response.statusCode);
                //Handling logic
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(req);
    }
}
