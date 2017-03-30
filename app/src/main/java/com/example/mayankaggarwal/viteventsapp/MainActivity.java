package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;

import com.example.mayankaggarwal.viteventsapp.adapter.RVAttendaceList;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.rest.Data;

import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    AppBarLayout appBarLayout;
    ActionBarDrawerToggle mActionDrawerToggle;
    public static ImageView imageView;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        SetTheme.setThemePref(this);

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.setBackground(new ColorDrawable(Color.parseColor(SetTheme.colorName)));
        imageView=(ImageView)findViewById(R.id.noclass);
        imageView.setVisibility(View.GONE);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.rrv_swipe_refresh_layout);

        Realm.init(this);

        //update date and attendance
        updateDayAndDate();

        //fetch attendance

        fetchAttendance(this);


        getSupportActionBar().setTitle("");

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (RealmController.with(this).hasAttendance()) {
            Log.d("playyyRefresh",RealmController.with(this).getAtendance().toString());
            recyclerView.setAdapter(new RVAttendaceList(RealmController.with(this).getAtendance(), this, true));
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Globals.doneFetching = 0;
                fetchAttendance(MainActivity.this);
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final CoordinatorLayout mainView = (CoordinatorLayout) findViewById(R.id.main_coordinate);

        mActionDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
        };

        mActionDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.navvv);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerLayout.setDrawerListener(mActionDrawerToggle);
        mActionDrawerToggle.syncState();

        navigation_drawer.nav_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

    }


    private void updateDayAndDate() {

        TextView main_date = (TextView) findViewById(R.id.main_date);
        Date date = new Date();
        SimpleDateFormat dat = new SimpleDateFormat("dd MMMM,");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        main_date.setText(dat.format(date).toString() + " " + day.format(date).toString());

    }

    private void fetchAttendance(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                if (Globals.doneFetching == 0) {
                    CustomProgressDialog.showProgress(MainActivity.this, "Fetching Attendance...");
                    Data.updateAttendance(activity, new Data.UpdateCallback() {
                        @Override
                        public void onUpdate() {
                            Log.d("playyynormal",RealmController.with(activity).getAtendance().toString());

                            RVAttendaceList attendaceList=new RVAttendaceList(RealmController.with(activity).getAtendance(), MainActivity.this, true);
                            if(Globals.attendanceListSize==Globals.courseCodeDaySize){
                                recyclerView.setAdapter(attendaceList);
                            }else {
                                fetchAttendance(activity);
                            }
                            swipeRefreshLayout.setRefreshing(false);
                            Globals.doneFetching = 1;
                            if (Globals.doneFetching == 1) {
                                CustomProgressDialog.hideProgress();
                            }
                        }
                        @Override
                        public void onFailure() {
                            swipeRefreshLayout.setRefreshing(false);
                            CustomProgressDialog.hideProgress();
                            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
