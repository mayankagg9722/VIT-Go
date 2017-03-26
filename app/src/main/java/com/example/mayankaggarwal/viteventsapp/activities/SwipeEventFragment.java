package com.example.mayankaggarwal.viteventsapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class SwipeEventFragment  extends android.support.v4.app.Fragment {

    private int count=0;

    CardView regcard;
    TextView eventname;
    TextView chapname;
    TextView date;
    TextView going;
    ImageView imageView;
//    private List<EventList> eventList=new ArrayList<>();

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


        final EventList event=Globals.eventList.get(count);

        Picasso.with(getContext()).load("https://vitmantra.feedveed.com/posters/"+event.getId()).into(imageView);

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



}