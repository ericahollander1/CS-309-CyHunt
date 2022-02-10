package com.example.cyhunt.Presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.CustomVolleyListenerAchievements;
import com.example.cyhunt.Model.ServerRequestAchievements;
import com.example.cyhunt.View.IAchievementsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles the logic involved in the Achievements page and sends calls
 * to the server.
 * Currently doesn't have any logic to handle, so it may be deleted if that doesn't change.
 * @author Lexi
 */
public class AchievementsPresenter implements CustomVolleyListenerAchievements {
    private ServerRequestAchievements model;
    private IAchievementsView view;
    private Context context;

    /**
     * Creates a new AchievementsPresenter
     * @param view the current view
     * @param c the current context
     */
    public AchievementsPresenter(IAchievementsView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new ServerRequestAchievements(context, this);
    }

    /**
     * Set the model for the AchievementsPresenter
     * @param m the model
     */
    public void setModel(ServerRequestAchievements m) {
        this.model = m;
    }
}
