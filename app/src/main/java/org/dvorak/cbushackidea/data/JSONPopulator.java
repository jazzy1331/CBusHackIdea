package org.dvorak.cbushackidea.data;

import org.json.JSONObject;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 *
 * Interface containing the method that all other classes in this package use: populate
 *
 * @populate is a  method that will take the data from the JSON Object data and use it to instantiate
 * each class's instance variables
 *
 * @data is a JSON Object from Yahoo Weather service containing all of the weather information used in
 * this app
 */

public interface JSONPopulator {

    void populate(JSONObject data);
}
