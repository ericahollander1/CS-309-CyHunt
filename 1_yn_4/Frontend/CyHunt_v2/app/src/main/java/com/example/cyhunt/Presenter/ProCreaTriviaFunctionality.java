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
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyhunt.Model.CustomVolleyListenerProCreaTrivia;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ProCreaTriviaFunctionality {
    /**
     * A queue for the JSON requests made within this class
     */
    public RequestQueue queue;
    private SharedPreferences sharedPreferences;
    /**
     * create the queue with the proper contex
     * @param context context
     */
    public ProCreaTriviaFunctionality(Context context)
    {
        queue = Volley.newRequestQueue(context);
        sharedPreferences = context.getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);
    }
    public ProCreaTriviaFunctionality()
    {
    }

    public void submitNewTrivia(String question, String realAns, String da1, String da2, String da3, boolean intendToCreate, CustomVolleyListenerProCreaTrivia CVL)
    {
        JSONObject postData = new JSONObject();
        try {
            postData.put("question", question);
            postData.put("answer", realAns);
            postData.put("answers", new JSONArray(new Object[] { da1, da2, da3} ));
            postData.put("approved", intendToCreate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = postData.toString();
        String url = "http://coms-309-020.cs.iastate.edu:8080/trivia";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("JSONRESPONSE", response  + " " + response.getClass().getName());
                    CVL.onSuccess((response.toString() + ""));
                } catch (Exception e) {
                    Log.d("JSON_ERROR", e.toString());
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.toString());
                CVL.onError(error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody(){
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
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

    public void attachAuthorToTrivia(int triviaID, CustomVolleyListenerProCreaTrivia CVL)
    {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        int userID = sharedPreferences.getInt("USER_ID", -1);
        String url = ("http://coms-309-020.cs.iastate.edu:8080/trivia/" + triviaID + "/user/" + userID);
        Log.d("IDS", userID + " " + triviaID);
        StringRequest req = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("JSONRESPONSE", response);
                    CVL.onSuccessComplete(response);
                } catch (Exception e) {
                    Log.d("JSON_ERROR", "Author failed to add " + response);
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", error.toString());
                CVL.onError(error);
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

    public String simpleTriviaCheck(String question, String realAns, String da1, String da2, String da3)
    {
        if(question.length() == 0 || realAns.length() == 0 || da1.length() == 0 || da2.length() == 0 || da3.length() == 0)
        {
            return "No field can be blank! \nPlease fill in all fields to submit.";
        }
        return "Valid";
    }
}
