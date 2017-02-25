package com.example.mayankaggarwal.viteventsapp;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.models.PostParams;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.InternetConnection;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.JsonAdapter;

import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class Details extends AppCompatActivity {

    CircleProgressView mCircleView;
    public int prog=0;
    public int attendedClasses=0;
    public int totalClasses=0;
    int miss=0;
    int attend=0;
//    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout detailsLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mCircleView = (CircleProgressView) findViewById(R.id.circleView);

        recyclerView=(RecyclerView)findViewById(R.id.detail_recycler);

        TextView course_name=(TextView)findViewById(R.id.detail_course_name);
        TextView course_slot=(TextView)findViewById(R.id.course_slot);
        final TextView attend_number=(TextView)findViewById(R.id.atendnumber);
        TextView course_code=(TextView)findViewById(R.id.detail_course_code);
        TextView faculty=(TextView)findViewById(R.id.detail_faculty);

        final TextView misstext=(TextView)findViewById(R.id.miss);
        final TextView attendtext=(TextView)findViewById(R.id.attend);

        ImageButton attend_plus=(ImageButton)findViewById(R.id.attend_plus);
        final ImageButton attend_minus=(ImageButton)findViewById(R.id.attend_minus);
        ImageButton miss_plus=(ImageButton)findViewById(R.id.miss_plus);
        ImageButton miss_minus=(ImageButton)findViewById(R.id.miss_minus);


//        progressDialog=new ProgressDialog(this);
//        progressDialog.setTitle("Fetching Detailed Attendance");
//        progressDialog.setMessage("Loading..");
//        progressDialog.create();
//        progressDialog.setCancelable(false);

        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        detailsLayout=(LinearLayout)findViewById(R.id.detaillistlayout);



        //fetchCoursePage
        fetchCoursePage();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RVDetailedAttendanceList(RealmController.with(this).getCoursePage(),
                RealmController.with(this).getDetailAttendance(),this, true));



        course_name.setText(getIntent().getStringExtra("coursename"));
        course_slot.setText(getIntent().getStringExtra("classroom"));
        course_code.setText(getIntent().getStringExtra("code"));
        faculty.setText(getIntent().getStringExtra("faculty"));
        attend_number.setText(getIntent().getStringExtra("attendedclass")+"/"+getIntent().getStringExtra("totalclass"));

        attendedClasses= Integer.parseInt(getIntent().getStringExtra("attendedclass"));
        totalClasses= Integer.parseInt(getIntent().getStringExtra("totalclass"));


        prog= Integer.parseInt(getIntent().getStringExtra("percentage").replace("%",""));
        mCircleView.setValueAnimated(prog, 1500);

        attend_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int try_prog = (int) calcPercentage(attendedClasses+1 , totalClasses+1 );
//                Log.d("tagg", String.valueOf(try_prog));
                if(try_prog>=0 && try_prog<=100 && (attendedClasses<=totalClasses)) {
                    attend=attend+1;
                    attendtext.setText("Attend "+attend);
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
                int try_prog = (int) calcPercentage(attendedClasses-1 , totalClasses-1 );
//                Log.d("tagg", String.valueOf(try_prog));
                if(try_prog>=0 && try_prog<=100 && (attendedClasses<=totalClasses) && attendedClasses>(Integer.parseInt(getIntent().getStringExtra("attendedclass")))){
                    attend=attend-1;
                    attendtext.setText("Attend "+attend);
                    attendedClasses=attendedClasses-1;
                    totalClasses=totalClasses-1;
                    prog= (int) calcPercentage(attendedClasses,totalClasses);
                    mCircleView.setValueAnimated(prog, 1000);
                    attend_number.setText(String.valueOf(attendedClasses)+"/"+String.valueOf(totalClasses));
                }
            }
        });

        miss_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int try_prog = (int) calcPercentage(attendedClasses , totalClasses+1 );
//                Log.d("tagg", String.valueOf(try_prog));
                if(try_prog>=0 && try_prog<=100 && (attendedClasses<=totalClasses)) {
                    miss=miss+1;
                    misstext.setText("Miss "+miss);
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
                int try_prog = (int) calcPercentage(attendedClasses , totalClasses-1 );
//                Log.d("tagg", String.valueOf(try_prog));
                if(try_prog>=0 && try_prog<=100 && (attendedClasses<=totalClasses) && totalClasses>(Integer.parseInt(getIntent().getStringExtra("totalclass")))){
                    miss=miss-1;
                    misstext.setText("Miss "+miss);
                    totalClasses=totalClasses-1;
                    prog= (int) calcPercentage(attendedClasses,totalClasses);
                    mCircleView.setValueAnimated(prog, 1000);
                    attend_number.setText(String.valueOf(attendedClasses)+"/"+String.valueOf(totalClasses));
                }
            }
        });

//        mCircleView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
//            @Override
//            public void onProgressChanged(float value) {
//
//            }
//        });

//        mCircleView.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
//        mCircleView.setText("Loading...");
//        mCircleView.setOnAnimationStateChangedListener(
//                new AnimationStateChangedListener() {
//                    @Override
//                    public void onAnimationStateChanged(AnimationState _animationState) {
//                        switch (_animationState) {
//                            case IDLE:
//                            case ANIMATING:
//                            case START_ANIMATING_AFTER_SPINNING:
//                                mCircleView.setTextMode(TextMode.PERCENT); // show percent if not spinning
//                                mCircleView.setUnitVisible(mShowUnit);
//                                break;
//                            case SPINNING:
//                                mCircleView.setTextMode(TextMode.TEXT); // show text while spinning
//                                mCircleView.setUnitVisible(false);
//                            case END_SPINNING:
//                                break;
//                            case END_SPINNING_START_ANIMATING:
//                                break;
//
//                        }
//                    }
//                }
//        );

    }

    private void fetchCoursePage() {
//        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        detailsLayout.setVisibility(View.GONE);
        if(InternetConnection.isNetworkAvailable()){

            Data.updateCoursepage(this, new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
//                    progressDialog.dismiss();
                    fetchDetailAttendance();
//                    Log.d("tagg","success api");
                    //recyclerView.setAdapter(new RVAttendaceList(RealmController.with(activity).getAtendance(), MainActivity.this, true));
//                    progressDialog.dismiss();
                    //swipeRefreshLayout.setRefreshing(false);
                }
                @Override
                public void onFailure() {
//                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    //swipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(Details.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
//                    Log.d("tagg","fail api");
                }
            });
        }else{
//            progressDialog.dismiss();
            progressBar.setVisibility(View.GONE);
            //swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(Details.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchDetailAttendance() {
        if(InternetConnection.isNetworkAvailable()){

            Data.updateDetailAttendance(this, new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
//                    Log.d("tagg","success api");
                    recyclerView.setAdapter(new RVDetailedAttendanceList(RealmController.with(Details.this).getCoursePage(),
                            RealmController.with(Details.this).getDetailAttendance(),Details.this, true));
//                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    detailsLayout.setVisibility(View.VISIBLE);
                    //swipeRefreshLayout.setRefreshing(false);
                }
                @Override
                public void onFailure() {
//                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    //swipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(Details.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
//                    Log.d("tagg","fail api");
                }
            });
        }else{
//            progressDialog.dismiss();
            progressBar.setVisibility(View.GONE);
            //swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(Details.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    public float calcPercentage(int attended,int total){

        float per=((Float.parseFloat(String.valueOf(attended)))*100)/(Float.parseFloat(String.valueOf(total)));
        if(per-Math.floor(per)>0.0){
            per=(int)per+1;
        }
        return per;
    }
}
