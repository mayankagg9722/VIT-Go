package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by mayankaggarwal on 25/03/17.
 */

public class RVOuting extends RecyclerView.Adapter<RVOuting.MyViewHolder>  {

    private Context context;
    JsonParser parser;
    JsonArray jsonArray;
    int count=0;

    public RVOuting(String leaves, Activity context) {

        parser=new JsonParser();
        jsonArray= (JsonArray) parser.parse(leaves);
        for(JsonElement j:jsonArray){
            if(j.getAsJsonObject().get("type").getAsString().toLowerCase().contains("outing")){
                count++;
            }
        }
//        Log.d("tagg",jsonArray.get(0).getAsJsonObject().get("leaveId").getAsString());
        this.context=context;
    }

    @Override
    public RVOuting.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leave, parent, false);

        return new RVOuting.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVOuting.MyViewHolder holder, int position) {

        JsonObject obj=jsonArray.get(position).getAsJsonObject();
        if(obj.get("type").getAsString().toLowerCase().contains("outing")){
        holder.applyid.setText(obj.get("leaveId").getAsString());
        holder.type.setText("Outing");
        holder.from.setText(obj.get("from").getAsString());
        holder.to.setText(obj.get("to").getAsString());
        holder.status.setText(obj.get("status").getAsString());
    }


    }

    @Override
    public int getItemCount() {
        return count;
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
            type=(TextView)itemView.findViewById(R.id.type);
            from=(TextView)itemView.findViewById(R.id.from);
            to=(TextView)itemView.findViewById(R.id.to);
            status=(TextView)itemView.findViewById(R.id.status);
        }
    }
}