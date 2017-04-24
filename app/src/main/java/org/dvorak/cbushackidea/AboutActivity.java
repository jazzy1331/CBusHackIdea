package org.dvorak.cbushackidea;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AboutActivity extends AppCompatActivity {


    //    Lifecycle method that always runs at the start of an activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //        Calls the parent method, sets the layout to be used with this class, and sets the toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    If the weather images credit is clicked by the user, then this method is called
    public void onWeatherCreditClicked(View view) {

//        Sets up the Chrome Custom Tab that will display a given URL along with specifications on how to display it
        String url = "http://vclouds.deviantart.com/art/VClouds-Weather-Icons-179152045";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        String COL = "#3F51B5";
        builder.setToolbarColor(Color.parseColor(COL));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

}
