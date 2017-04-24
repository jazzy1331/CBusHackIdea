package org.dvorak.cbushackidea.data;

import org.json.JSONObject;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 *
 * Object for the weather condition (i.e. "Cloudy")
 * Has getters for the temperature, description, and code (which picture to pick) of the weather
 */

public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;

    /*
    * @return code (which picture to pick)
    */
    public int getCode() {
        return code;
    }

    /*
    * @return temperature
    */
    public int getTemperature() {
        return temperature;
    }

    /*
    * @return description (i.e. "Cloudy")
    */
    public String getDescription() {
        return description;
    }

    /*
     * Instantiates the instance variables based on the information in the JSON Object data
     * @data a JSON Object of weather data obtained through Yahoo with values for the instance variables
     */
    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
    }
}
