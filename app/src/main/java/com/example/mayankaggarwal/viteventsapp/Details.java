package com.example.mayankaggarwal.viteventsapp;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.Random;

import at.grabner.circleprogress.CircleProgressView;



public class Details extends AppCompatActivity {

    CircleProgressView mCircleView;
    public int prog = 0;
    public int attendedClasses = 0;
    public int totalClasses = 0;
    int miss = 0;
    int attend = 0;
    private RecyclerView recyclerView;
    LinearLayout detailsLayout;
    ActionBar actionBar;
    com.wang.avi.AVLoadingIndicatorView avi;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SetTheme.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_details);

        getWindow().setAllowEnterTransitionOverlap(false);

        mCircleView = (CircleProgressView) findViewById(R.id.circleView);

        actionBar=getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        actionBar.setTitle("Detail Attendance");
        actionBar.setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.detail_recycler);


        String[] s={"BallClipRotateIndicator","BallClipRotatePulseIndicator","SquareSpinIndicator","BallClipRotateMultipleIndicator"
                ,"BallTrianglePathIndicator","LineScaleIndicator","BallBeatIndicator","BallScaleRippleMultipleIndicator"
                ,"TriangleSkewSpinIndicator"};

        Random r=new Random();
        int i=r.nextInt(s.length);
        avi = (com.wang.avi.AVLoadingIndicatorView)findViewById(R.id.detailavv);
        avi.setIndicator(s[i]);
        avi.setIndicatorColor(Color.parseColor(SetTheme.colorName));

        TextView course_name = (TextView) findViewById(R.id.detail_course_name);
        TextView course_slot = (TextView) findViewById(R.id.course_slot);
        final TextView attend_number = (TextView) findViewById(R.id.atendnumber);
        TextView course_code = (TextView) findViewById(R.id.detail_course_code);
        TextView faculty = (TextView) findViewById(R.id.detail_faculty);

        final TextView misstext = (TextView) findViewById(R.id.miss);
        final TextView attendtext = (TextView) findViewById(R.id.attend);

        ImageButton attend_plus = (ImageButton) findViewById(R.id.attend_plus);
        final ImageButton attend_minus = (ImageButton) findViewById(R.id.attend_minus);
        ImageButton miss_plus = (ImageButton) findViewById(R.id.miss_plus);
        ImageButton miss_minus = (ImageButton) findViewById(R.id.miss_minus);


        detailsLayout = (LinearLayout) findViewById(R.id.detaillistlayout);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //$$$$$$$$$$$$$$$$$ setting adapter
        setGlobalAdapter();


        course_name.setText(getIntent().getStringExtra("coursename"));
        course_slot.setText(getIntent().getStringExtra("classroom"));
        course_code.setText(getIntent().getStringExtra("code"));
        faculty.setText(getIntent().getStringExtra("faculty"));
        attend_number.setText(getIntent().getStringExtra("attendedclass") + "/" + getIntent().getStringExtra("totalclass"));

        attendedClasses = Integer.parseInt(getIntent().getStringExtra("attendedclass"));
        totalClasses = Integer.parseInt(getIntent().getStringExtra("totalclass"));


        prog = Integer.parseInt(getIntent().getStringExtra("percentage").replace("%", ""));
        mCircleView.setValueAnimated(prog, 1500);

        attend_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int try_prog = (int) calcPercentage(attendedClasses + 1, totalClasses + 1);
                if (try_prog >= 0 && try_prog <= 100 && (attendedClasses <= totalClasses)) {
                    attend = attend + 1;
                    attendtext.setText("Attend " + attend);
                    attendedClasses = attendedClasses + 1;
                    totalClasses = totalClasses + 1;
                    prog = (int) calcPercentage(attendedClasses, totalClasses);
                    mCircleView.setValueAnimated(prog, 1000);
                    attend_number.setText(String.valueOf(attendedClasses) + "/" + String.valueOf(totalClasses));
                }

            }
        });

        attend_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int try_prog = (int) calcPercentage(attendedClasses - 1, totalClasses - 1);
                if (try_prog >= 0 && try_prog <= 100 && (attendedClasses <= totalClasses) && attendedClasses > (Integer.parseInt(getIntent().getStringExtra("attendedclass")))) {
                    attend = attend - 1;
                    attendtext.setText("Attend " + attend);
                    attendedClasses = attendedClasses - 1;
                    totalClasses = totalClasses - 1;
                    prog = (int) calcPercentage(attendedClasses, totalClasses);
                    mCircleView.setValueAnimated(prog, 1000);
                    attend_number.setText(String.valueOf(attendedClasses) + "/" + String.valueOf(totalClasses));
                }
            }
        });

        miss_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int try_prog = (int) calcPercentage(attendedClasses, totalClasses + 1);
                if (try_prog >= 0 && try_prog <= 100 && (attendedClasses <= totalClasses)) {
                    miss = miss + 1;
                    misstext.setText("Miss " + miss);
                    totalClasses = totalClasses + 1;
                    prog = (int) calcPercentage(attendedClasses, totalClasses);
                    mCircleView.setValueAnimated(prog, 1000);
                    attend_number.setText(String.valueOf(attendedClasses) + "/" + String.valueOf(totalClasses));
                }
            }
        });

        miss_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int try_prog = (int) calcPercentage(attendedClasses, totalClasses - 1);
                if (try_prog >= 0 && try_prog <= 100 && (attendedClasses <= totalClasses) && totalClasses > (Integer.parseInt(getIntent().getStringExtra("totalclass")))) {
                    miss = miss - 1;
                    misstext.setText("Miss " + miss);
                    totalClasses = totalClasses - 1;
                    prog = (int) calcPercentage(attendedClasses, totalClasses);
                    mCircleView.setValueAnimated(prog, 1000);
                    attend_number.setText(String.valueOf(attendedClasses) + "/" + String.valueOf(totalClasses));
                }
            }
        });

    }

    private void setGlobalAdapter() {
        String crscd = getIntent().getStringExtra("crscd");
        String crstp = getIntent().getStringExtra("crstp");
        int flag=0;
        int p=0;
        if(Globals.courseCode.size()>0){
            for (int i = 0; i <Globals.courseCode.size(); i++) {
                if ((Globals.courseCode.get(i).equals(crscd.toString())) && (Globals.courseType.get(i).equals(crstp.toString()))) {
                    flag=1;
                    p=i;
                    break;
                }
            }
            if(flag==0){
                //fetchCoursePage
                fetchCoursePage();
                recyclerView.setAdapter(new RVDetailedAttendanceList(RealmController.with(this).getCoursePage(),
                        RealmController.with(this).getDetailAttendance(), this, true));
            }
            if(flag==1){


                avi.hide();


                recyclerView.setAdapter(new RVDetailedAttendanceList(Globals.couresePages.get(p),
                        Globals.detailAttendances.get(p), this, true));

            }

        }
        else{
            //fetchCoursePage
            fetchCoursePage();
            recyclerView.setAdapter(new RVDetailedAttendanceList(RealmController.with(this).getCoursePage(),
                    RealmController.with(this).getDetailAttendance(), this, true));
        }
    }

    private void addDataToGlobals() {
        String crscd = getIntent().getStringExtra("crscd");
        String crstp = getIntent().getStringExtra("crstp");
        int k=0;
        if(Globals.courseCode.size()>0){
            for (int i = 0; i <Globals.courseCode.size(); i++) {
                if ((Globals.courseCode.get(i).equals(crscd.toString())) && (Globals.courseType.get(i).equals(crstp.toString()))) {
                   k=1;
                    break;
                }
            }
            if(k==0){
                Globals.courseCode.add(crscd);
                Globals.courseType.add(crstp);
            }
        }else{
            Globals.courseCode.add(crscd);
            Globals.courseType.add(crstp);
        }
    }


    private void fetchCoursePage() {
//        progressBar.setVisibility(View.VISIBLE);
        avi.show();
        detailsLayout.setVisibility(View.GONE);

        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.updateCoursepage(Details.this, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        fetchDetailAttendance();
                    }

                    @Override
                    public void onFailure() {
                        avi.hide();
                    }
                });
            }

            @Override
            public void onFailure() {
                avi.hide();
                Toast.makeText(Details.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDetailAttendance() {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.updateDetailAttendance(Details.this, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
//                    Log.d("tagg","success api");
                        avi.hide();
                        detailsLayout.setVisibility(View.VISIBLE);

//                  @@@@@@@@@@@@@@@@@@@ add to globals
                        addDataToGlobals();

                        setGlobalAdapter();
                    }

                    @Override
                    public void onFailure() {
                        avi.hide();
                    }
                });
            }

            @Override
            public void onFailure() {
                avi.hide();
                Toast.makeText(Details.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public float calcPercentage(int attended, int total) {

        float per = ((Float.parseFloat(String.valueOf(attended))) * 100) / (Float.parseFloat(String.valueOf(total)));
        if (per - Math.floor(per) > 0.0) {
            per = (int) per + 1;
        }
        return per;
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }
}
