package com.example.mayankaggarwal.viteventsapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;


import io.realm.Realm;

public class Faculties extends AppCompatActivity implements TextWatcher {

    private ActionBar actionBar;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private TextView textview;
    private EditText search;
    RVFaculties adapter;

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

        Realm.init(this);

        actionBar.setTitle("Search faculties");
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView=(RecyclerView)findViewById(R.id.faculty_recycler);
        linearLayout=(LinearLayout)findViewById(R.id.activity_faculties) ;

        linearLayout.setBackground(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new RVFaculties(RealmController.with(this).getFaculty(),this, true);

        recyclerView.setAdapter(adapter);

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
