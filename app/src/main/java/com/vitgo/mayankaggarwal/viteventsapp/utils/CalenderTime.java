package com.vitgo.mayankaggarwal.viteventsapp.utils;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mayankaggarwal on 22/03/17.
 */

public class CalenderTime {

    public static void setTime(Activity activity, final TextView textView) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String _24HourTime = selectedHour + ":" + selectedMinute;
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = null;
                try {
                    _24HourDt = _24HourSDF.parse(_24HourTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textView.setText(_12HourSDF.format(_24HourDt));
//                Log.d("tagg",_12HourSDF.format(_24HourDt));
            }
        }, hour, minute, true);
        mTimePicker.show();
    }



}
