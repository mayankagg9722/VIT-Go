package com.vitgo.mayankaggarwal.viteventsapp.utils;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mayankaggarwal on 22/03/17.
 */

public class DateListener implements DatePickerDialog.OnDateSetListener {
    TextView textView;

    public DateListener(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int mYear = year;
        int mMonth = month;
        int mDay = dayOfMonth;

        String monthName = getMonthName(mMonth);


        Globals.dayName=getDayName(mYear,mMonth+1,mDay);

        textView.setText(new StringBuilder().append(mDay).append("-")
                .append(monthName).append("-")
                .append(mYear).append(" "));

    }

    public static String getMonthName(int month) {
        switch (month + 1) {
            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "Jun";

            case 7:
                return "Jul";

            case 8:
                return "Aug";

            case 9:
                return "Sep";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";
        }

        return "";
    }

    public static String getDayName(int year, int month, int dayOfMonth) {
        String dateString = String.format("%d-%d-%d", year, month, dayOfMonth);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            Log.d("tag","date:"+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        return dayOfWeek;
    }
}
