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
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyhunt.Model.CustomVolleyListenerLogin;
import com.example.cyhunt.Model.LoginFuntionalityInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *holds the JSON requests and logical requirements for the LoginFunctionality class
 */
public class LoginFunctionality implements LoginFuntionalityInterface {
    /**
     * A queue for the JSON requests made within this class
     */
    public RequestQueue queue;

    /**
     * create queue with proper context
     * @param context contect of the class which called this one
     */
    public LoginFunctionality(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    /**
     * extra constructor for testing
     */
    public LoginFunctionality(){};

    @Override
    /**
     * This function makes a call to the database to learn if the user's password and username
     * corresponds to a real user. The thread created by this function calls a class in CVL when
     * it has an answer from the server, either an ID or a fialure message.
     * @param CVL CVL, the class that called this one
     * @param emailId username provided by user
     * @param password password provided by user
     */
    public void checkIfLoginValid(CustomVolleyListenerLogin CVL, String emailId, String password) {
        //String url = "https://c1f90864-8557-4f9f-9f6a-149e1b3a7acf.mock.pstmn.io/users";
        JSONObject postData = new JSONObject();
        try {
            postData.put("emailId", emailId);
            postData.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = postData.toString();
        String url = "http://coms-309-020.cs.iastate.edu:8080/login";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("USERLOGINRESPONSE", response);
                    CVL.onSuccess(response);
                } catch (Exception e) {
                    Log.d("JSON_ERROR", "Whoops lol that didn't work.");
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


    /**
     * Before calling the database, check the strings given by the user for proper length. This
     * will avoid unneccecary accidental calls to the database by users that haven't finished
     * logging in.
     * @param p password provided by user
     * @param u username provided by user
     * @return if the user has put in a valid username and password
     */
    public boolean checkIfUserCredentialsExist(String p, String u) {
        Log.d("EXIST?", p + p.length());
        Log.d("EXIST?", u + u.length());
        if (p.length() != 0 && u.length() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Once the user's ID has been retrieved, this one takes the ID and retrieves the user's info
     * from the database
     * @param CVL CVL, the class that called this one
     * @param userId the user's ID
     */
    public void getUserDataById(CustomVolleyListenerLogin CVL, String userId) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/users/" + userId;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    CVL.onSuccessStudentFound(response.getInt("id"), response.getString("role"), response.getInt("cyScore"), response.getString("emailId"));
                } catch (JSONException e) {
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

    /**
     * create a new guest user
     * @param CVL CVL, the class that called this one
     */
    @Override
    public void signInAsGuest(CustomVolleyListenerLogin CVL)
    {
        JSONObject postData = new JSONObject();
        try {
            postData.put("role", "GUEST");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = postData.toString();
        String url = "http://coms-309-020.cs.iastate.edu:8080/users";
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("JSONRESPONSE", response);
                    CVL.onGuestCreated(response);
                } catch (Exception e) {
                    Log.d("JSON_ERROR", "Incorrect field requested");
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
}


