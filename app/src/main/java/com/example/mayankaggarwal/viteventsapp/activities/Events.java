package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.icu.text.DateFormat;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVEvent;
import com.example.mayankaggarwal.viteventsapp.fragment.PagerAdapterEvents;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.Date;

import io.realm.Realm;

public class Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout linearLayout;
    Boolean flag = true;
    Handler hand = new Handler();
    Runnable r;
    MenuItem gridView;
    Menu menu;
    public static ViewPager viewPager;
    public static TabLayout tabLayout;
    public static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        init();
        deleteOldPref(this);
        setGradientAnimation();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchEvents(Events.this);
            }
        });
    }

    private void init() {
        linearLayout = (LinearLayout) findViewById(R.id.event_layout);
        linearLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        imageView=(ImageView)findViewById(R.id.noclass);
        imageView.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.event_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upcoming Events");

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        recyclerView = (RecyclerView) findViewById(R.id.event_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        viewPager = (ViewPager) findViewById(R.id.viewpagerevents);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        viewPager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        viewPager.setPadding(90, 0, 90, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        viewPager.setPageMargin(40);

        FragmentManager fm = getSupportFragmentManager();
        PagerAdapterEvents pagerAdapter = new PagerAdapterEvents(fm,this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height-height/4));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_event);


        if (Globals.fetchEvent == 0) {
            recyclerView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            fetchEvents(this);
        } else {
            if (Globals.gridorliner == 0) {
                viewPager.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                //setting grid again
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(!(Prefs.getPrefs("eventslist", this).equals("notfound"))){
                    recyclerView.setAdapter(new RVEvent(Prefs.getPrefs("eventslist", this), R.layout.item_event, this));
                }
            }
        }

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
        CustomProgressDialog.showProgress(Events.this, "Fetching Events...");
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.getEvent(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {

                        if(!(Prefs.getPrefs("eventslist", activity).equals("notfound"))){
                            recyclerView.setAdapter(new RVEvent(Prefs.getPrefs("eventslist", activity), R.layout.item_event, activity));

                            FragmentManager fm = getSupportFragmentManager();
                            PagerAdapterEvents pagerAdapter = new PagerAdapterEvents(fm,activity);
                            viewPager.setAdapter(pagerAdapter);

                            CustomProgressDialog.hideProgress();
                            swipeRefreshLayout.setRefreshing(false);

                            Globals.fetchEvent = 1;
                        }

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
                CustomProgressDialog.hideProgress();
                Toast.makeText(Events.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void deleteOldPref(Activity activity) {
        if (!(Prefs.getPrefs("registeredEvents", activity).equals("notfound"))) {

            String str = Prefs.getPrefs("registeredEvents", activity);
            java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String today = simpleDateFormat.format(date);

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(str).getAsJsonArray();
            JsonArray newJsonArray=new JsonArray();

            boolean err=false;

            for (int i = 0; i < jsonArray.size(); i++) {
                try {
                    if (today.compareTo(jsonArray.get(i).getAsJsonObject().get("date").getAsString()) <= 0) {
                        newJsonArray.add(jsonArray.get(i).getAsJsonObject());
                    }
                } catch (Exception e) {
                    err=true;
                    e.printStackTrace();
                }
            }
            if(!err){
                Prefs.setPrefs("registeredEvents", newJsonArray.toString(), activity);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        this.menu = menu;
        gridView = (MenuItem) MenuItemCompat.getActionView(menu.findItem(R.id.action_grid));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_grid) {
            //setting linear layout
            if (Globals.gridorliner == 0) {
                Globals.gridorliner = 1;
                this.menu.getItem(0).setIcon(R.drawable.ic_dns_white_24dp);
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                if(!(Prefs.getPrefs("eventslist", this).equals("notfound"))){
                    recyclerView.setAdapter(new RVEvent(Prefs.getPrefs("eventslist", this), R.layout.item_event, this));
                }
            } else {
                //setting grid again
                Globals.gridorliner = 0;
                this.menu.getItem(0).setIcon(R.drawable.ic_dashboard_white_24dp);
                viewPager.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                FragmentManager fm = getSupportFragmentManager();
                PagerAdapterEvents pagerAdapter = new PagerAdapterEvents(fm,this);
                viewPager.setAdapter(pagerAdapter);
                recyclerView.setVisibility(View.GONE);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        hand.removeCallbacks(r);
    }


    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
        hand.removeCallbacks(r);
    }


}
