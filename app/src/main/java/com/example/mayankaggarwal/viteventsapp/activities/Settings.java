package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.SplashSlider;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {


    FloatingActionButton one, two, three, four, five, six, seven, eight, nine;
    CardView logout,about,privacy,opensource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);
        init();
        setClickListener(this);
    }

    private void setClickListener(final Activity activity) {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "1", activity);
                SetTheme.changeToTheme(activity, SetTheme.ONE);
                setTheme(R.style.one);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "2", activity);
                SetTheme.changeToTheme(activity, SetTheme.TWO);
                setTheme(R.style.two);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "3", activity);
                SetTheme.changeToTheme(activity, SetTheme.THREE);
                setTheme(R.style.three);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "4", activity);
                SetTheme.changeToTheme(activity, SetTheme.FOUR);
                setTheme(R.style.four);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "5", activity);
                SetTheme.changeToTheme(activity, SetTheme.FIVE);
                setTheme(R.style.five);

            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "6", activity);
                SetTheme.changeToTheme(activity, SetTheme.SIX);
                setTheme(R.style.six);

            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "7", activity);
                SetTheme.changeToTheme(activity, SetTheme.SEVEN);
                setTheme(R.style.seven);

            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "8", activity);
                SetTheme.changeToTheme(activity, SetTheme.EIGHT);
                setTheme(R.style.eight);
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.setPrefs("theme", "9", activity);
                SetTheme.changeToTheme(activity, SetTheme.NINE);
                setTheme(R.style.nine);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmController.with(activity).clearAll();
                Prefs.deletePrefs(activity);
                settingGlobalback();
                activity.finishAffinity();
                activity.startActivity(new Intent(activity, SplashSlider.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,AboutUs.class));
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,PrivacyPolicy.class));
            }
        });

        opensource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,OpenSource.class));
            }
        });

    }

    private void settingGlobalback() {
        Globals.couresePages = new ArrayList<>();
        Globals.singleCopydetailAttendances = new ArrayList<>();
        Globals.singleCopycouresePages = new ArrayList<>();
        Globals.detailAttendances = new ArrayList<>();
        Globals.attendanceListSize = 0;
        Globals.courseCodeDaySize = 0;
        Globals.digitalAssignmentMarks = new ArrayList<>();
        Globals.digitalCourseCode = new ArrayList<>();
        Globals.digitalCourseType = new ArrayList<>();
        Globals.courseCode = new ArrayList<>();
        Globals.courseType = new ArrayList<>();
        Globals.doneFetching = 0;
        Globals.faculty_email = null;
        Globals.faculty_openhours = null;
        Globals.faculty_venue = null;
        Globals.faculty_designation = null;
        Globals.faculty_intercom = null;
        Globals.faculty_openhours = new ArrayList<>();
        Globals.register_event = null;
        Globals.fetchEvent = 0;
        Globals.fetchAssignment = 0;
        Globals.fetchMarks = 0;
        Globals.fetchMessage = 0;
        Globals.dayName = null;
        Globals.firstCallFaculty = 0;
        Globals.gridorliner = 0;
        Globals.eventNumber = 0;
        Globals.averageAttendanceSize = 0;
    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        one = (FloatingActionButton) findViewById(R.id.one);
        two = (FloatingActionButton) findViewById(R.id.two);
        three = (FloatingActionButton) findViewById(R.id.three);
        four = (FloatingActionButton) findViewById(R.id.four);
        five = (FloatingActionButton) findViewById(R.id.five);
        six = (FloatingActionButton) findViewById(R.id.six);
        seven = (FloatingActionButton) findViewById(R.id.seven);
        eight = (FloatingActionButton) findViewById(R.id.eight);
        nine = (FloatingActionButton) findViewById(R.id.nine);
        about=(CardView)findViewById(R.id.aboutus);
        privacy=(CardView)findViewById(R.id.privacypolicy);
        opensource=(CardView)findViewById(R.id.opensource);

        logout = (CardView) findViewById(R.id.logout);
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
