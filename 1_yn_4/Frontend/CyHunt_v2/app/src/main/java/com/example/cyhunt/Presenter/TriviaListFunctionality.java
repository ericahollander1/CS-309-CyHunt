package com.example.cyhunt.Presenter;

import static java.lang.Math.min;

import android.content.Context;
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
import com.example.cyhunt.Model.CustomVolleyListenerApprovalList;
import com.example.cyhunt.triviaData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TriviaListFunctionality {
    public RequestQueue queue;
    public TriviaListFunctionality(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    public ArrayList<triviaData> getTriviaQuestions(CustomVolleyListenerApprovalList CVL)
    {
        ArrayList<triviaData> t = new ArrayList();
        String url = "http://coms-309-020.cs.iastate.edu:8080/pending/trivia";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json array response
                // loop through each json object
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject trivia = (JSONObject) response.get(i);
                        triviaData temp = new triviaData();
                        temp.ID = trivia.getInt("id");
                        String tempQ = trivia.getString("question");
                        if(tempQ.length() > 30)
                        {
                            tempQ = tempQ.substring(0, 27) + "...";
                        }
                        temp.question = tempQ;
                        t.add(temp);
                    }
                    //testFunction(allUsers, view);
                    //Log.d("TAG", "SERVER RESPONSE: " + l.toString());

                }
                catch(JSONException e)
                {
                    Log.d("JSON_ERROR", "Whoops lol that didn't work.");
                }
                CVL.onSuccess(t);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CVL.onError(error);
            }
        });
        queue.add(req);
        return t;
    }

    public void getAuthor(CustomVolleyListenerApprovalList CVL, int index, int ID)
    {
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia/" + ID + "/author";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Parsing json array response
                // loop through each json object
                try {
                    CVL.onAuthorRetrieved(response.getString("emailId"), index);

                }
                catch(JSONException e)
                {
                    Log.d("JSON_ERROR", "Whoops lol that didn't work.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CVL.onError(error);
            }
        });
        queue.add(req);
    }
}
