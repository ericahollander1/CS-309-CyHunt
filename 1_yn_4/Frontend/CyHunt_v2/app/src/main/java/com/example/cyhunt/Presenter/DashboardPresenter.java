package com.example.cyhunt.Presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.CustomVolleyListenerDashboard;
import com.example.cyhunt.Model.ServerRequestDashboard;
import com.example.cyhunt.View.IDashboardView;

import org.json.JSONArray;

/**
 * Handles the logic involved in the Dashboard page and sends calls
 * to the server.
 * @author Lexi
 */
public class DashboardPresenter implements CustomVolleyListenerDashboard {
    private ServerRequestDashboard model;
    private IDashboardView view;
    private Context context;

    /**
     * Creates a new DashboardPresenter
     * @param view the current view
     * @param c the current context
     */
    public DashboardPresenter(IDashboardView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new ServerRequestDashboard(c, this);
    }

    /**
     * Set the model for the DashboardPresenter
     * @param m the model
     */
    public void setModel(ServerRequestDashboard m) {
        this.model = m;
    }

    /**
     * Handles the successful completion of a JSONArray request
     * Will be updated later
     * @param result the JSONArray returned by the server
     */
    @Override
    public void onSuccess(JSONArray result) {

    }

    /**
     * Handles the successful completion of a string request.
     * Will be updated later
     * @param result the string returned by the server
     */
    @Override
    public void onSuccess(String result) {

    }

    /**
     * Handles the unsuccessful attempt at a request.
     * Currently empty, will eventually be handled
     * @param error the VolleyError returned by the failed request
     */
    @Override
    public void onError(VolleyError error) {

    }
}
