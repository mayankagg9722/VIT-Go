package com.example.mayankaggarwal.viteventsapp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.mayankaggarwal.viteventsapp.R;

import java.util.Set;

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
        String color=Prefs.getPrefs("theme",activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.parseColor(colorName));
        }
        if(color.equals("notfound")){
            sTheme=0;
            colorName="#f37051";
        }else{
            sTheme=Integer.parseInt(color);
            switch (sTheme) {
                default:
                    colorName="#f37051";
                case BASE:
                    colorName="#f37051";
                    break;
                case BLUE:
                    colorName="#0b7cec";
                    break;
                case PINK:
                    colorName="#e07aff";
                    break;
            }
        }
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

    public static void setThemePref(Activity activity) {
        String color=Prefs.getPrefs("theme",activity);
        if(color.equals("notfound")){
            sTheme=0;
            colorName="#f37051";
        }else{
            sTheme=Integer.parseInt(color);
            switch (sTheme) {
                default:
                    colorName="#f37051";
                case BASE:
                    colorName="#f37051";
                    break;
                case BLUE:
                    colorName="#0b7cec";
                    break;
                case PINK:
                    colorName="#e07aff";
                    break;
            }
        }
    }

}
