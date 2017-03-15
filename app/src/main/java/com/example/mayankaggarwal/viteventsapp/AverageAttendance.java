package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

public class AverageAttendance extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private  RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    public static int avg_per=0;

    public static TextView avgnumber;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Attendance");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout appBarLayout=(AppBarLayout)findViewById(R.id.avgappbar);

        appBarLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        toolbar.setBackground(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        ImageView image = (ImageView) findViewById(R.id.spinimage);
        final Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        image.startAnimation(myRotation);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.avg_swipe);

        avgnumber=(TextView)findViewById(R.id.avgnumber);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Attendance");
        progressDialog.setMessage("Loading..");
        progressDialog.create();
        progressDialog.setCancelable(false);

        recyclerView=(RecyclerView)findViewById(R.id.avgrecylerview);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new RVAverageAttendace(RealmController.with(this).getAtendance(),
                AverageAttendance.this, true));

        fetchAverageAttendance(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAverageAttendance(AverageAttendance.this);
            }
        });


    }


    private void fetchAverageAttendance(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                    progressDialog.show();
                    Data.updateAttendance(activity, new Data.UpdateCallback() {
                        @Override
                        public void onUpdate() {
                            recyclerView.setAdapter(new RVAverageAttendace(RealmController.with(activity).getAtendance(),
                                    AverageAttendance.this, true));
                            progressDialog.dismiss();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        @Override
                        public void onFailure() {
                            swipeRefreshLayout.setRefreshing(false);
                                progressDialog.dismiss();
                        }
                    });
                }
            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(AverageAttendance.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

