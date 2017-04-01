package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.fragment.ExamCatTwo;
import com.example.mayankaggarwal.viteventsapp.fragment.ExamFat;
import com.example.mayankaggarwal.viteventsapp.fragment.ExamFragment;
import com.example.mayankaggarwal.viteventsapp.fragment.ViewPagerAdapter;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;


import static com.example.mayankaggarwal.viteventsapp.activities.TimeTable.viewPager;

public class ExamSchedule extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPagerAdapter viewpageradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.exam_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exam Schedule");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        viewPager = (ViewPager) findViewById(R.id.exampager);
        viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpageradapter.addfragment(new ExamFragment(), "CAT 1");
        viewpageradapter.addfragment(new ExamCatTwo(), "CAT 2");
        viewpageradapter.addfragment(new ExamFat(), "FAT");
        viewPager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        fetchExamSchedule(this);

    }

    private void fetchExamSchedule(final Activity activity) {
        CustomProgressDialog.showProgress(activity,"Fetching Schedule...");
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.getExamShedule(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        viewPager.setAdapter(viewpageradapter);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        CustomProgressDialog.hideProgress();
                    }
                });
            }

            @Override
            public void onFailure() {
                CustomProgressDialog.hideProgress();
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
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
