package com.example.mayankaggarwal.viteventsapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.Random;

/**
 * Created by mayankaggarwal on 16/03/17.
 */

public class CustomProgressDialog {

    private static AlertDialog alert;

    private static boolean showingAlert=false;

    public static void showProgress(Activity activity, String str) {

        showingAlert=false;

        com.wang.avi.AVLoadingIndicatorView avi;

        String[] s={"BallClipRotateIndicator","BallClipRotatePulseIndicator","SquareSpinIndicator","BallClipRotateMultipleIndicator"
                ,"BallTrianglePathIndicator","LineScaleIndicator","BallBeatIndicator","BallScaleRippleMultipleIndicator"
                ,"TriangleSkewSpinIndicator"};

        Random r=new Random();

        int i=r.nextInt(s.length);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomDialog);
        View view = activity.getLayoutInflater().inflate(R.layout.custom_progress, null);
        avi = (com.wang.avi.AVLoadingIndicatorView) view.findViewById(R.id.avv);
        TextView textView = (TextView) view.findViewById(R.id.alertextview);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setText(str);
        avi.setIndicator(s[i]);
        avi.setIndicatorColor(Color.parseColor("#ffffff"));
        avi.show();
        builder.setView(view);
        alert = builder.create();
        alert.setCancelable(false);
        alert.show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        showingAlert=true;

    }


    public static void hideProgress() {
        if(showingAlert==true){
            alert.dismiss();
        }
    }

}
