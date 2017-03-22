package com.example.mayankaggarwal.viteventsapp.utils;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by mayankaggarwal on 22/03/17.
 */

public class DateListener implements DatePickerDialog.OnDateSetListener  {
    TextView textView;
    public DateListener(TextView textView) {
        this.textView=textView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int mYear = year;
        int mMonth = month;
        int mDay = dayOfMonth;
        String monthName=getMonthName(mMonth);

        textView.setText(new StringBuilder().append(mDay).append("-")
                .append(monthName).append("-")
                .append(mYear).append(" "));

    }

    public static String getMonthName(int month){
        switch(month+1){
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
}
