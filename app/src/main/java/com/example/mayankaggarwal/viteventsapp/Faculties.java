package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesList;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class Faculties extends AppCompatActivity implements TextWatcher {

    private ActionBar actionBar;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TextView textview;
    private EditText search;
    private SwipeRefreshLayout swipeRefreshLayout;
    RVFaculties adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);
        actionBar=getSupportActionBar();

        search=(EditText)findViewById(R.id.search_faculties);

        textview=(TextView)findViewById(R.id.namelongtext);

        search.addTextChangedListener(this);

        String name=Prefs.getPrefs("name",this);
        String str=name.split(" ")[0];
        String finalName=str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();

        textview.setText("Hey "+finalName+", type in the name you are looking for:");

        actionBar.setTitle("Search Faculties");
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        recyclerView=(RecyclerView)findViewById(R.id.faculty_recycler);
        relativeLayout=(RelativeLayout) findViewById(R.id.activity_faculties) ;

        relativeLayout.setBackground(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.facultyrefresh);


        if(!RealmController.with(this).hasFaculty()){
            Prefs.setPrefs("firstFacultyFetch","1",this);
            CustomProgressDialog.showProgress(this,"Fetching Faculties...");
            updateFaculties(this);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateFaculties(Faculties.this);
            }
        });


        adapter=new RVFaculties(RealmController.with(this).getFaculty(),this, true);

        recyclerView.setAdapter(adapter);

    }

    private void updateFaculties(final Activity activity) {

        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.updateFaculty(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
//                    Log.d("tagg","success api");
                        if(Prefs.getPrefs("firstFacultyFetch",Faculties.this).equals("1")){
                            CustomProgressDialog.hideProgress();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setAdapter(new RVFaculties(RealmController.with(activity).getFaculty(), Faculties.this, true));
                    }
                    @Override
                    public void onFailure() {
                        if(Prefs.getPrefs("firstFacultyFetch",Faculties.this).equals("1")){
                            CustomProgressDialog.hideProgress();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure() {
                if(Prefs.getPrefs("firstFacultyFetch",Faculties.this).equals("1")){
                    CustomProgressDialog.hideProgress();
                }
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(Faculties.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            this.adapter.filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
