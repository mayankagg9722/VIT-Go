package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;

import com.example.mayankaggarwal.viteventsapp.utils.Data;

import com.example.mayankaggarwal.viteventsapp.utils.InternetConnection;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private  SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.rrv_swipe_refresh_layout);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Fetching Attendance");
        progressDialog.setMessage("Loading..");
        progressDialog.create();
        progressDialog.setCancelable(false);

        Realm.init(this);

//        final Realm realm = Realm.getDefaultInstance();

        //update date and attendance
        updateDayAndDate();

        //fetch attendance
        fetchAttendance(this);

        getSupportActionBar().setTitle("");

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RVAttendaceList(RealmController.with(this).getAtendance(), this, true));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                progressDialog.show();
                    fetchAttendance(MainActivity.this);
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.navicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        TextView name=(TextView)header.findViewById(R.id.nametext);
        TextView regno=(TextView)header.findViewById(R.id.regtext);

        name.setText(Prefs.getPrefs("name",this));
        regno.setText(Prefs.getPrefs("regno",this));
    }

    private void updateDayAndDate() {

        TextView main_date = (TextView) findViewById(R.id.main_date);
        Date date = new Date();
        SimpleDateFormat dat = new SimpleDateFormat("dd MMMM,");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        main_date.setText(dat.format(date).toString()+" "+day.format(date).toString());

    }

    private void fetchAttendance(final Activity activity) {
//            Log.d("tagg","attendance");
        progressDialog.show();
        if(InternetConnection.isNetworkAvailable()){
            Data.updateAttendance(this, new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
//                    Log.d("tagg","success api");
                    recyclerView.setAdapter(new RVAttendaceList(RealmController.with(activity).getAtendance(), MainActivity.this, true));
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
                @Override
                public void onFailure() {
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
//                    Log.d("tagg","fail api");
                }
            });
        }else{
            progressDialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,TimeTable.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            RealmController.with(this).clearAll();
            Prefs.deletePrefs(this);
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
