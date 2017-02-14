package com.example.mayankaggarwal.viteventsapp;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;

import com.example.mayankaggarwal.viteventsapp.utils.Data;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Fetching Timetable Attendance");
        progressDialog.setMessage("Loading");
        progressDialog.create();

        Realm.init(this);

        final Realm realm = Realm.getDefaultInstance();

        progressDialog.show();

        //update date and attendance
        updateDayAndDate();

        //fetch attendance
        fetchAttendance();

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RVAttendaceList(RealmController.with(this).getAtendance(), this, true));
        recyclerView.getAdapter().notifyDataSetChanged();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void updateDayAndDate() {

        TextView main_date = (TextView) findViewById(R.id.main_date);
        TextView main_day = (TextView) findViewById(R.id.main_day);
        Date date = new Date();
        SimpleDateFormat dat = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        main_date.setText(dat.format(date).toString());
        main_day.setText(day.format(date).toString());

    }

    private void fetchAttendance() {
        if (!RealmController.with(this).hasAttendance()) {
//            Log.d("tagg","skip already has attendance");
            Data.updateAttendance(this, new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
//                    Log.d("tagg","success api");
                    recyclerView.getAdapter().notifyDataSetChanged();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure() {
                    progressDialog.dismiss();
//                    Log.d("tagg","fail api");
                }
            });
        }else{
            progressDialog.dismiss();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            RealmController.with(this).clearAll();
            Prefs.deletePrefs(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            recyclerView.getAdapter().notifyDataSetChanged();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
