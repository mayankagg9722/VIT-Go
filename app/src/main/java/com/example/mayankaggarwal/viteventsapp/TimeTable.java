package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.InternetConnection;

public class TimeTable extends AppCompatActivity {

    ActionBar actionBar;
    ViewPager viewPager;
    FrameLayout frameLayout;

    private RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    public static CardView mycard,mycard1,mycard2,mycard3,mycard4,mycard5,mycard6;

    public static int pagerHieght=0;
    public static int cardwidth=0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        actionBar=getSupportActionBar();
        actionBar.setTitle("Timetable");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable( new ColorDrawable(Color.parseColor("#f37051")));

        viewPager=(ViewPager)findViewById(R.id.view);

        frameLayout=(FrameLayout)findViewById(R.id.layout_footer);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Fetching Attendance");
        progressDialog.setMessage("Loading..");
        progressDialog.create();
        progressDialog.setCancelable(false);

        mycard=(CardView)findViewById(R.id.mycard);
        mycard1=(CardView)findViewById(R.id.mycard1);
        mycard2=(CardView)findViewById(R.id.mycard2);
        mycard3=(CardView)findViewById(R.id.mycard3);
        mycard4=(CardView)findViewById(R.id.mycard4);
        mycard5=(CardView)findViewById(R.id.mycard5);
        mycard6=(CardView)findViewById(R.id.mycard6);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        pagerHieght=height/4;
        cardwidth=width/7;

//        Log.d("tagg", String.valueOf("width:"+width+"height:"+height));
//        Log.d("tagg", String.valueOf("cardwidth:"+cardwidth));


        viewPager.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,pagerHieght));

        frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,cardwidth));

        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams)frameLayout.getLayoutParams();
        params.setMargins(0, pagerHieght-(cardwidth/2), 0, 0);
        frameLayout.setLayoutParams(params);

        PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        setCard(this);

        //fetch attendance
        fetchAttendance(this,"MON");

        recyclerView=(RecyclerView)findViewById(R.id.timetablerecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void setCard(final Activity activity) {
        mycard.setLayoutParams(new LinearLayout.LayoutParams(cardwidth,cardwidth));
        mycard.setRadius(cardwidth/3);
        mycard.setUseCompatPadding(true);
        mycard1.setLayoutParams(new LinearLayout.LayoutParams(cardwidth,cardwidth));
        mycard1.setRadius(cardwidth/3);
        mycard1.setUseCompatPadding(true);
        mycard2.setLayoutParams(new LinearLayout.LayoutParams(cardwidth,cardwidth));
        mycard2.setRadius(cardwidth/3);
        mycard2.setUseCompatPadding(true);
        mycard3.setLayoutParams(new LinearLayout.LayoutParams(cardwidth,cardwidth));
        mycard3.setRadius(cardwidth/3);
        mycard3.setUseCompatPadding(true);
        mycard4.setLayoutParams(new LinearLayout.LayoutParams(cardwidth,cardwidth));
        mycard4.setRadius(cardwidth/3);
        mycard4.setUseCompatPadding(true);
        mycard5.setLayoutParams(new LinearLayout.LayoutParams(cardwidth,cardwidth));
        mycard5.setRadius(cardwidth/3);
        mycard5.setUseCompatPadding(true);
        mycard6.setLayoutParams(new LinearLayout.LayoutParams(cardwidth,cardwidth));
        mycard6.setRadius(cardwidth/3);
        mycard6.setUseCompatPadding(true);

        mycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mycard.setCardBackgroundColor(Color.RED);
                fetchAttendance(activity,"MON");
            }
        });

        mycard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAttendance(activity,"TUE");
            }
        });

        mycard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAttendance(activity,"WED");
            }
        });

        mycard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAttendance(activity,"THU");
            }
        });

        mycard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAttendance(activity,"FRi");
            }
        });

        mycard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAttendance(activity,"SAT");
            }
        });

        mycard6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAttendance(activity,"SUN");
            }
        });

    }


    private void fetchAttendance(final Activity activity, final String day) {
//            Log.d("tagg","attendance");
        progressDialog.show();
        if(InternetConnection.isNetworkAvailable()){
            Data.updateAttendance(this, new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
//                    Log.d("tagg","success api");
                    recyclerView.setAdapter(new RVTimeTableDetails(RealmController.with(activity).getAtendance(), TimeTable.this, true,day));
                    progressDialog.dismiss();
//                    swipeRefreshLayout.setRefreshing(false);
                }
                @Override
                public void onFailure() {
                    progressDialog.dismiss();
//                    swipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
//                    Log.d("tagg","fail api");
                }
            });
        }else{
            progressDialog.dismiss();
//            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(TimeTable.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

}
