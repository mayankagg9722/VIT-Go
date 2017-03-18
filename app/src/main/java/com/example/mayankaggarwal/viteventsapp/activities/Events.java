package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.MainActivity;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVAttendaceList;
import com.example.mayankaggarwal.viteventsapp.adapter.RVEvent;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import io.realm.Realm;

public class Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.event_layout);
        linearLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        Toolbar toolbar=(Toolbar)findViewById(R.id.event_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upcoming Events");

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        recyclerView=(RecyclerView)findViewById(R.id.event_recycler);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_event);

        fetchEvents(this);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new RVEvent(RealmController.with(this).getEvents(), Events.this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchEvents(Events.this);
            }
        });

    }


    private void fetchEvents(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                    CustomProgressDialog.showProgress(Events.this,"Fetching Events...");
                    Data.getEvent(activity, new Data.UpdateCallback() {
                        @Override
                        public void onUpdate() {
                            recyclerView.setAdapter(new RVEvent(RealmController.with(activity).getEvents(), Events.this));
                            CustomProgressDialog.hideProgress();
                            swipeRefreshLayout.setRefreshing(false);
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
                Toast.makeText(Events.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
