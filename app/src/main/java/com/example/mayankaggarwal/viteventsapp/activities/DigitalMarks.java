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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
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
    public static ImageView imageView;


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

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.digital_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("crscd"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        imageView=(ImageView)findViewById(R.id.noclass);
        imageView.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        digitalMarksRequest = new DigitalMarksRequest();

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_digital_marks);

        relativeLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.digital_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.digital_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String sem = getIntent().getStringExtra("sem");
        String classnbr = getIntent().getStringExtra("classnbr");
        String crscd = getIntent().getStringExtra("crscd");
        String crstp = getIntent().getStringExtra("crstp");

        int i;
        int flag=0;
        int pos=0;


        for(i=0;i<Globals.digitalCourseCode.size();i++){
            if(Globals.digitalCourseCode.get(i).equals(crscd) && Globals.digitalCourseType.get(i).equals(crstp)){
                flag=1;
                pos=i;
                break;
            }
        }

        if(flag==0){
            fetchDigitalMarks(this);
        }else {
            if(!(Prefs.getPrefs("digitalAssignmentMarks",this).equals("notfound"))){
                recyclerView.setAdapter(new RVDigitalMarks(Globals.digitalAssignmentMarks.get(pos), this));
            }

        }

        digitalMarksRequest.sem = sem;
        digitalMarksRequest.classnbr = classnbr;
        digitalMarksRequest.crscd = crscd;
        digitalMarksRequest.crstp = crstp;

    }

    private void fetchDigitalMarks(final Activity activity) {

        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity, "Fetching Data..");
                Data.getDigitalAssignmentMarsk(activity, digitalMarksRequest, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                        if(!(Prefs.getPrefs("digitalAssignmentMarks",activity).equals("notfound"))){
                            recyclerView.setAdapter(new RVDigitalMarks(Prefs.getPrefs("digitalAssignmentMarks",activity), activity));
                        }
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
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
