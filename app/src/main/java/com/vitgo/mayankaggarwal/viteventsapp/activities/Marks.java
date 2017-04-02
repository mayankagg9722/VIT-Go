package com.vitgo.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vitgo.mayankaggarwal.viteventsapp.R;
import com.vitgo.mayankaggarwal.viteventsapp.adapter.RVMarks;
import com.vitgo.mayankaggarwal.viteventsapp.rest.Data;
import com.vitgo.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.vitgo.mayankaggarwal.viteventsapp.utils.Globals;
import com.vitgo.mayankaggarwal.viteventsapp.utils.Prefs;
import com.vitgo.mayankaggarwal.viteventsapp.utils.SetTheme;

public class Marks extends AppCompatActivity {


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;
    public static ImageView imageView;

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
        toolbar.setBackgroundColor(Color.parseColor("#00000000"));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        imageView=(ImageView)findViewById(R.id.noclass);
        imageView.setVisibility(View.VISIBLE);

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
        }


    }


    private void fetchMarks(final Activity activity) {
        CustomProgressDialog.showProgress(activity, "Fetching Data..");
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {

                Data.getMarks(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                        if(!(Prefs.getPrefs("marksjson",activity).equals("notfound"))){
                            recyclerView.setAdapter(new RVMarks(Prefs.getPrefs("marksjson",activity),activity));
                        }
                        Globals.fetchMarks = 1;
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
                CustomProgressDialog.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

}

