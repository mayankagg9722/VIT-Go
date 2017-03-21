package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.MainActivity;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventRegister extends AppCompatActivity {

    private EventList e;
    private int i;
    private List<String> fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Events");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(SetTheme.colorName)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }
        CardView b=(CardView)findViewById(R.id.postreg);
        CardView oneclick=(CardView)findViewById(R.id.oneclick);
        oneclick.setVisibility(View.GONE);
        b.setVisibility(View.GONE);

        e = Globals.register_event;


        if(e.getFields().length()!=0){
            addFloatEditText();
            b.setVisibility(View.VISIBLE);

        }else{
            oneclick.setVisibility(View.VISIBLE);
        }

        fields=new ArrayList<>();

        oneclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEvent(e.getId(),fields, EventRegister.this);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int k=0;k<i;k++){
                    fields.add(((EditText)findViewById(k)).getText().toString());
                    registerEvent(e.getId(),fields,EventRegister.this);
                }
            }
        });
    }

    private void registerEvent(final String id, final List<String> field, final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.getEventRegister(activity,id,field, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
            @Override
            public void onFailure() {
                Toast.makeText(activity,"No Internet",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void addFloatEditText() {

        String f=e.getFields();

        i=0;

        String[] orgF=f.replace("*","").split("//");

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.boxes);

        for(String s:orgF){
            TextInputLayout textInputLayout=new TextInputLayout(this);
            EditText name=new EditText(this);
            name.getBackground().mutate().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
            name.setHint(s);
            name.setId(i);
            textInputLayout.addView(name);
            linearLayout.addView(textInputLayout);
            i++;
        }

    }
}
