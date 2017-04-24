package org.dvorak.cbushackidea.data;

import org.json.JSONObject;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 *
 * Object containing values for the units for each piece of weather data and the item,
 * which is essentially just the place that it is looking at at a particular time.
 */


public class Channel implements JSONPopulator {
    private Units units;
    private Item item;

    /*
     * @return units, which contains the units for each piece of weather data
     */
    public Units getUnits() {
        return units;
    }

    /*
     * @return item, which is the place that the Yahoo Weather service is looking at
     */
    public Item getItem() {
        return item;
    }

    /*
    * Instantiates the instance variables to values that are in the JSON Object data
    * @data is a JSON Object from Yahoo Weather service containing all of the weather information for this app
    */
    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));
    }
}
