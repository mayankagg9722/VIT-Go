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

    public final static int GREEN = 3;
    public final static int RED= 4;
    public final static int NEWPINK = 5;
    public final static int DARKGREEN = 6;
    public final static int DARKBLUE = 7;


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
                colorName="#8057E1";
                break;

            case GREEN:
                activity.setTheme(R.style.greentheme);
                colorName="#37cf97";
                break;

            case RED:
                activity.setTheme(R.style.redtheme);
                colorName="#f44336";
                break;

            case NEWPINK:
                activity.setTheme(R.style.newpinktheme);
                colorName="#e91e63";
                break;

            case DARKGREEN:
                activity.setTheme(R.style.darkgreentheme);
                colorName="#009688";
                break;

            case DARKBLUE:
                activity.setTheme(R.style.darkbluetheme);
                colorName="#3f51b5";
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
                    colorName="#8057E1";
                    break;

                case GREEN:
                    colorName="#37cf97";
                    break;

                case RED:
                    colorName="#f44336";
                    break;

                case NEWPINK:
                    colorName="#e91e63";
                    break;

                case DARKGREEN:
                    colorName="#009688";
                    break;

                case DARKBLUE:
                    colorName="#3f51b5";
                    break;
            }
        }
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
                colorName="#8057E1";
                break;

            case GREEN:
                activity.setTheme(R.style.greentheme);
                colorName="#37cf97";
                break;

            case RED:
                activity.setTheme(R.style.redtheme);
                colorName="#f44336";
                break;

            case NEWPINK:
                activity.setTheme(R.style.newpinktheme);
                colorName="#e91e63";
                break;

            case DARKGREEN:
                activity.setTheme(R.style.darkgreentheme);
                colorName="#009688";
                break;

            case DARKBLUE:
                activity.setTheme(R.style.darkbluetheme);
                colorName="#3f51b5";
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
                    colorName="#8057E1";
                    break;

                case GREEN:
                    colorName="#37cf97";
                    break;

                case RED:
                    colorName="#f44336";
                    break;

                case NEWPINK:
                    colorName="#e91e63";
                    break;

                case DARKGREEN:
                    colorName="#009688";
                    break;

                case DARKBLUE:
                    colorName="#3f51b5";
                    break;
            }
        }
    }

}
