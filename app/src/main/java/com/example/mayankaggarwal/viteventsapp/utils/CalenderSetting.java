package com.example.mayankaggarwal.viteventsapp.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mayankaggarwal on 22/03/17.
 */

public class CalenderSetting {

    public static void setCalendar(Activity activity, TextView textView) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(activity,
                new DateListener(textView), mYear, mMonth, mDay);
        dialog.show();
    }
}
