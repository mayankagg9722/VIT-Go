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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 24/03/17.
 */

public class RVExamFat extends RecyclerView.Adapter<RVExamFat.MyViewHolder> {

    private Context context;

    JsonParser parser;
    JsonArray jsonArray;

    public RVExamFat(String examSchedule, Activity context) {
        this.context=context;

        parser=new JsonParser();

        if(examSchedule!=null){
            jsonArray=parser.parse(examSchedule).getAsJsonObject().get("fat").getAsJsonArray();
        }

    }

    @Override
    public RVExamFat.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam_schedule, parent, false);

        return new RVExamFat.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVExamFat.MyViewHolder holder, int position) {


        JsonObject object=jsonArray.get(position).getAsJsonObject();

        holder.course_name.setText(object.get("subjectName").getAsString());
        holder.date.setText(object.get("date").getAsString());
        holder.classroom.setText(object.get("venue").getAsString());
        holder.day.setText(object.get("day").getAsString());
        holder.tableno.setText(object.get("tableNumber").getAsString());
        holder.slottime.setText(object.get("time").getAsString());
        holder.type.setText(object.get("courseType").getAsString());
    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course_name,date,classroom,slottime,type,tableno,day;

        public MyViewHolder(View itemView) {
            super(itemView);
            course_name=(TextView)itemView.findViewById(R.id.course_name);
            date=(TextView)itemView.findViewById(R.id.date);
            classroom=(TextView)itemView.findViewById(R.id.classroom);
            slottime=(TextView)itemView.findViewById(R.id.slottime);
            type=(TextView)itemView.findViewById(R.id.type);
            tableno=(TextView)itemView.findViewById(R.id.tableno);
            day=(TextView)itemView.findViewById(R.id.day);
        }

    }

}