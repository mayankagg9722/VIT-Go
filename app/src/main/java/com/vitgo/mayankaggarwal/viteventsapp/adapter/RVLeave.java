package com.vitgo.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitgo.mayankaggarwal.viteventsapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Created by mayankaggarwal on 23/03/17.
 */

public class RVLeave extends RecyclerView.Adapter<RVLeave.MyViewHolder>  {

    private Context context;
    JsonParser parser;
    JsonArray jsonArray;
    int count=0;

    public RVLeave(String leaves, Activity context) {

        parser=new JsonParser();
        if(leaves!=null){
            jsonArray= (JsonArray) parser.parse(leaves);
            for(JsonElement j:jsonArray){
                if(!(j.getAsJsonObject().get("type").getAsString().toLowerCase().contains("outing"))){
                    count++;
                }
            }
        }
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

        if(!(obj.get("type").getAsString().toLowerCase().contains("outing"))){
            holder.applyid.setText(obj.get("leaveId").getAsString());
            holder.type.setText("Home Town");
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