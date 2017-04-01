package com.example.mayankaggarwal.viteventsapp.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mayankaggarwal.viteventsapp.R;

public class OpenSource extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);
        Toolbar toolbar = (Toolbar) findViewById(R.id.fac_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Open Source Licenses");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
    }
}
