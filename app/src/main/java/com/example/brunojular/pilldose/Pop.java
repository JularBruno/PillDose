package com.example.brunojular.pilldose;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TimePicker;

public class Pop extends Activity {
    private TimePicker timePicker1;
    private Calendar calendar;
    private String format = "";
    private String time = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        calendar = Calendar.getInstance();
        timePicker1.setIs24HourView(true);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9),(int) (height*.84));
    }

    public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time = hour + " : " + min;

        final Intent intnt2 = new Intent(this, BtActivity.class);

        intnt2.putExtra("tiempo", time);
        startActivity(intnt2);

    }



}

