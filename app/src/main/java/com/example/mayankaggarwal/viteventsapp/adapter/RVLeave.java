package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.activities.EventDetails;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 23/03/17.
 */

public class RVLeave extends RecyclerView.Adapter<RVLeave.MyViewHolder> {

    private Context context;
    JsonParser parser;
    JsonArray jsonArray;

    public RVLeave(String leaves, Activity context) {

        parser=new JsonParser();
        jsonArray= (JsonArray) parser.parse(leaves);
//        Log.d("tagg",jsonArray.get(0).getAsJsonObject().get("leaveId").getAsString());
        this.context=context;
    }

    @Override
    public RVLeave.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leave, parent, false);

        return new RVLeave.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVLeave.MyViewHolder holder, int position) {

        JsonObject obj=jsonArray.get(position).getAsJsonObject();

        holder.applyid.setText(obj.get("leaveId").getAsString());
//        holder.type.setText(obj.get("type").getAsString());
        holder.from.setText(obj.get("from").getAsString());
        holder.to.setText(obj.get("to").getAsString());
//        holder.status.setText(obj.get("status").getAsString());

    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView applyid;
        TextView from;
        TextView to;
        TextView status;
        TextView type;

        public MyViewHolder(View itemView) {
            super(itemView);
            applyid=(TextView)itemView.findViewById(R.id.leaveid);
            type=(TextView)itemView.findViewById(R.id.from);
            from=(TextView)itemView.findViewById(R.id.to);
            to=(TextView)itemView.findViewById(R.id.type);
            status=(TextView)itemView.findViewById(R.id.status);
        }
    }
}