package org.dvorak.cbushackidea.service;

import android.net.Uri;
import android.os.AsyncTask;

import org.dvorak.cbushackidea.data.Channel;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Patrick Sheeran on 4/1/2017.
 * This is a class primarily dedicated to the purpose of abtaining the needed weather information from
 * Yahoo Weather Service
 */

public class YahooWeatherService {

    private WeatherServiceCallback callback;
    private String location;
    private Exception error;

    /*
    * Constructor to instantiate the callback instance variable
    */
    public YahooWeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    /*
    * @return location (where Yahoo is getting the weather from)
    */
    public String getLocation() {
        return location;
    }

    /*
    * This methods serves to refresh the data for the weather from the JSON file on Yahoo's website
    * @l is the location passed to the refreshWeather method
    */
    public void refreshWeather(String l) {
        this.location = l;
        // Async task to get the weather information from Yahoo
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // Get weather info from Yahoo's provided JSON file
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", params[0]);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                // Try to get the info from Yahoo. If it works, store it in result
                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();

                    // If the try throws an error, set error to that error
                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            /*
           * Decides weather to run serviceFailure or serviceSuccess
           */
            @Override
            protected void onPostExecute(String s) {
                // If getting the weather from Yahoo didn't work, run serviceFailure
                if(s == null && error != null) {
                    callback.serviceFailure(error);
                    return;
                }

                // Try to decide whether to run serviceFailure or serviceSuccess
                try {
                    // Set data to the JSON file provided by Yahoo
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");

                    // If no weather info was found for a city (count == 0), run serviceFailure
                    if(count == 0) {
                        callback.serviceFailure(new LocationWeatherException("No weather information found for " + location));
                        return;
                    }

                    /*
                    * Run service success and populate the channel (which includes all necessary variables to change)
                    * if the serviceFailure hasn't been called yet
                    */
                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.serviceSuccess(channel);

                    // If an exception is thrown in the try, run serviceFailure with that error
                } catch (JSONException e) {
                    callback.serviceFailure(e);
                }
            }
        }.execute(location);
    }

    /*
    * Runs the Exception constructor for any exception in the weather passed to it
    */
    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String message) {
            super(message);
        }
    }
}
