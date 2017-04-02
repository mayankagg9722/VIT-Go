package com.vitgo.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitgo.mayankaggarwal.viteventsapp.R;

import com.vitgo.mayankaggarwal.viteventsapp.fragment.ExamFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by mayankaggarwal on 24/03/17.
 */

public class RVExamShedule extends RecyclerView.Adapter<RVExamShedule.MyViewHolder> {

    private Context context;

    JsonParser parser;
    JsonArray jsonArray;

    public RVExamShedule(String examSchedule, Activity context) {
        this.context=context;
        parser=new JsonParser();
        if(examSchedule!=null){
            jsonArray=parser.parse(examSchedule).getAsJsonObject().get("CAT1").getAsJsonArray();
        }

        if(jsonArray.size()>0){
            ExamFragment.imageView.setVisibility(View.GONE);
        }else {
            ExamFragment.imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public RVExamShedule.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam_schedule, parent, false);

        return new RVExamShedule.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVExamShedule.MyViewHolder holder, int position) {


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
