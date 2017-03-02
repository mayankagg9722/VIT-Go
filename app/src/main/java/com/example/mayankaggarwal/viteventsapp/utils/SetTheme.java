package com.example.mayankaggarwal.viteventsapp.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.mayankaggarwal.viteventsapp.R;

/**
 * Created by mayankaggarwal on 02/03/17.
 */

public class SetTheme {
    public static int sTheme;
    public static String colorName="#f37051";
    public final static int BASE = 0;
    public final static int BLUE = 1;
    public final static int PINK = 2;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        switch (sTheme) {
            default:
                activity.setTheme(R.style.basetheme);
                colorName="#f37051";
            case BASE:
                activity.setTheme(R.style.basetheme);
                colorName="#f37051";
                break;
            case BLUE:
                activity.setTheme(R.style.bluetheme);
                colorName="#0b7cec";
                break;
            case PINK:
                activity.setTheme(R.style.pinktheme);
                colorName="#e07aff";
                break;
        }
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
                activity.setTheme(R.style.basetheme);
            case BASE:
                activity.setTheme(R.style.basetheme);
                break;
            case BLUE:
                activity.setTheme(R.style.bluetheme);
                break;
            case PINK:
                activity.setTheme(R.style.pinktheme);
                break;
        }
    }

}
