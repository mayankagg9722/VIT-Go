package com.example.mayankaggarwal.viteventsapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

public class Hosteller extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView recyclerView;
    LinearLayout bottom_layout;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosteller);

        Toolbar toolbar = (Toolbar) findViewById(R.id.hostel_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hosteller");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }


        CardView leaveRequest=(CardView)findViewById(R.id.leaverequest);

        ((TextView)findViewById(R.id.hostellername)).setText(" SO WHAT'S \n "+ Prefs.getPrefs("name",this).split(" ")[0]+" \n UPTO ?");

        recyclerView=(RecyclerView)findViewById(R.id.bottom_recyclerview);

        bottomSheetBehavior=(BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_item)));

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottom_layout=(LinearLayout)findViewById(R.id.bottom_sheet_item);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        bottom_layout.getLayoutParams().height=height-120;



        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        recyclerView.setVisibility(View.GONE);
                        Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        recyclerView.setVisibility(View.GONE);
                        Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        recyclerView.setVisibility(View.VISIBLE);
                        Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        recyclerView.setVisibility(View.GONE);
                        Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        recyclerView.setVisibility(View.GONE);
                        Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
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
                startActivity(new Intent(Hosteller.this, LeaveRequest.class));
            }
        });


    }
}
