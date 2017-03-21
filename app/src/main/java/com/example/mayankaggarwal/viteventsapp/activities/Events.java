package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVEvent;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import io.realm.Realm;

public class Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;
    Boolean flag = true;
    Handler hand = new Handler();
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        linearLayout = (LinearLayout) findViewById(R.id.event_layout);
        linearLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        Toolbar toolbar = (Toolbar) findViewById(R.id.event_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upcoming Events");

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        recyclerView = (RecyclerView) findViewById(R.id.event_recycler);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_event);

        if(getIntent().getStringExtra("eventid")!=null){
            EventList ev=RealmController.with(this).getEvent(getIntent().getStringExtra("eventid"));
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ev.setGoing(String.valueOf((Integer.parseInt(ev.getGoing())+1)));
            realm.commitTransaction();
            realm.close();
        }

        if(Globals.fetchEvent==0){
            fetchEvents(this);
            Globals.fetchEvent=1;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new RVEvent(RealmController.with(this).getEvents(), Events.this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchEvents(Events.this);
            }
        });

        setGradientAnimation();

    }

    private void setGradientAnimation() {
        int dark = manipulateColor(Color.parseColor(SetTheme.colorName), 0.6f);
        int light = manipulateColor(Color.parseColor(SetTheme.colorName), 1.4f);

        ColorDrawable[] color = {new ColorDrawable(light), new ColorDrawable(Color.parseColor(SetTheme.colorName)), new ColorDrawable(dark)};
        TransitionDrawable trans = new TransitionDrawable(color);
        linearLayout.setBackground(trans);
        trans.startTransition(3000);
        repeatTransition(trans);
    }

    void repeatTransition(final TransitionDrawable trans) {
        r = new Runnable() {
            @Override
            public void run() {
                if (flag) {
//                    Log.d("tagg", "straight");
                    trans.startTransition(3000);
                    flag = false;
                } else {
//                    Log.d("tagg", "reverse");
                    trans.reverseTransition(3000);
                    flag = true;
                }
                hand.postDelayed(this, 6000);
            }
        };
        hand.post(r);
    }


    public int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round((Color.red(color)) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        int c = Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
        return c;
    }

//    ##########  Value Animator Animation For Gradient   ###############
//
//    private void changeBackground(final int light, final int dark) {
//        ValueAnimator anim = new ValueAnimator();
//        anim.setIntValues(light, dark);
//        anim.setEvaluator(new ArgbEvaluator());
//
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                Log.d("tagg", valueAnimator.getAnimatedValue().toString());
//                linearLayout.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
//            }
//        });
//        anim.setDuration(5000);
//        anim.setRepeatCount(ValueAnimator.INFINITE);
//        anim.start();
//    }
//
//    #############  (HSV color model change)  #################
//
//    public int manipulateHSVColor(int color, float factor) {
//        float[] hsv = new float[3];
//        int color = Color.parseColor(SetTheme.colorName);
//        Color.colorToHSV(color, hsv);
//        hsv[2] =0.8f * hsv[2];
//        int dark = Color.HSVToColor(hsv);
//
//
//        float[] hsv1 = new float[3];
//        int color1 = Color.parseColor(SetTheme.colorName);
//        Color.colorToHSV(color1, hsv1);
//
////        hsv1[2] = 1.0f - 0.8f * (1.0f - hsv1[2]);
//        hsv[2] =1.5f * hsv[2];
//        int light = Color.HSVToColor(hsv1);
//
//        changeBackground(light, dark);
//    }


    private void fetchEvents(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(Events.this, "Fetching Events...");
                Data.getEvent(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        recyclerView.setAdapter(new RVEvent(RealmController.with(activity).getEvents(), Events.this));
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure() {
                        CustomProgressDialog.hideProgress();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(Events.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hand.removeCallbacks(r);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hand.removeCallbacks(r);
    }

    @Override
    public boolean onNavigateUp() {
        hand.removeCallbacks(r);
        return super.onNavigateUp();
    }
}
