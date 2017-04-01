package org.dvorak.cbushackidea.data;

import org.json.JSONObject;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 */

public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
