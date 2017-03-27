package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
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
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CalenderSetting;
import com.example.mayankaggarwal.viteventsapp.utils.CalenderTime;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.ArrayList;
import java.util.List;

public class OutingRequest extends AppCompatActivity {

    public static TextView fromdate, fromtime, totime;
    private EditText place, reason;
    public static List<String> fac_name = new ArrayList<>();
    public static List<String> fac_id = new ArrayList<>();
    CardView submitLeave;
    ArrayAdapter<String> name;
    Spinner authority;
    HomeTownRequest homeTownRequest;
    int wrongSpinnerItem = 0;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outing_request);

        init();

        getLeave(this);

        setClickListener();

    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.leave_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Outing");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        fromdate = (TextView) findViewById(R.id.fromdate);
        fromtime = (TextView) findViewById(R.id.fromtime);
        totime = (TextView) findViewById(R.id.totime);
        authority = (Spinner) findViewById(R.id.authority);
        submitLeave = (CardView) findViewById(R.id.submitleave);
        submitLeave.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
        place = (EditText) findViewById(R.id.place);
        reason = (EditText) findViewById(R.id.reason);

        fac_name.clear();
        fac_id.clear();

        fac_name.add("--Select--");
        fac_id.add("--Select--");

        homeTownRequest = new HomeTownRequest();

    }

    private void setClickListener() {

        authority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homeTownRequest.apply = fac_id.get(position);
                if (parent.getItemAtPosition(position).equals("--Select--")) {
                    wrongSpinnerItem = 1;
                } else {
                    wrongSpinnerItem = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                wrongSpinnerItem = 1;
            }
        });


        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderSetting.setCalendar(OutingRequest.this, fromdate);
            }
        });


        fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderTime.setTime(OutingRequest.this, fromtime);
            }
        });

        totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderTime.setTime(OutingRequest.this, totime);
            }
        });

        submitLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=checkEmptyFields();
                if (p== 0) {
                    setFieldsText();
                    submitHomeTownLeave(OutingRequest.this, homeTownRequest);
                } else if(p==1) {
                    Toast.makeText(OutingRequest.this, "Fill all fields.", Toast.LENGTH_SHORT).show();
                } else if(p==2) {
                    Toast.makeText(OutingRequest.this, "Check Date or time.", Toast.LENGTH_SHORT).show();
                } else if(p==3) {
                    Toast.makeText(OutingRequest.this, "Select only weekands.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setFieldsText() {
        homeTownRequest.lvtype = "OG";
        homeTownRequest.exitdate = fromdate.getText().toString();
        homeTownRequest.place = place.getText().toString();
        homeTownRequest.reason = reason.getText().toString();
        homeTownRequest.sttimeHh = fromtime.getText().toString().trim().substring(0, 2).trim();
        homeTownRequest.sttimeMm = fromtime.getText().toString().trim().substring(3, 5).trim();
        homeTownRequest.frmTimetype = fromtime.getText().toString().trim().substring(5).trim();
        homeTownRequest.endtimeHh = totime.getText().toString().trim().substring(0, 2).trim();
        homeTownRequest.endtimeMm = totime.getText().toString().trim().substring(3, 5).trim();
        homeTownRequest.toTimetype = totime.getText().toString().trim().substring(5).trim();
    }

    private int checkEmptyFields() {
        flag = 0;
        int exit_hour = Integer.parseInt(fromtime.getText().toString().trim().substring(0, 2).trim());
        int entry_hour = Integer.parseInt(totime.getText().toString().trim().substring(0, 2).trim());
        String exit_am_pm = fromtime.getText().toString().trim().substring(5).trim();
        String entry_am_pm = totime.getText().toString().trim().substring(5).trim();
        Log.d("tagg",Globals.dayName);
        if (wrongSpinnerItem == 1) {
            flag = 1;
        } else if (isEmpty(fromdate)) {
            flag = 1;
        } else if (isEmpty(fromtime)) {
            flag = 1;
        } else if (isEmpty(totime)) {
            flag = 1;
        } else if (isEmpty(place)) {
            flag = 1;
        } else if (isEmpty(reason)) {
            flag = 1;
        } else if (exit_hour <= 7 && exit_am_pm.equals("AM")) {
            flag=2;
        }
        else if (entry_hour >=6 && entry_am_pm.equals("PM")) {
            flag=2;
        }
        else if(!(Globals.dayName.equals("Saturday") || Globals.dayName.equals("Sunday"))){
            flag=3;
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

    private void getLeave(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity, "Fetching Authorities...");
                Data.getLeaves(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        name = new ArrayAdapter<String>(OutingRequest.this, R.layout.support_simple_spinner_dropdown_item, fac_name);
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

    private void submitHomeTownLeave(final Activity activity, HomeTownRequest hoomeTownRequest) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity, "Submitting Leave...");
                Data.submitHometownLeave(activity, homeTownRequest, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        name = new ArrayAdapter<String>(OutingRequest.this, R.layout.support_simple_spinner_dropdown_item, fac_name);
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
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
