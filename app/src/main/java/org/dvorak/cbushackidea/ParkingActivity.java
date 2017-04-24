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

    }

    public void getJson(){



    }

}
