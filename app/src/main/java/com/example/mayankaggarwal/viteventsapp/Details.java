package com.example.mayankaggarwal.viteventsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class Details extends AppCompatActivity {

    CircleProgressView mCircleView;
    public int prog=0;
    public int attendedClasses=0;
    public int totalClasses=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mCircleView = (CircleProgressView) findViewById(R.id.circleView);

        TextView course_name=(TextView)findViewById(R.id.detail_course_name);
        TextView course_slot=(TextView)findViewById(R.id.course_slot);
        final TextView attend_number=(TextView)findViewById(R.id.atendnumber);
        TextView course_code=(TextView)findViewById(R.id.detail_course_code);
        TextView faculty=(TextView)findViewById(R.id.detail_faculty);

        ImageButton attend_plus=(ImageButton)findViewById(R.id.attend_plus);
        ImageButton attend_minus=(ImageButton)findViewById(R.id.attend_minus);
        ImageButton miss_plus=(ImageButton)findViewById(R.id.miss_plus);
        ImageButton miss_minus=(ImageButton)findViewById(R.id.miss_minus);



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

    public float calcPercentage(int attended,int total){

        float per=((Float.parseFloat(String.valueOf(attended)))*100)/(Float.parseFloat(String.valueOf(total)));
        if(per-Math.floor(per)>0.0){
            per=(int)per+1;
        }
        return per;
    }
}
