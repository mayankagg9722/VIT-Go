package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CalenderSetting;
import com.example.mayankaggarwal.viteventsapp.utils.CalenderTime;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequest extends AppCompatActivity {


    public static TextView fromdate,toDate,fromtime,totime;
    public static List<String> fac_name=new ArrayList<>();
    ArrayAdapter<String> name;
    Spinner authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.leave_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hosteller");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }


        fromdate=(TextView)findViewById(R.id.fromdate);
        toDate=(TextView)findViewById(R.id.todate);
        fromtime=(TextView)findViewById(R.id.fromtime);
        totime=(TextView)findViewById(R.id.totime);
         authority=(Spinner)findViewById(R.id.authority);

//        Drawable drawable = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            drawable = getDrawable(R.drawable.ic_date_range_white_24dp);
//        }
//        drawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTint(drawable, Color.parseColor(SetTheme.colorName));
//        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
//        fromdate.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
//        toDate.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);


//        List<String> facname=new ArrayList<>();
//        authority.getBackground().setColorFilter(Color.parseColor(SetTheme.colorName), PorterDuff.Mode.SRC_ATOP);
//        authority.setAdapter(arrayAdapter);


        getLeave(this);


        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(LeaveRequest.this,fromdate);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(LeaveRequest.this,toDate);
            }
        });

        fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderTime.setTime(LeaveRequest.this,fromtime);
            }
        });

        totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderTime.setTime(LeaveRequest.this,totime);
            }
        });


    }

    private void getLeave(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity,"Fetching Authorities...");
                    Data.getLeaves(activity, new Data.UpdateCallback() {
                        @Override
                        public void onUpdate() {
                            name=new ArrayAdapter<String>(LeaveRequest.this,R.layout.support_simple_spinner_dropdown_item,fac_name);
                            authority.setAdapter(name);
                            CustomProgressDialog.hideProgress();

                        }
                        @Override
                        public void onFailure() {
                            CustomProgressDialog.hideProgress();
                        }
                    });
                }

            @Override
            public void onFailure() {

            }
        });
    }
}

