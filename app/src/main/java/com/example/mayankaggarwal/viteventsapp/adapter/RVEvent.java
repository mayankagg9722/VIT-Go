package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.MainActivity;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.activities.EventDetails;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by mayankaggarwal on 18/03/17.
 */

public class RVEvent extends RecyclerView.Adapter<RVEvent.MyViewHolder> {

    private Context context;
    private List<EventList> eventList=new ArrayList<>();
    int item;


    public RVEvent(List<EventList> eventLists,int item_event, Activity context) {
        this.context=context;
        this.item=item_event;

        for(EventList e:eventLists){
            this.eventList.add(e);
        }

    }

    @Override
    public RVEvent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(item, parent, false);

        return new RVEvent.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final EventList event=this.eventList.get(position);

        holder.eventname.setText(event.getEventName());
        holder.chapname.setText(event.getChapterName());
        holder.date.setText(event.getDate());
        holder.going.setText(event.getGoing()+" Going");

        holder.regcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, EventDetails.class);
                Globals.register_event=event;
                context.startActivity(intent);
            }
        });

        holder.event_item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, EventDetails.class);
                Globals.register_event=event;
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView regcard;
        CardView event_item_card;
        TextView eventname;
        TextView chapname;
        TextView date;
        TextView going;

        public MyViewHolder(View itemView) {
            super(itemView);
            regcard=(CardView)itemView.findViewById(R.id.regcard);
            event_item_card=(CardView)itemView.findViewById(R.id.event_item_card);
            eventname=(TextView)itemView.findViewById(R.id.event_name);
            chapname=(TextView)itemView.findViewById(R.id.chapter_name);
            date=(TextView)itemView.findViewById(R.id.date_event);
            going=(TextView)itemView.findViewById(R.id.event_going);

        }
    }
}
