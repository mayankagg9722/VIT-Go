package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVAttendaceList;
import com.example.mayankaggarwal.viteventsapp.adapter.RVDigitalAssignment;
import com.example.mayankaggarwal.viteventsapp.adapter.RVDigitalMarks;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksRequest;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

public class DigitalMarks extends AppCompatActivity {


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    DigitalMarksRequest digitalMarksRequest;
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_marks);

        init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDigitalMarks(DigitalMarks.this);
            }
        });

            recyclerView.setAdapter(new RVDigitalMarks(RealmController.with(this).getDigitalMarks(), this));
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.digital_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Digital Assignment");
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        digitalMarksRequest=new DigitalMarksRequest();

        relativeLayout=(RelativeLayout)findViewById(R.id.activity_digital_marks);

        relativeLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.digital_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.digital_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        fetchDigitalMarks(this);

        String sem=getIntent().getStringExtra("sem");
        String classnbr=getIntent().getStringExtra("classnbr");
        String crscd=getIntent().getStringExtra("crscd");
        String crstp=getIntent().getStringExtra("crstp");

        digitalMarksRequest.sem=sem;
        digitalMarksRequest.classnbr=classnbr;
        digitalMarksRequest.crscd=crscd;
        digitalMarksRequest.crstp=crstp;

    }

    private void fetchDigitalMarks(final Activity activity) {

        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity, "Fetching Data..");
                Data.getDigitalAssignmentMarsk(activity,digitalMarksRequest, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setAdapter(new RVDigitalMarks(RealmController.with(DigitalMarks.this).getDigitalMarks(), activity));
                    }

                    @Override
                    public void onFailure() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure() {
            }
        });
    }


}
