package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.adapter.RVFaculties;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

public class Faculties extends AppCompatActivity implements TextWatcher {

    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TextView textview;
    private EditText search;
    private SwipeRefreshLayout swipeRefreshLayout;
    RVFaculties adapter;
    public static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);

        init();


        if (Globals.firstCallFaculty == 0) {
            updateFaculties(this);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateFaculties(Faculties.this);
            }
        });


        if (!(Prefs.getPrefs("facultiesListJson", this).equals("notfound"))) {
            adapter = new RVFaculties(Prefs.getPrefs("facultiesListJson", this), this, true);
            recyclerView.setAdapter(adapter);
        }


    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.fac_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Faculties");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        imageView=(ImageView)findViewById(R.id.noclass);
        imageView.setVisibility(View.VISIBLE);

        search = (EditText) findViewById(R.id.search_faculties);

        textview = (TextView) findViewById(R.id.namelongtext);

        search.addTextChangedListener(this);

        String name = Prefs.getPrefs("name", this);
        String str = name.split(" ")[0];
        String finalName = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();

        textview.setText("Hey " + finalName + ", type in the name you are looking for:");

        recyclerView = (RecyclerView) findViewById(R.id.faculty_recycler);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_faculties);

        relativeLayout.setBackground(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.facultyrefresh);

        hideSoftKeyboard();
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void updateFaculties(final Activity activity) {
        CustomProgressDialog.showProgress(activity, "Fetching faculties...");
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.updateFaculty(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        CustomProgressDialog.hideProgress();
                        finish();
                        startActivity(getIntent());
                        Globals.firstCallFaculty = 1;
                        swipeRefreshLayout.setRefreshing(false);
                        search.addTextChangedListener(Faculties.this);
                        if (!(Prefs.getPrefs("facultiesListJson", activity).equals("notfound"))) {
                            adapter = new RVFaculties(Prefs.getPrefs("facultiesListJson", activity), activity, true);
                            recyclerView.setAdapter(adapter);
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
        if(!(Prefs.getPrefs("facultiesListJson", this).equals("notfound"))){
            this.adapter.filter(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
