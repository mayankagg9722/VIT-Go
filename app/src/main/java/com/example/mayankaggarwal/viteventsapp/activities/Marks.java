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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.adapter.RVDigitalAssignment;
import com.example.mayankaggarwal.viteventsapp.adapter.RVMarks;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

public class Marks extends AppCompatActivity {


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMarks(Marks.this);
            }
        });

        if(!(Prefs.getPrefs("marksjson",this).equals("notfound"))){
            recyclerView.setAdapter(new RVMarks(Prefs.getPrefs("marksjson",this),this));
        }

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.digital_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Marks");
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }
        linearLayout=(LinearLayout) findViewById(R.id.activity_marks);

        linearLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.digital_swipe_refresh_layout);

        recyclerView = (RecyclerView) findViewById(R.id.digital_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        if (Globals.fetchMarks == 0) {
            fetchMarks(this);
            Globals.fetchMarks = 1;
        }

    }


    private void fetchMarks(final Activity activity) {

        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity, "Fetching Data..");
                Data.getMarks(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                        if(!(Prefs.getPrefs("marksjson",activity).equals("notfound"))){
                            recyclerView.setAdapter(new RVMarks(Prefs.getPrefs("marksjson",activity),activity));
                        }
                    }

                    @Override
                    public void onFailure() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

