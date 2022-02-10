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
 * Handles all server requests for the Achievements page.
 * Currently doesn't make any requests and may be deleted if that doesn't change
 * @author Lexi
 */
public class ServerRequestAchievements {
    private CustomVolleyListenerAchievements myListener;
    private Context context;

    /**
     * Creates a new ServerRequestAchievements
     * @param c the current Context
     * @param l the current CustomVolleyListenerAchievements interface
     */
    public ServerRequestAchievements(Context c, CustomVolleyListenerAchievements l) {
        context = c;
        myListener = l;
    }
}
