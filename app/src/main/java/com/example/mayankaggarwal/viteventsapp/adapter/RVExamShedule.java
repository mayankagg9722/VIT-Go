package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.CAT1;
import com.example.mayankaggarwal.viteventsapp.models.CAT2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 24/03/17.
 */

public class RVExamShedule extends RecyclerView.Adapter<RVExamShedule.MyViewHolder> {

    private Context context;
    private List<CAT1> cat1ArrayList=new ArrayList<>();
    private List<CAT2> cat2ArrayList=new ArrayList<>();


    public RVExamShedule(List<CAT1> cat1s, Activity context) {
        this.context=context;

        for(CAT1 c:cat1s){
            cat1ArrayList.add(c);
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

        final CAT1 cat=this.cat1ArrayList.get(position);

        holder.course_name.setText(cat.getSubjectName());
        holder.date.setText(cat.getDate());
        holder.classroom.setText(cat.getVenue());
        holder.day.setText(cat.getDay());
        holder.tableno.setText(cat.getTableNumber());
        holder.slottime.setText(cat.getTime());
        holder.type.setText(cat.getCourseCode());
    }

    @Override
    public int getItemCount() {
        return this.cat1ArrayList.size();
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
