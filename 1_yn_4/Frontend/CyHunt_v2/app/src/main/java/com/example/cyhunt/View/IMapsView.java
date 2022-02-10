package com.example.cyhunt.View;

/**
 * An interface for the Maps Activity. It contains the methods that are called
 *  from the Maps presenter
 * @author Lexi
 */
public interface IMapsView {
    /**
     * Generates a new location for Cy inside the server.
     */
    void generateNewCyLocation();

    /**
     * Updates the Cy marker on the map.
     * @param lat the latitude of the location
     * @param lng the longitude of the location
     * @param name the name of the location
     * @param description the description of the location
     */
    void changeCyMarker(double lat, double lng, String name, String description);
}
