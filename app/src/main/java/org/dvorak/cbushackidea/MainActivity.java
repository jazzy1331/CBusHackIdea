package org.dvorak.cbushackidea;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import org.dvorak.cbushackidea.data.Item;
import org.dvorak.cbushackidea.service.WeatherServiceCallback;
import org.dvorak.cbushackidea.service.YahooWeatherService;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, WeatherServiceCallback {

//    Instance Variables to be used later on with the Map Functions

    private GoogleMap mMap;
    private LatLngBounds BOUNDS = new LatLngBounds(new LatLng(39.953786, -82.994589), new LatLng(39.974247, -82.981827));

    private ImageView ivWeatherIcon;
    private TextView tvTemperature;
    private TextView tvLocation;
    private TextView tvCondition;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    private LatLng cscc;
    private LatLng collegeOfArtDesign;
    private LatLng franklinUniv;
    private LatLng capitalUnivLaw;
    private LatLng museumOfArt;
    private LatLng metropolitanLibrary;
    private LatLng topiaryPark;
    private LatLng thurberHouse;
    private LatLng cristoReyHS;
    private LatLng keltonHouse;
    private LatLng grantMedicalCenter;
    private LatLng balletMet;
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
    private final String COL = "#3F51B5";

    private final String SP_NAME = "cbushackpref";
    private Dialog mapDialog;
    private boolean isMapCheckboxChecked = false;


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


        onCreateDialog();

        SharedPreferences settings = getSharedPreferences(SP_NAME, 0);
        if (settings.getString("mapDialog", "F").equals("F")) {
            mapDialog.show();
        }

        ivWeatherIcon = (ImageView)findViewById(R.id.ivWeatherIcon);
        tvTemperature = (TextView)findViewById(R.id.tvTemperature);
        tvCondition = (TextView)findViewById(R.id.tvCondition);
        tvLocation = (TextView)findViewById(R.id.tvLocation);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Columbus, OH");

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

//        MapHelperActivity mapHelperActivity = new MapHelperActivity(googleMap);
//        mapHelperActivity.beginFunctions();

    }

    //    Is called when the map is ready and basic parameters of the map are set-up. This method creates all the orange markers on the map
    public void createMarkers() {

        cscc = new LatLng(39.969207, -82.987206);
        collegeOfArtDesign = new LatLng(39.965000, -82.989781);
        franklinUniv = new LatLng(39.958361, -82.990331);
        capitalUnivLaw = new LatLng(39.962738, -82.992002);
        museumOfArt = new LatLng(39.964385, -82.987772);
        metropolitanLibrary = new LatLng(39.961219, -82.989510);
        topiaryPark = new LatLng(39.961183, -82.987481);
        thurberHouse = new LatLng(39.965770, -82.985203);
        cristoReyHS = new LatLng(39.960782, -82.988985);
        keltonHouse = new LatLng(39.960795, -82.984303);
        grantMedicalCenter = new LatLng(39.960812, -82.991183);
        balletMet = new LatLng(39.969623, -82.992805);

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

    //    When a marker is selected by the user, this function is called to handle it and do something(or not) with it
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

//        Sets up the Chrome Custom Tab that will display a given URL with specifications on how to display it
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor(COL));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }


    public void onCreateDialog() {

        final CheckBox cb1 = (CheckBox) findViewById(R.id.map_checkbox);


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

    public void onCheckboxClicked(View view){

        CheckBox cb1 = (CheckBox) view;
        SharedPreferences settings = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        String checkBoxResult = "T";
        if (cb1.isChecked() == false) {
            checkBoxResult = "F";
        }
        editor.putString("mapDialog", checkBoxResult);
        editor.commit();


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

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(),  Toast.LENGTH_LONG).show();

    }
}
