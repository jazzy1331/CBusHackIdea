package org.dvorak.cbushackidea;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

public class ParkingActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLngBounds BOUNDS = new LatLngBounds(new LatLng(32.70649, -117.16498), new LatLng(32.71884, -117.14781));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
//        Creates a map around the area of San Diego that has simulated data from GE
        mMap.setLatLngBoundsForCameraTarget(BOUNDS);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BOUNDS.getCenter(), 15f));
        mMap.setMinZoomPreference(14f);
        createMarkers();
    }

    //    Is called when the map is ready and basic parameters of the map are set-up. This method creates all the orange markers on the map
    public void createMarkers() {

        LatLng test1 = new LatLng(32.71263, -117.16116);
        Marker test1Marker = mMap.addMarker(new MarkerOptions().position(test1).title("Open Parking"));
        LatLng test2 = new LatLng(32.7105, -117.15927);
        Marker test2Marker = mMap.addMarker(new MarkerOptions().position(test2).title("Possibly Taken"));
        LatLng test3 = new LatLng(32.71479, -117.15661);
        Marker test3Marker = mMap.addMarker(new MarkerOptions().position(test3).title("No Parking Available"));

        getInfo();

    }

//    Will contact GE's servers and get the simulated data and add it to the map
    public void getInfo(){

//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new httpGet("https://8553482c-1d32-4d38-8597-2e56ab642dd3.predixuaa.run.asv-pr.ice.predix.io");
//        //Need to put in Headers
//        //Getting simulated data is not finished. Made up data was added on to the map to show how it would act


    }

}
