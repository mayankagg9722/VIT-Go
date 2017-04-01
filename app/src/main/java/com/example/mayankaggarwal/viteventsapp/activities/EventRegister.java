package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.List;

public class EventRegister extends AppCompatActivity {

    private JsonObject e;
    private int i;
    private List<String> fields;
    CardView b;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        init();

        e = Globals.register_event;

        addFloatEditText();
        b.setVisibility(View.VISIBLE);


        fields = new ArrayList<>();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAlreadyRegistered(EventRegister.this)){
                    Toast.makeText(EventRegister.this, "Already Registerd", Toast.LENGTH_LONG).show();
                }else {
                    for (int k = 0; k < i; k++) {
                        fields.add(((EditText) findViewById(k)).getText().toString());
                    }
                    registerEvent(e.get("_id").getAsString(), fields, EventRegister.this);
                }
            }
        });

    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register Events");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(SetTheme.colorName)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }
        relativeLayout=(RelativeLayout)findViewById(R.id.event_register);
//        relativeLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        b = (CardView) findViewById(R.id.postreg);
        b.setVisibility(View.GONE);
    }

    private void addFloatEditText() {
        String f = e.get("fieldsAndroid").getAsString();
        i = 0;
        String[] orgF = f.replace("*", "").split("//");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.boxes);

        for (String s : orgF) {
            TextInputLayout textInputLayout = new TextInputLayout(this);
//          textInputLayout.setHintTextAppearance(R.style.income);
            EditText name = new EditText(this);
            name.setTextColor(Color.parseColor("#000000"));
//            name.getBackground().mutate().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
            name.setHint(s);
            name.setId(i);
            textInputLayout.addView(name);
            linearLayout.addView(textInputLayout);
            i++;
        }

    }

    private void registerEvent(final String id, final List<String> field, final Activity activity) {
        CustomProgressDialog.showProgress(activity, "Registering...");
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.getEventRegister(activity, id, field, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        storeEventPref(activity);
                        Toast.makeText(activity, "Successfully Registered!!", Toast.LENGTH_LONG).show();
                        CustomProgressDialog.hideProgress();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(activity, "No Internet", Toast.LENGTH_LONG).show();
                        CustomProgressDialog.hideProgress();
                    }
                });
            }

            @Override
            public void onFailure() {
                CustomProgressDialog.hideProgress();
                Toast.makeText(activity, "No Internet", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void storeEventPref(Activity activity) {
        try{
            if (!(Prefs.getPrefs("registeredEvents", activity).equals("notfound"))) {
                String str = Prefs.getPrefs("registeredEvents", activity);
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArray = jsonParser.parse(str).getAsJsonArray();
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("id",e.get("_id").getAsString());
                jsonObject.addProperty("date",e.get("date").getAsString());
                jsonArray.add(jsonObject);
                Prefs.setPrefs("registeredEvents",jsonArray.toString(),activity);
            }else {
                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("id",e.get("_id").getAsString());
                jsonObject.addProperty("date",e.get("date").getAsString());
                jsonArray.add(jsonObject);
                Prefs.setPrefs("registeredEvents",jsonArray.toString(),activity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private Boolean checkAlreadyRegistered(Activity activity) {
        int flag = 0;
        if (!(Prefs.getPrefs("registeredEvents", activity).equals("notfound"))) {
            String str = Prefs.getPrefs("registeredEvents", activity);
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(str).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                if (e.get("_id").getAsString().equals(jsonArray.get(i).getAsJsonObject().get("id").getAsString())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
