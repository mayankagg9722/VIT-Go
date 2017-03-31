package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.HomeTownRequest;
import com.example.mayankaggarwal.viteventsapp.models.LateRequest;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CalenderSetting;
import com.example.mayankaggarwal.viteventsapp.utils.CalenderTime;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class LateNightRequest extends AppCompatActivity {

    public static TextView fromdate, toDate, fromtime, totime, authority;
    private EditText place, reason;
    CardView submitLeave;
    LateRequest lateRequest;
    int wrongSpinnerItem = 0;
    int flag = 0;
    String empid = null;
    String profname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_late_night_request);

        init();

        setClickListener();
    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.leave_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Late Night");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        fromdate = (TextView) findViewById(R.id.fromdate);
        toDate = (TextView) findViewById(R.id.todate);
        fromtime = (TextView) findViewById(R.id.fromtime);
        totime = (TextView) findViewById(R.id.totime);
        authority = (TextView) findViewById(R.id.authority);
        submitLeave = (CardView) findViewById(R.id.submitleave);
        submitLeave.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
        place = (EditText) findViewById(R.id.place);
        reason = (EditText) findViewById(R.id.reason);

        lateRequest = new LateRequest();

        empid = getIntent().getStringExtra("empid");
        profname = getIntent().getStringExtra("profname");

        if (profname != null) {
            authority.setText(profname);
        }

    }

    private void setClickListener() {

        authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LateNightRequest.this, Faculties.class);
                intent.putExtra("comefrom", "latenight");
                startActivity(intent);
            }
        });


        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(LateNightRequest.this, fromdate);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(LateNightRequest.this, toDate);
            }
        });

        fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderTime.setTime(LateNightRequest.this, fromtime);
            }
        });

        totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderTime.setTime(LateNightRequest.this, totime);
            }
        });

        submitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = checkEmptyFields();
                if (p == 0) {
                    setFieldsText();
                    submitLateNight(LateNightRequest.this, lateRequest);
                } else if (p == 2) {
                    Toast.makeText(LateNightRequest.this, "Wrong Time Selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LateNightRequest.this, "Fill all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setFieldsText() {
        lateRequest.cvFaculty = empid;
        lateRequest.frmdate = fromdate.getText().toString();
        lateRequest.todate = toDate.getText().toString();
        lateRequest.frmtm = fromtime.getText().toString();
        lateRequest.totm = totime.getText().toString();
        lateRequest.txtVenue = place.getText().toString();
        lateRequest.txtRsn = reason.getText().toString();
    }

    private int checkEmptyFields() {
        flag = 0;
        if (fromtime.getText().length() > 0) {
            int exit_hour = Integer.parseInt(fromtime.getText().toString().trim().substring(0, 2).trim());
            String exit_am_pm = fromtime.getText().toString().trim().substring(5).trim();
            if (wrongSpinnerItem == 1) {
                flag = 1;
            } else if (isEmpty(fromdate)) {
                flag = 1;
            } else if (isEmpty(toDate)) {
                flag = 1;
            } else if (isEmpty(fromtime)) {
                flag = 1;
            } else if (isEmpty(totime)) {
                flag = 1;
            } else if (isEmpty(place)) {
                flag = 1;
            } else if (isEmpty(reason)) {
                flag = 1;
            } else if (empid.length() <= 0) {
                flag = 1;
            } else if (!(exit_am_pm.equals("PM") && exit_hour >= 8)) {
                flag = 2;
            }
        } else {
            flag = 1;
        }
        return flag;
    }

    private boolean isEmpty(TextView etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }


    private void submitLateNight(final Activity activity, final LateRequest lateRequest) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity, "Submitting Late Night...");
                Data.submitLateNight(activity, lateRequest, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
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
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
