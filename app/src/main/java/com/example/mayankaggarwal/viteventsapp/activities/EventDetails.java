package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EventDetails extends AppCompatActivity {

    ProgressBar prog;
    JsonObject e;
    private List<String> fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

         e=Globals.register_event;


        prog=(ProgressBar)findViewById(R.id.progress);
        prog.setVisibility(View.VISIBLE);

        Toolbar toolbar=(Toolbar)findViewById(R.id.ev_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }

        RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.eventcontent);
        ImageView eventImage=(ImageView)findViewById(R.id.eventimage);
        TextView eventName=(TextView)findViewById(R.id.eventname);
        final TextView chapname=(TextView)findViewById(R.id.chapname);
        TextView desc=(TextView)findViewById(R.id.eventdesc);
        TextView date=(TextView)findViewById(R.id.datetext);
        TextView time=(TextView)findViewById(R.id.timetext);
        TextView venue=(TextView)findViewById(R.id.venuetext);
        TextView fee=(TextView)findViewById(R.id.fee);
        CardView reg=(CardView) findViewById(R.id.regcardone);

        reg.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));


        Picasso.with(this).load("https://vitmantra.feedveed.com/posters/"+e.get("_id").getAsString()).into(eventImage, new Callback() {
            @Override
            public void onSuccess() {
                prog.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                prog.setVisibility(View.GONE);
            }
        });

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventDetails.this,ImageFull.class));
            }
        });

        eventName.setText(e.get("eventName").getAsString());
        chapname.setText(e.get("chapterName").getAsString());
        desc.setText(e.get("description").getAsString());
        date.setText(e.get("date").getAsString());
        time.setText(e.get("time").getAsString());
        venue.setText(e.get("venue").getAsString());
        fee.setText(e.get("fees").getAsString());

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int heightImage= (int) (height/2.5);

        RelativeLayout.LayoutParams r=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

        r.setMargins(70,heightImage-60,70,10);

        r.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);

        relativeLayout.setLayoutParams(r);

        eventImage.getLayoutParams().height=heightImage;

        fields = new ArrayList<>();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAlreadyRegistered()){
                    Toast.makeText(EventDetails.this, "Already Registerd", Toast.LENGTH_LONG).show();
                }else {
                    if (e.get("fieldsAndroid").getAsString().length() > 0) {
                        startActivity(new Intent(EventDetails.this, EventRegister.class));
                    } else {
                        registerEvent(e.get("_id").getAsString(), fields, EventDetails.this);
                    }
                }
            }
        });
    }

    private Boolean checkAlreadyRegistered() {
        int flag = 0;
        if (!(Prefs.getPrefs("registeredEvents", EventDetails.this).equals("notfound"))) {
            String str = Prefs.getPrefs("registeredEvents", EventDetails.this);
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

    private void registerEvent(final String id, final List<String> field, final Activity activity) {
        CustomProgressDialog.showProgress(activity,"Registering...");
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
                        CustomProgressDialog.hideProgress();
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
}
