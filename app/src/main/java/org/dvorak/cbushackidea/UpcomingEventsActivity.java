package org.dvorak.cbushackidea;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Activity to handle all of the things that have to do with upcoming events
 */
public class UpcomingEventsActivity extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    /*
     * Sets up all of the necessary things to set when the upcoming events activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set default values for the bar at the top
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        // Instantiate compactCalendar and use three letter abbreviation
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        // Set all of the events in the calendar
        final ArrayList<Event> events = new ArrayList<>();
        events.add(new Event(Color.RED, 1493228071000L, "GED Classes at Columbus Metropolitan Library"));
        events.add(new Event(Color.RED, 1493400871000L, "Family Story Time at Columbus Metropolitan Library"));
        events.add(new Event(Color.RED, 1493573671000L, "Learning Lab at Columbus Metropolitan Library"));
        events.add(new Event(Color.RED, 1493919271000L, "YouMedia at Columbus Metropolitan Library"));
        events.add(new Event(Color.RED, 1494092071000L, "African American Interest Group at Columbus Metropolitan Library"));
        events.add(new Event(Color.RED, 1494351271000L, "Online Job Hunting at Columbus Metropolitan Library"));
        events.add(new Event(Color.RED, 1494956071000L, "Resumes for Job Hunters at Columbus Metropolitan Library"));
        events.add(new Event(Color.RED, 1495906471000L, "Last day of Regular Classes at Ballet Met"));
        events.add(new Event(Color.RED, 1496424871000L, "Annual Spring Performance for Ballet Met at Capitol Theater"));
        events.add(new Event(Color.RED, 1496856871000L, "Summer Dance Camps Begin at Ballet Met"));
        events.add(new Event(Color.RED, 1497548071000L, "5 Week Summer Intensive at Ballet Met"));
        events.add(new Event(Color.RED, 1495042471000L, "Wil Haygood: Special Event at the Thurber House"));
        events.add(new Event(Color.RED, 1496252071000L, "An Evening with Sydney Blumenthal at the Thurber House"));
        events.add(new Event(Color.RED, 1497202471000L, "Second Sundays: Trail of Hope at the Kelton House"));

        // Add all of the events from the list events to the calendar
        for(int i = 0; i < events.size(); i++) {
            compactCalendar.addEvent(events.get(i));
        }

        /*
         * Check if the user has clicked on a day or scrolled to another month.
         * If they scroll to another month, display the month in the actionBar at the top
         * If they click a date, check if there is an event. If there is, display it. If not,
         * say there aren't any events planned for that day
         */
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            /*
             * When a day is clicked,  check if it has an event. If yes, tell the user that event is it
             * If no, tell the user there are no events planned for that day
             */
            @Override
            public void onDayClick(Date dateClicked) {
                boolean event = false;
                Context context = getApplicationContext();

                // Loop through the events to check if the date clicked has an event
                for(int i = 0; i < events.size(); i++) {
                    Date date = new Date(events.get(i).getTimeInMillis());

                    // If the date clicked has an event, display to the user what that event is
                    if(dateClicked.getDate() == date.getDate() && dateClicked.getMonth() == date.getMonth()){
                        Toast.makeText(context, events.get(i).getData().toString(), Toast.LENGTH_SHORT).show();
                        event = true;
                        break;
                    }
                }
                // If no event was found for that day, tell the user that there is no event that day
                if(!event) {
                    Toast.makeText(context, "No Events Planned for this Day", Toast.LENGTH_SHORT).show();
                }
            }

            /*
             * When the user scrolls to another month, display the month name and year in the actionBar
             */
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}
