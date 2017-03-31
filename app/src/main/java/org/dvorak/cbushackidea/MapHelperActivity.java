package org.dvorak.cbushackidea;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by JasBawa on 3/30/2017.
 */

public class MapHelperActivity {

    private GoogleMap mMap;
    private LatLngBounds BOUNDS = new LatLngBounds(new LatLng(39.953786, -82.994589), new LatLng(39.974247, -82.981827));

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

    public MapHelperActivity(GoogleMap gMap) {
        mMap = gMap;

        mMap.setLatLngBoundsForCameraTarget(BOUNDS);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BOUNDS.getCenter(), 15f));
        mMap.setMinZoomPreference(14f);
    }


    public void beginFunctions() {
        createMarkers();
    }

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

        mMap.addMarker(new MarkerOptions().position(cscc).title("Columbus State Community College"));
        mMap.addMarker(new MarkerOptions().position(collegeOfArtDesign).title("Columbus College of Art and Design"));
        mMap.addMarker(new MarkerOptions().position(franklinUniv).title("Franklin University"));
        mMap.addMarker(new MarkerOptions().position(capitalUnivLaw).title("Capital University Law School"));
        mMap.addMarker(new MarkerOptions().position(museumOfArt).title("Columbus Museum of Art"));
        mMap.addMarker(new MarkerOptions().position(metropolitanLibrary).title("Columbus Metropolitan Library"));
        mMap.addMarker(new MarkerOptions().position(topiaryPark).title("Topiary Park"));
        mMap.addMarker(new MarkerOptions().position(thurberHouse).title("Thurber Hosue"));
        mMap.addMarker(new MarkerOptions().position(cristoReyHS).title("Cristo Rey Columbus High School"));
        mMap.addMarker(new MarkerOptions().position(keltonHouse).title("Kelton House Museum and Garden"));
        mMap.addMarker(new MarkerOptions().position(grantMedicalCenter).title("Grant Medical Center"));
        mMap.addMarker(new MarkerOptions().position(balletMet).title("Ballet Met"));


    }

}
