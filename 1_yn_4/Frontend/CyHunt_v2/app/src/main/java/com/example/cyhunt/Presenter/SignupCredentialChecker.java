package com.example.cyhunt.Presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyhunt.Model.CustomVolleyListenerSignup;
import com.example.cyhunt.Model.SignupCredentialCheckerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 *holds the JSON requests and logical requirements for the SignupFragment class
 */
public class SignupCredentialChecker implements SignupCredentialCheckerInterface {

    /**
     * A queue for the JSON requests made within this class
     */
    public RequestQueue queue;

    /**
     * create the queue with the proper contex
     * @param context context
     */
    public SignupCredentialChecker(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    /**
     * extra constructor for testing
     */
    public SignupCredentialChecker(){}

    /**
     * Check if the password meets some set requirements of security
     * @param pass the user's inputted password
     * @param repeatPass the user's inputted repeatPassword
     * @return a message that says either 'valid' or the list of things the user has to fix
     */
    @Override
    public String checkPasswordRequirements(String pass, String repeatPass)
    {
        String message = "";
        boolean correctLength  = true;
        boolean containsSpecial = false;
        boolean containsNumber = false;
        boolean matches = true;
        char[] passCharacters = new char[pass.length()];
        passCharacters = pass.toCharArray();
        if(pass.length() < 8)
        {
            correctLength = false;
            message += "Password is not at least 8 characters long\n";
        }

        for(int i = 0; i < passCharacters.length; i++)
        {
            if(!containsNumber)
            {
                if(Character.isDigit(passCharacters[i])) {
                    containsNumber = true;
                }
            }
            if (!containsSpecial) {
                if(!Character.isDigit(passCharacters[i]) && !Character.isAlphabetic(passCharacters[i]) && !Character.isWhitespace(passCharacters[i])) {
                    containsSpecial = true;
                }
            }
        }
        if(!containsNumber)
        {
            message += ("Password must contain at least one number\n");
        }
        if(!containsSpecial)
        {
            message += ("Password must contain at least one special character\n");
        }

        if(!pass.equals(repeatPass) || pass.equals(""))
        {
            matches = false;
            message += "Password and repeat password fields must match\n";
        }

        if(correctLength && containsSpecial && containsNumber && matches) {
            message = "valid";
        }
        return message;
    }

    @Override
    /**
     * Check if the names the user put in exist
     * @param fn first name
     * @param ln last name
     * @param en email username
     * @return a message that says either 'valid' or lists the problems the user needs to fix
     */
    public String checkNameRequirements (String fn, String ln, String en)
    {
        String message = "";
        Log.d("EXIST?", fn + fn.length());
        Log.d("EXIST?", ln + ln.length());
        Log.d("EXIST?", en + en.length());
        if(fn.length() < 1)
        {
            message += ("First name cannot be empty!\n");
        }
        if(ln.length() < 1)
        {
            message += ("Last name cannot be empty!\n");
        }
        if(en.length() < 1)
        {
            message += ("NetID cannot be empty!\n");
        }
        if(message.length() < 2)
        {
            message = "valid";
        }

        return message;
    }
    /**
     * If the user's names and password meets requirements, run the info by the database to either add
     * them or reject them. Calls a method in CVL with either a failure (user already exists) or success
     * message
     * @param fn user's first name
     * @param ln user's last name
     * @param en user's email
     * @param pass user's password
     * @param CVL CVL, the class that called this one
     */
    @Override
    public void addNewUserIfPossible(String fn, String ln, String en, String pass, CustomVolleyListenerSignup CVL)
    {
        JSONObject postData = new JSONObject();
        try {
            postData.put("first_name", fn);
            postData.put("last_name", ln);
            postData.put("emailId", en);
            postData.put("password", pass);
            postData.put("role", "USER");

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
                    CVL.onNewUserCreated(response);
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

    @Override
    /**
     * Once the user has been successfully created, call this to re-retireve their new info from the
     * database
     * @param CVL CVL
     * @param userId the user's ID
     */
    public void getUserDataById(CustomVolleyListenerSignup CVL, String userId) {
        String url = "http://coms-309-020.cs.iastate.edu:8080/user/email/" + userId;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    CVL.onNewUserDataRetrieved(response.getInt("id"), response.getString("role"), response.getInt("cyScore"), response.getString("emailId"));
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
}
