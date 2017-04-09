package org.dvorak.cbushackidea;


import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.dvorak.cbushackidea.data.Channel;
import org.dvorak.cbushackidea.data.Condition;
import org.dvorak.cbushackidea.service.WeatherServiceCallback;
import org.dvorak.cbushackidea.service.YahooWeatherService;

import java.util.ArrayList;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, WeatherServiceCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {


    //    Instance Variables to be used later on with the Map Functions
    private GoogleMap mMap;
    private LatLngBounds BOUNDS = new LatLngBounds(new LatLng(39.953786, -82.994589), new LatLng(39.974247, -82.981827));
    private Marker csccMarker;
    private Marker collegeOfArtDesignMarker;
    private Marker franklinUnivMarker;
    private Marker capitalUnivLawMarker;
    private Marker museumOfArtMarker;
    private Marker metropolitanLibraryMarker;
    private Marker topiaryParkMarker;
    private Marker thurberHouseMarker;
    private Marker cristoReyHSMarker;
    private Marker keltonHouseMarker;
    private Marker grantMedicalCenterMarker;
    private Marker balletMetMarker;

    //    Instance Variables used for Dialog Box and saving data
    private final String SP_NAME = "cbushackpref";
    private Dialog mapDialog;

    //    Instance Variables used for the Weather Functionality
    private ImageView ivWeatherIcon;
    private TextView tvTemperature;
    private TextView tvLocation;
    private TextView tvCondition;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    protected ArrayList<Geofence> mGeofenceList;
    protected GoogleApiClient mGoogleApiClient;


    //    Lifecycle method that always runs at the start of an activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Calls the parent method, sets the layout to be used with this class, and sets the toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Sets up the Navigation Drawer that is used as the primary means of navigation through the app
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        Sets up the map in the background and automatically calls onMapReady() when the map is ready to be customized and displayed
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Calls the function to build the dialog box and then checks saved data if there is a need to show it or not
        onCreateDialog();
        SharedPreferences settings = getSharedPreferences(SP_NAME, 0);
        if (settings.getString("mapDialog", "F").equals("F")) {
            mapDialog.show();
        }


//        Instantiates Instance Variables used for weather
        final ConstraintLayout view = (ConstraintLayout) navigationView.getHeaderView(0);
        ivWeatherIcon = (ImageView) view.findViewById(R.id.ivWeatherIcon);
        tvTemperature = (TextView) view.findViewById(R.id.tvTemperature);
        tvCondition = (TextView) view.findViewById(R.id.tvCondition);
        tvLocation = (TextView) view.findViewById(R.id.tvLocation);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Columbus, OH");

        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();

        // Get the geofences used. Geofence data is hard coded
        populateGeofenceList();

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();

        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, "Google API Client not connected!", Toast.LENGTH_SHORT).show();
            mGoogleApiClient.connect();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
        }

    }


    //    Lifecycle method that determines what happens if the system back button is pressed by the user
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //    Creates the options menu on the right side of the toolbar and gives it options to present to the user
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //    When an option is selected, this method is called to handle what happens
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //    When a item is selected in the navigation drawer, this method is called to handle what happens
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upcoming_events) {


        } else if (id == R.id.nav_weather) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
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

        mMap.setLatLngBoundsForCameraTarget(BOUNDS);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BOUNDS.getCenter(), 15f));
        mMap.setMinZoomPreference(14f);
        mMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) this);
        createMarkers();
    }

    //    Is called when the map is ready and basic parameters of the map are set-up. This method creates all the orange markers on the map
    public void createMarkers() {

        LatLng cscc = new LatLng(39.969207, -82.987206);
        LatLng collegeOfArtDesign = new LatLng(39.965000, -82.989781);
        LatLng franklinUniv = new LatLng(39.958361, -82.990331);
        LatLng capitalUnivLaw = new LatLng(39.962738, -82.992002);
        LatLng museumOfArt = new LatLng(39.964385, -82.987772);
        LatLng metropolitanLibrary = new LatLng(39.961219, -82.989510);
        LatLng topiaryPark = new LatLng(39.961183, -82.987481);
        LatLng thurberHouse = new LatLng(39.965770, -82.985203);
        LatLng cristoReyHS = new LatLng(39.960782, -82.988985);
        LatLng keltonHouse = new LatLng(39.960795, -82.984303);
        LatLng grantMedicalCenter = new LatLng(39.960812, -82.991183);
        LatLng balletMet = new LatLng(39.969623, -82.992805);

        csccMarker = mMap.addMarker(new MarkerOptions().position(cscc).title("Columbus State Community College"));
        collegeOfArtDesignMarker = mMap.addMarker(new MarkerOptions().position(collegeOfArtDesign).title("Columbus College of Art and Design"));
        franklinUnivMarker = mMap.addMarker(new MarkerOptions().position(franklinUniv).title("Franklin University"));
        capitalUnivLawMarker = mMap.addMarker(new MarkerOptions().position(capitalUnivLaw).title("Capital University Law School"));
        museumOfArtMarker = mMap.addMarker(new MarkerOptions().position(museumOfArt).title("Columbus Museum of Art"));
        metropolitanLibraryMarker = mMap.addMarker(new MarkerOptions().position(metropolitanLibrary).title("Columbus Metropolitan Library"));
        topiaryParkMarker = mMap.addMarker(new MarkerOptions().position(topiaryPark).title("Topiary Park"));
        thurberHouseMarker = mMap.addMarker(new MarkerOptions().position(thurberHouse).title("Thurber Hosue"));
        cristoReyHSMarker = mMap.addMarker(new MarkerOptions().position(cristoReyHS).title("Cristo Rey Columbus High School"));
        keltonHouseMarker = mMap.addMarker(new MarkerOptions().position(keltonHouse).title("Kelton House Museum and Garden"));
        grantMedicalCenterMarker = mMap.addMarker(new MarkerOptions().position(grantMedicalCenter).title("Grant Medical Center"));
        balletMetMarker = mMap.addMarker(new MarkerOptions().position(balletMet).title("Ballet Met"));
    }

    //    When a marker is selected by the user, this function is called to handle it and do something with it
    @Override
    public void onInfoWindowClick(Marker marker) {

        String url = "";
        if (marker.equals(csccMarker)) {
            url = "http://www.cscc.edu";
        } else if (marker.equals(collegeOfArtDesignMarker)) {
            url = "http://www.ccad.edu";
        } else if (marker.equals(franklinUnivMarker)) {
            url = "http://www.franklin.edu";
        } else if (marker.equals(capitalUnivLawMarker)) {
            url = "http://law.capital.edu";
        } else if (marker.equals(museumOfArtMarker)) {
            url = "https://www.columbusmuseum.org";
        } else if (marker.equals(metropolitanLibraryMarker)) {
            url = "http://www.columbuslibrary.org";
        } else if (marker.equals(topiaryParkMarker)) {
            url = "http://www.topiarypark.org";
        } else if (marker.equals(thurberHouseMarker)) {
            url = "http://www.thurberhouse.org/";
        } else if (marker.equals(cristoReyHSMarker)) {
            url = "http://www.cristoreycolumbus.org/";
        } else if (marker.equals(keltonHouseMarker)) {
            url = "http://keltonhouse.com/";
        } else if (marker.equals(grantMedicalCenterMarker)) {
            url = "https://www.ohiohealth.com/locations/hospitals-and-emergency-departments/grant-medical-center";
        } else if (marker.equals(balletMetMarker)) {
            url = "https://www.balletmet.org/";
        }

//        Sets up the Chrome Custom Tab that will display a given URL along with specifications on how to display it
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        String COL = "#3F51B5";
        builder.setToolbarColor(Color.parseColor(COL));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    //    Builds the fundamentals of the Map Help Dialog Box at the start the activity
    public void onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle("Map Help");
        builder.setView(inflater.inflate(R.layout.activity_map_dialog, null));
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        mapDialog = builder.create();
    }

    //    When the checkbox on the Dialog Box is clicked, this method is called automatically to change the saved data
    public void onCheckboxClicked(View view) {

        CheckBox cb1 = (CheckBox) view;
        SharedPreferences settings = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        String checkBoxResult = "T";
        if (!cb1.isChecked()) {
            checkBoxResult = "F";
        }
        editor.putString("mapDialog", checkBoxResult);
        editor.apply();
    }

    //    Patrick, ADD COMMENTS TO UR THINGS CAUSE I NO UNDERSTAND
    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Condition condition = channel.getItem().getCondition();
        int resourceId = getResources().getIdentifier("icon_" + condition.getCode(), "drawable", getPackageName());

        ivWeatherIcon.setImageResource(resourceId);

        tvLocation.setText(service.getLocation());

        tvTemperature.setText(condition.getTemperature() + "\u00B0" + channel.getUnits().getTemperature());

        tvCondition.setText(condition.getDescription());

    }

    //    Patrick, ADD COMMENTS TO UR THINGS CAUSE I NO UNDERSTAND
    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Main Activity", "Connected");
        Toast.makeText(this, "Google API Client is connected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        Log.d("Main Activity", "Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient.connect();
        Log.d("Main Activity", "Failed");
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            Toast.makeText(
                    this,
                    "Geofences Added",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = "IDK I DELETED THE CODE";
        }

    }

    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : Constants.PLACES.entrySet()) {
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
                    .setLoiteringDelay(10)
                    .build());
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}


