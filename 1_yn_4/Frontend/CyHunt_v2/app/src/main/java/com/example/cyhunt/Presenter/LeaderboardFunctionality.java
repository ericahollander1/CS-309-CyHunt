package com.example.cyhunt.Presenter;

import static java.lang.Math.min;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.cyhunt.Model.*;
import com.example.cyhunt.View.LeaderboardFragment;
import com.example.cyhunt.Model.LeaderboardFunctionalityInterface;

/**
 *holds the JSON requests and logical requirements for the LeaderboadrFragment class
 */
public class LeaderboardFunctionality implements LeaderboardFunctionalityInterface {
    /**
     * A queue for the JSON requests made within this class
     */
    public RequestQueue queue;

    /**
     * create the queue with the proper contex
     * @param context context
     */
    public LeaderboardFunctionality(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    /**
     *Send a jsonArrayRequest to get the list of users as sorted by score. Returns nothing, but the
     * thread it initiates eventually calls a function in CVL to return the info to the view
     * @param CVL, the class that called this one
     * @return depreciated
     */
    public ArrayList<String> getUsersForDisplay(CustomVolleyListenerArrayList CVL)
    {
        ArrayList<String> l = new ArrayList<>();
        //String url = "https://c1f90864-8557-4f9f-9f6a-149e1b3a7acf.mock.pstmn.io/users";
        String url = "http://coms-309-020.cs.iastate.edu:8080/users/score";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json array response
                // loop through each json object
                try {
                    int length = response.length();
                    int index = 1;
                    for (int i = 0; (i < length && index < 17); i++) {
                        JSONObject person = (JSONObject) response.get(i);
                        if(!person.getString("role").equals("GUEST")) {
                            l.add(" " + (index) + ": " + person.getString("emailId") + " - " + person.getInt("cyScore") + " points");
                            index++;
                        }
                    }
                    //testFunction(allUsers, view);
                    Log.d("TAG", "SERVER RESPONSE: " + l.toString());

                }
                catch(JSONException e)
                {
                    Log.d("JSON_ERROR", e.toString());
                }
                CVL.onSuccess(l);
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CVL.onError(error);
            }
        });
        queue.add(req);
        return l;
    }

}
