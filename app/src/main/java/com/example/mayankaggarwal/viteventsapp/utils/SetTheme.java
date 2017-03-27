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

    public static String colorName = "#009688";

    public final static int ONE = 1;
    public final static int TWO = 2;
    public final static int THREE = 3;
    public final static int FOUR = 4;
    public final static int FIVE = 5;
    public final static int SIX = 6;
    public final static int SEVEN = 7;
    public final static int EIGHT = 8;
    public final static int NINE = 9;


    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        switch (sTheme) {
            default:
                activity.setTheme(R.style.two);
                colorName = "#009688";
            case ONE:
                activity.setTheme(R.style.one);
                colorName = "#37CE97";
                break;
            case TWO:
                activity.setTheme(R.style.two);
                colorName = "#009688";
                break;
            case THREE:
                activity.setTheme(R.style.three);
                colorName = "#4CB050";
                break;

            case FOUR:
                activity.setTheme(R.style.four);
                colorName = "#3F51B5";
                break;

            case FIVE:
                activity.setTheme(R.style.five);
                colorName = "#2196F3";
                break;

            case SIX:
                activity.setTheme(R.style.six);
                colorName = "#673BB7";
                break;

            case SEVEN:
                activity.setTheme(R.style.seven);
                colorName = "#EA1E63";
                break;

            case EIGHT:
                activity.setTheme(R.style.eight);
                colorName = "#F44236";
                break;

            case NINE:
                activity.setTheme(R.style.nine);
                colorName = "#FF9700";
                break;
        }
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }


    public static void onActivityCreateSetTheme(Activity activity) {
        String color = Prefs.getPrefs("theme", activity);
        if (color.equals("notfound")) {
            sTheme = 0;
            colorName = "#009688";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.parseColor(colorName));
            }
        } else {
            sTheme = Integer.parseInt(color);
            switch (sTheme) {
                default:
                    colorName = "#009688";
                case ONE:
                    colorName = "#37CE97";
                    break;
                case TWO:
                    colorName = "#009688";
                    break;
                case THREE:
                    colorName = "#4CB050";
                    break;

                case FOUR:
                    colorName = "#3F51B5";
                    break;

                case FIVE:
                    colorName = "#2196F3";
                    break;

                case SIX:
                    colorName = "#673BB7";
                    break;

                case SEVEN:
                    colorName = "#EA1E63";
                    break;

                case EIGHT:
                    colorName = "#F44236";
                    break;

                case NINE:
                    colorName = "#FF9700";
                    break;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.parseColor(colorName));
            }
        }
        switch (sTheme) {
            default:
                colorName = "#009688";
            case ONE:
                colorName = "#37CE97";
                break;
            case TWO:
                colorName = "#009688";
                break;
            case THREE:
                colorName = "#4CB050";
                break;

            case FOUR:
                colorName = "#3F51B5";
                break;

            case FIVE:
                colorName = "#2196F3";
                break;

            case SIX:
                colorName = "#673BB7";
                break;

            case SEVEN:
                colorName = "#EA1E63";
                break;

            case EIGHT:
                colorName = "#F44236";
                break;

            case NINE:
                colorName = "#FF9700";
                break;
        }
    }

    public static void setThemePref(Activity activity) {
        String color = Prefs.getPrefs("theme", activity);
        if (color.equals("notfound")) {
            sTheme = 0;
            colorName = "#009688";
        } else {
            sTheme = Integer.parseInt(color);
            switch (sTheme) {
                default:
                    colorName = "#009688";
                case ONE:
                    colorName = "#37CE97";
                    break;
                case TWO:
                    colorName = "#009688";
                    break;
                case THREE:
                    colorName = "#4CB050";
                    break;

                case FOUR:
                    colorName = "#3F51B5";
                    break;

                case FIVE:
                    colorName = "#2196F3";
                    break;

                case SIX:
                    colorName = "#673BB7";
                    break;

                case SEVEN:
                    colorName = "#EA1E63";
                    break;

                case EIGHT:
                    colorName = "#F44236";
                    break;

                case NINE:
                    colorName = "#FF9700";
                    break;
            }
        }
    }

}
