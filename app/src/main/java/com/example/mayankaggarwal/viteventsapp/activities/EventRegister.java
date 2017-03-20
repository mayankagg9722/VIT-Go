package com.example.mayankaggarwal.viteventsapp.activities;

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

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventRegister extends AppCompatActivity {

    private EventList e;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Events");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(SetTheme.colorName)));
        CardView b=(CardView)findViewById(R.id.postreg);

        e = Globals.register_event;

        Log.d("tagg", String.valueOf(e.getFields().length()));


        if(e.getFields().length()!=0){
            addFloatEditText();
        }else{
            //redirect directly
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirect
//                Log.d("tagg",((EditText)findViewById(Integer.parseInt("2"))).getText().toString());
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
