package org.dvorak.cbushackidea.data;

import org.json.JSONObject;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 *
 * Object containing the current conditions of the weather, which is an object in itself containing
 * temperature, description, and code
 */

public class Item implements JSONPopulator {
    private Condition condition;

    /*
     * @return condition,
     */
    public Condition getCondition() {
        return condition;
    }

    /*
     * Instantiates condition with the information provided to it in the JSON Object data
     * @data is a JSON Object from Yahoo Weather service, which provides all of the weather information for this app
     */
    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
