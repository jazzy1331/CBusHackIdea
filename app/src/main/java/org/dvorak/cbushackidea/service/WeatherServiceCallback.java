package org.dvorak.cbushackidea.service;

import org.dvorak.cbushackidea.data.Channel;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 *
 * Interface for the two weather-related methods in MainActivity: serviceSuccess and serviceFailure
 *
 * @serviceSuccess sets all of the necessary image and text views to display the weather if the weather was able to be retrieved from Yahoo
 *
 * @serviceFailure brings up a message saying that there isn't any weather information available for that location
 * if none can be received from Yahoo for whatever reason
 */

public interface WeatherServiceCallback {

    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
