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

public class UpcomingEventsActivity extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);


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

        for(int i = 0; i < events.size(); i++) {
            compactCalendar.addEvent(events.get(i));
        }

        //GED Classes at the Metro Library Event
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                boolean event = false;
                Context context = getApplicationContext();
                for(int i = 0; i < events.size(); i++) {
                    Date date = new Date(events.get(i).getTimeInMillis());
                    if(dateClicked.getDate() == date.getDate() && dateClicked.getMonth() == date.getMonth()){
                        Toast.makeText(context, events.get(i).getData().toString(), Toast.LENGTH_SHORT).show();
                        event = true;
                        break;
                    }
                }
                if(!event) {
                    Toast.makeText(context, "No Events Planned for this Day", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}
