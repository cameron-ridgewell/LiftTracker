package com.lifttracker.elements;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifttracker.R;

import mehdi.sakout.fancybuttons.FancyButton;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created by cameronridgewell on 7/13/16.
 */
public class TimerButton extends FancyButton {
    private DateTime startTime;
    private Chronometer chronometer;
    private String startText = "RESET";

    public TimerButton(Context context) {
        super(context);
        setupButtonText(context);
    }

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupButtonText(context);
    }

    private void setupButtonText(Context context)
    {
        setCustomTextFont(getResources().getString(R.string.button_font_directory));
        startTime = DateTime.now();

        Chronometer chron = new Chronometer(context);
        chron.setVisibility(GONE);
        chron.setBase(DateTime.now().getMillis());
        chron.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                Period timer = new Period(startTime, DateTime.now());
                String seconds = "0" + (timer.getSeconds() % 60);
                if (seconds.length() > 2)
                {
                    seconds = seconds.substring(1);
                }
                setText(startText, timer.getMinutes() + ":" + seconds);
            }
        });
        chron.start();
        addView(chron);
        setMinimumWidth(650);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTime();

            }
        });
    }

    public void setTime(DateTime dateTime)
    {
        this.startTime = dateTime;
    }

    public void resetTime()
    {
        this.startTime = DateTime.now();
        this.setText(startText, "0:00");
    }

    @Override
    public void setText(String text) {
        startText = text;
    }

    private void setText(String string, String time)
    {
        super.setText(string + " | " + time);
    }
}
