package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

/**
 * Created by mayankaggarwal on 16/03/17.
 */

public class CustomProgressDialog {

    private static AlertDialog alert;

    public static void showProgress(Activity activity, String str) {

        com.wang.avi.AVLoadingIndicatorView avi;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.custom_progress, null);
        avi = (com.wang.avi.AVLoadingIndicatorView) view.findViewById(R.id.avv);
        TextView textView = (TextView) view.findViewById(R.id.alertextview);
        textView.setTextColor(Color.parseColor(SetTheme.colorName));
        textView.setText(str);
        avi.setIndicatorColor(Color.parseColor(SetTheme.colorName));
        avi.show();
        builder.setView(view);
        alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }


    public static void hideProgress() {
        alert.dismiss();
    }

}
