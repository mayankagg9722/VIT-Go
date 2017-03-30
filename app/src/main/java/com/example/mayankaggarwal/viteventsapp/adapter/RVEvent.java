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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by mayankaggarwal on 18/03/17.
 */

public class RVEvent extends RecyclerView.Adapter<RVEvent.MyViewHolder> {

    private Activity context;
//    private List<EventList> eventList = new ArrayList<>();
    int item;
    JsonParser parser;
    JsonArray jsonArray;


    public RVEvent(String eventLists, int item_event, Activity context) {
        this.context = context;
        this.item = item_event;

        parser=new JsonParser();

        jsonArray=parser.parse(eventLists).getAsJsonArray();

//        for (EventList e : eventLists) {
//            this.eventList.add(e);
//        }

        this.context = context;

    }

    @Override
    public RVEvent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(item, parent, false);

        return new RVEvent.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final JsonObject event = this.jsonArray.get(position).getAsJsonObject();

        holder.eventname.setText(event.get("eventName").getAsString());
        holder.chapname.setText(event.get("chapterName").getAsString());
        holder.date.setText(event.get("date").getAsString());
        holder.going.setText(event.get("going").getAsString() + " Going");

        if (checkAlreadyRegistered(this.context, event)) {
            holder.regcard.setVisibility(View.GONE);
            holder.tickcard.setVisibility(View.VISIBLE);
        } else {
            holder.tickcard.setVisibility(View.GONE);
            holder.regcard.setVisibility(View.VISIBLE);
        }

        holder.regcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetails.class);
                Globals.register_event = event;
                context.startActivity(intent);
            }
        });

        holder.event_item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetails.class);
                Globals.register_event = event;
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView regcard, tickcard;
        CardView event_item_card;
        TextView eventname;
        TextView chapname;
        TextView date;
        TextView going;

        public MyViewHolder(View itemView) {
            super(itemView);
            regcard = (CardView) itemView.findViewById(R.id.regcard);
            tickcard = (CardView) itemView.findViewById(R.id.tickcard);
            event_item_card = (CardView) itemView.findViewById(R.id.event_item_card);
            eventname = (TextView) itemView.findViewById(R.id.event_name);
            chapname = (TextView) itemView.findViewById(R.id.chapter_name);
            date = (TextView) itemView.findViewById(R.id.date_event);
            going = (TextView) itemView.findViewById(R.id.event_going);
            tickcard.setVisibility(View.GONE);
            regcard.setVisibility(View.GONE);

        }
    }

    private Boolean checkAlreadyRegistered(Activity activity, JsonObject event) {
        int flag = 0;
        if (!(Prefs.getPrefs("registeredEvents", activity).equals("notfound"))) {
            String str = Prefs.getPrefs("registeredEvents", activity);
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(str).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                if (event.get("_id").getAsString().equals(jsonArray.get(i).getAsJsonObject().get("id").getAsString())) {
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
