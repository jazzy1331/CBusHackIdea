package org.dvorak.cbushackidea.service;

import org.dvorak.cbushackidea.data.Channel;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 */

public interface WeatherServiceCallback {

    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
