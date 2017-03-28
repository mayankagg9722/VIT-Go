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
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class EventDetails extends AppCompatActivity {

    ProgressBar prog;
    EventList e;
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
        TextView chapname=(TextView)findViewById(R.id.chapname);
        TextView desc=(TextView)findViewById(R.id.eventdesc);
        TextView date=(TextView)findViewById(R.id.datetext);
        TextView time=(TextView)findViewById(R.id.timetext);
        TextView venue=(TextView)findViewById(R.id.venuetext);
        TextView fee=(TextView)findViewById(R.id.fee);
        CardView reg=(CardView) findViewById(R.id.regcardone);

        reg.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));

//        Data.DownloadImageTask download=new Data.DownloadImageTask(eventImage,this);
//        download.execute("https://vitmantra.feedveed.com/posters/"+e.getId());

        Picasso.with(this).load("https://vitmantra.feedveed.com/posters/"+e.getId()).into(eventImage, new Callback() {
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

        eventName.setText(e.getEventName());
        chapname.setText(e.getChapterName());
        desc.setText(e.getDescription());
        date.setText(e.getDate());
        time.setText(e.getTime());
        venue.setText(e.getVenue());
        fee.setText(e.getFees());

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
                if (e.getFields().length() > 0) {
                    startActivity(new Intent(EventDetails.this,EventRegister.class));
                } else {
                    registerEvent(e.getId(), fields, EventDetails.this);
                }
            }
        });
    }

    private void registerEvent(final String id, final List<String> field, final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(activity,"Registering...");
                Data.getEventRegister(activity, id, field, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        Toast.makeText(activity, "Success!!", Toast.LENGTH_LONG).show();
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
                Toast.makeText(activity, "No Internet", Toast.LENGTH_LONG).show();
            }
        });
    }
}
