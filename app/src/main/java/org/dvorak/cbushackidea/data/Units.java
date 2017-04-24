package org.dvorak.cbushackidea.data;

import org.json.JSONObject;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 *
 * Object containing the current temperature in Columbus, Ohio
 */

public class Units implements JSONPopulator {
    private String temperature;

    /*
    * @return temperature
    */
    public String getTemperature() {
        return temperature;
    }

    /*
     * Instantiates the instance variable temperature to the temperature data contained in the JSON
     * Object data
     *
     * @data is a JSON Object from Yahoo Weather Service providing all of the information for weather
     * int this app
     */
    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
