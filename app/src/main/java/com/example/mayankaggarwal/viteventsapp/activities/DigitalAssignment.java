package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.MainActivity;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVDigitalAssignment;
import com.example.mayankaggarwal.viteventsapp.adapter.RVExamShedule;
import com.example.mayankaggarwal.viteventsapp.adapter.RVFaculties;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import io.realm.Realm;

public class DigitalAssignment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_assignment);

        init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAssignment(DigitalAssignment.this);
            }
        });

        if(!(Prefs.getPrefs("digitalassignment",this).equals("notfound"))){
            recyclerView.setAdapter(new RVDigitalAssignment(Prefs.getPrefs("digitalassignment",this),this));
        }

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.digital_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Digital Assignments");
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.digital_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.digital_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if (Globals.fetchAssignment == 0) {
            fetchAssignment(this);
        }

        if(!(Prefs.getPrefs("digitalassignment",this).equals("notfound"))){
            recyclerView.setAdapter(new RVDigitalAssignment(Prefs.getPrefs("digitalassignment",this),this));
        }


    }


    private void fetchAssignment(final Activity activity) {
        CustomProgressDialog.showProgress(activity, "Fetching Assignment..");
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.getDigitalAssignment(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                        if(!(Prefs.getPrefs("digitalassignment",activity).equals("notfound"))){
                            recyclerView.setAdapter(new RVDigitalAssignment(Prefs.getPrefs("digitalassignment",activity),activity));
                        }
                        Globals.fetchAssignment = 1;
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure() {
                CustomProgressDialog.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

