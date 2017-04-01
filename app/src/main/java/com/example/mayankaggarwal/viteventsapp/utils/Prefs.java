package com.example.mayankaggarwal.viteventsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;

import java.util.Set;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class Prefs {

    public static void setPrefs(String key, String value, Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("VITGo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPrefs(String key, Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("VITGo", Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "notfound");
    }

    public static void deletePrefs(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("VITGo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
}
