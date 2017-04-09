package org.dvorak.cbushackidea;

/**
 * Created by JasBawa on 4/9/2017.
 */


import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;


public class Constants {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 20;

    public static final HashMap<String, LatLng> PLACES = new HashMap<String, LatLng>();

    static {
        // Metropolitan Library
        PLACES.put("Columbus Metropolitan Library", new LatLng(39.961219, -82.989510));

        // Museum of Art
        PLACES.put("Columbus Museum of Art", new LatLng(39.964385, -82.987772));

        // Test Location
        PLACES.put("MY HOUSE", new LatLng(40.127182, -83.092803));

    }
}

