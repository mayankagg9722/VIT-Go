package com.example.mayankaggarwal.viteventsapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.activities.EventDetails;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class SwipeEventFragment  extends android.support.v4.app.Fragment {

    private int count=0;

    CardView regcard,tickcard;
    TextView eventname;
    TextView chapname;
    TextView date;
    TextView going;
    ImageView imageView;
    private List<EventList> eventList=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        count=bundle.getInt("count");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_swipe_event,container,false);
        regcard=(CardView)view.findViewById(R.id.event_item_card);
        eventname=(TextView)view.findViewById(R.id.event_name);
        chapname=(TextView)view.findViewById(R.id.chapter_name);
        date=(TextView)view.findViewById(R.id.date_event);
        going=(TextView)view.findViewById(R.id.event_going);
        imageView=(ImageView)view.findViewById(R.id.bigeventimage);
        tickcard=(CardView)view.findViewById(R.id.tickcard);
        tickcard.setVisibility(View.GONE);


        final EventList event=Globals.getEventList(getActivity()).get(count);

        Picasso.with(getContext()).load("https://vitmantra.feedveed.com/posters/"+event.getId()).into(imageView);

        if(checkAlreadyRegistered(getActivity(),event)){
            tickcard.setVisibility(View.VISIBLE);
        }else {
            tickcard.setVisibility(View.GONE);
        }

        eventname.setText(event.getEventName());
        chapname.setText(event.getChapterName());
        date.setText(event.getDate());
        going.setText(event.getGoing()+" Going");
        regcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EventDetails.class);
                Globals.register_event=event;
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    private Boolean checkAlreadyRegistered(Activity activity, EventList event) {
        int flag = 0;
        if (!(Prefs.getPrefs("registeredEvents", activity).equals("notfound"))) {
            String str = Prefs.getPrefs("registeredEvents", activity);
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(str).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                if (event.getId().equals(jsonArray.get(i).getAsJsonObject().get("id").getAsString())) {
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