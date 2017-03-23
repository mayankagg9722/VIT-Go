package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVEvent;
import com.example.mayankaggarwal.viteventsapp.adapter.RVLeave;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

public class Hosteller extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout bottom_layout;
    RelativeLayout relativeLayout;
    CardView leaveRequest,outing;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosteller);

        init();

        getLeave(this);

        setClickListener();
    }

    private void init(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.hostel_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hosteller");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        relativeLayout=(RelativeLayout)findViewById(R.id.activity_hosteller);

        relativeLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        leaveRequest=(CardView)findViewById(R.id.leaverequest);
        outing=(CardView)findViewById(R.id.outing);

        ((TextView)findViewById(R.id.hostellername)).setText(" SO WHAT'S \n "+ Prefs.getPrefs("name",this).split(" ")[0]+" \n UPTO ?");

        recyclerView=(RecyclerView)findViewById(R.id.bottom_recyclerview);

        bottomSheetBehavior=(BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_item)));

        recyclerView=(RecyclerView)findViewById(R.id.bottom_recyclerview);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottom_layout=(LinearLayout)findViewById(R.id.bottom_sheet_item);


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        bottom_layout.getLayoutParams().height=height-120;

    }


    private void getLeave(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity, "Fetching Leaves...");
                Data.getLeaves(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        recyclerView.setAdapter(new RVLeave(Prefs.getPrefs("leaves",activity), activity));
                    }

                    @Override
                    public void onFailure() {
                        CustomProgressDialog.hideProgress();
                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void setClickListener() {
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        recyclerView.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        recyclerView.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        recyclerView.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        recyclerView.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        recyclerView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        leaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hosteller.this, LeaveRequest.class);
                startActivity(intent);
            }
        });

        outing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hosteller.this, OutingRequest.class);
                startActivity(intent);
            }
        });
    }
}
