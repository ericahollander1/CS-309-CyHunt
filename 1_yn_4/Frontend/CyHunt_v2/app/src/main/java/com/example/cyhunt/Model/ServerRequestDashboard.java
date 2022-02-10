package com.example.cyhunt.Model;

import android.content.Context;

import com.example.cyhunt.Model.CustomVolleyListenerDashboard;

/**
 * Handles all server requests for the Dashboard page
 * @author Lexi
 */
public class ServerRequestDashboard {
    private CustomVolleyListenerDashboard myListener;
    private Context context;

    /**
     * Creates a new ServerRequestDashboard
     * @param c the current Context
     * @param l the current CustomVolleyListenerTrivia interface
     */
    public ServerRequestDashboard(Context c, CustomVolleyListenerDashboard l) {
        this.context = c;
        this.myListener = l;
    }
}
