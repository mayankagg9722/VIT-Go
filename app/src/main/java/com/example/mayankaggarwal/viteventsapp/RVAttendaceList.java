package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mayankaggarwal on 13/02/17.
 */

public class RVAttendaceList extends RecyclerView.Adapter<RVAttendaceList.MyViewHolder> {

    private List<AttendanceList> attendanceList;
    private Activity context;
    Boolean clickable;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView percentage;
        public TextView course_name;
        public TextView course_code;
        public TextView slot;
        public TextView course_type;
        public TextView slotTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            percentage= (TextView) itemView.findViewById(R.id.attendance_percentage);
            course_name= (TextView) itemView.findViewById(R.id.course_name);
            course_type= (TextView) itemView.findViewById(R.id.type);
            course_code= (TextView) itemView.findViewById(R.id.classroom);
        }
    }

    public RVAttendaceList(List<AttendanceList> atendance, Activity context, boolean clickable) {
        this.attendanceList=new ArrayList<>();
        for(AttendanceList a:atendance){
            this.attendanceList.add(a);
        }
        this.context=context;
        this.clickable=clickable;

    }


    @Override
    public RVAttendaceList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new RVAttendaceList.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVAttendaceList.MyViewHolder holder, int position) {
       AttendanceList attendanceList=this.attendanceList.get(position);
        int per=((Integer.parseInt(attendanceList.getAttended()))*100)/(Integer.parseInt(attendanceList.getTotalClasses()));
        holder.percentage.setText(String.valueOf(per));
        holder.course_name.setText(attendanceList.getCourseName());
        holder.course_code.setText(attendanceList.getCourseCode());
        if(attendanceList.getCourseType().contains("Theory")){
            holder.course_type.setText("Theory");
        }
        else if(attendanceList.getCourseType().equals("Soft Skill")){
            holder.course_type.setText("Soft Skills");
        }else{
            holder.course_type.setText("Lab");
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }


}
