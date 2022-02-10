package com.example.cyhunt.Presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.CustomVolleyListenerMaps;
import com.example.cyhunt.Model.ServerRequestMaps;
import com.example.cyhunt.View.IMapsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles the logic involved in the Maps page and sends calls
 * to the server.
 * @author Lexi
 */
public class MapsPresenter implements CustomVolleyListenerMaps {
    private ServerRequestMaps model;
    private IMapsView view;
    private Context context;

    /**
     * Creates a new MainPresenter
     * @param view the current view
     * @param c the current context
     */
    public MapsPresenter(IMapsView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new ServerRequestMaps(c, this);
    }

    /**
     * Sets the model for the MapsPresenter
     * @param m the model
     */
    public void setModel(ServerRequestMaps m) {
        this.model = m;
    }

    /**
     * Calls a method from the model to update the user's score inside the server
     * @param i the current user ID
     */
    public void updateCyscore(int i) {
        model.putCyscore(i);
    }

    /**
     * Calls a method from the model to mark an achievement as completed in the server
     * for the current user
     * @param i the current user ID
     * @param j the achievement ID
     */
    public void updateAchievements(int i, int j) {
        model.putAchievement(i, j);
    }

    /**
     * Calls a method from the model to retrieve the current location of Cy from the server
     * @param i the current user ID
     */
    public void loadCurrentPlace(int i) {
        model.getCurrentPlace(i);
    }

    /**
     * Calls a method from the model to generate a new location for Cy
     * @param i the current user ID
     */
    public void loadNewPlace(int i) {
        model.getNewPlace(i);
    }

    /**
     * Handles the successful completion of a JSON object request for the current location of Cy
     * @param result the JSONObject returned by the server containing the current location of Cy
     */
    @Override
    public void onSuccessLoadPlace(JSONObject result) {
        double lat;
        double lng;
        String name;
        String description;
        try {
            if (result.getString("name") == "null") {
                view.generateNewCyLocation();
            } else {
                lat = result.getDouble("latitude");
                lng = result.getDouble("longitude");
                name = result.getString("name");
                description = result.getString("description");
                view.changeCyMarker(lat, lng, name, description);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the successful completion of a PUT request to update the user's achievements
     * @param result the String returned by the server
     */
    @Override
    public void onSuccessUpdateAchievement(String result) {
        Toast.makeText(context, "Achievement unlocked!", Toast.LENGTH_SHORT).show();
        Log.d("SUCCESS", "Achievements updated.");
    }

    /**
     * Handles the successful completion of a PUT request to update the user's score
     * @param result the string returned by the server
     */
    @Override
    public void onSuccessUpdateCyscore(String result) {
        Log.d("SUCCESS", "Score updated.");
    }

    /**
     * Handles the unsuccessful attempt at a request.
     * @param error the VolleyError returned by the failed request
     */
    @Override
    public void onError(VolleyError error) {
        Log.d("ERROR", "Error occurred ", error);
    }
}
