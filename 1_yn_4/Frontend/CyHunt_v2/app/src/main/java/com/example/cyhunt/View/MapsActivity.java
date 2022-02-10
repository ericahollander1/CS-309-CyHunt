package com.example.cyhunt.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.cyhunt.Presenter.MapsPresenter;
import com.example.cyhunt.R;
import com.example.cyhunt.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Handles all the Android related code inside the Maps page
 * @author Lakin & Lexi
 */
public class MapsActivity extends AppCompatActivity implements
        IMapsView,
        View.OnClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Location mCurrentLocation;
    private Marker currentUserLocationMarker;
    private Marker cyLocationMarker;
    private LatLng cyLatLng;
    private static final int Request_User_Location_Code = 99;
    private ActivityMapsBinding binding;
    private SharedPreferences sharedPreferences;
    private MapsPresenter presenter;
    private final int ACHIEVEMENT_FIRST_FOUND_CY = 2;
    private String name;
    private String description;

    /**
     * Creates the MapsActivity
     * @param savedInstanceState a savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Map Instance", "Should be created");
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CyHunt");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        sharedPreferences = getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);
        presenter = new MapsPresenter(this, getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /** sdk local properties
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    //check if user how found Cy
                    lastLocation = location;
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    checkUserLocationToCy(latLng);
                }
            });
            //retrieve location of Cy from server
            getCyLocation();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    /**
     * Generates a new location for Cy inside the server.
     */
    @Override
    public void generateNewCyLocation() {
        presenter.loadNewPlace(sharedPreferences.getInt("USER_ID", -1));
    }

    private void getCyLocation() {
        presenter.loadCurrentPlace(sharedPreferences.getInt("USER_ID", -1));
    }

    /**
     * Updates the Cy marker on the map.
     * @param lat the latitude of the location
     * @param lng the longitude of the location
     * @param name the name of the location
     * @param description the description of the location
     */
    @Override
    public void changeCyMarker(double lat, double lng, String name, String description) {
        this.name = name;
        this.description = description;

        //cyLatLng = new LatLng(42.027893, -93.630460);
        cyLatLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(cyLatLng);
        markerOptions.title("Cy is Here!");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        cyLocationMarker = mMap.addMarker(markerOptions);
    }

    /**
     * Checks if the user is close enough to Cy. Includes a score update, a possible
     * achievement unlock, and triggers a trivia question prompt
     * @param userLatLng the user's location
     */
    public void checkUserLocationToCy(LatLng userLatLng) {
        //if user is close enough to Cy
        if ((Math.abs(userLatLng.latitude - cyLatLng.latitude) <= .0001 && Math.abs(userLatLng.longitude - cyLatLng.longitude) <= .0001)) {
            if (cyLocationMarker != null) {
                cyLocationMarker.remove();
            }

            //update the user's score
            presenter.updateCyscore(sharedPreferences.getInt("USER_ID", -1));

            //generate new location for Cy
            generateNewCyLocation();

            //Handles an achievement
            if (sharedPreferences.getInt("ACHIEVEMENT_FIRST_FOUND_CY", -1) != 1) {
                presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_FIRST_FOUND_CY);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("ACHIEVEMENT_FIRST_FOUND_CY", 1);
                editor.apply();
            }

            //Create popup
            String title = "Congratulations, you found Cy at " + name + "!";
            String message = description + "\n\nYou have received 5 points for finding Cy. Please answer a trivia question for a chance to earn another 5 points.";

            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("Take me to the trivia question", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button, take user to trivia
                    Intent intent = new Intent(getApplicationContext(), TriviaActivity.class);
                    startActivity(intent);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * Checks the user's location permissions
     * @return permission granted boolean
     */
    public boolean checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Processes the results of the location permission request
     * @param requestCode the request code for the user location request
     * @param permissions the permissions
     * @param grantResults the results of the permission request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();

                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Processes a location change
     * @param location the user's location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastLocation = location;
        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.position(latLng);
        //markerOptions.title("User current location");
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        //currentUserLocationMarker = mMap.addMarker(markerOptions);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.animateCamera(CameraUpdateFactory.zoomBy(12));
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

        checkUserLocationToCy(latLng);
    }

    /**
     * The post connection process
     * @param bundle a bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval((1100));
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    /**
     * Processes a suspended connection.
     * @param i an int
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * Processes a failed connection.
     * @param connectionResult the connection result
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Processes a click
     * @param view a view
     */
    @Override
    public void onClick(View view) {

    }
}
