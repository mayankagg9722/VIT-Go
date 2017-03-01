package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;
import com.example.mayankaggarwal.viteventsapp.models.CouresePage;
import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 17/02/17.
 */

public class RVDetailedAttendanceList extends RecyclerView.Adapter<RVDetailedAttendanceList.MyViewHolder> {


    public List<CouresePage> couresePages;
    public List<DetailAttendance> detailAttendances;
    public Activity context;
    Boolean clickable;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public List<CouresePage> couresePages;
        public List<DetailAttendance> detailAttendances;
        public TextView detail_attendance;
        public TextView detail_date;
        public TextView sno;

        public MyViewHolder(View itemView) {
            super(itemView);
            detail_date = (TextView) itemView.findViewById(R.id.detail_date);
            detail_attendance = (TextView) itemView.findViewById(R.id.detail_attendance);
            sno = (TextView) itemView.findViewById(R.id.sno);

        }

    }

    public  RVDetailedAttendanceList(List<CouresePage> couresePages, List<DetailAttendance> detailAttendances, Activity context, boolean clickable){

        this.couresePages = new ArrayList<>();
        this.detailAttendances = new ArrayList<>();

        for (CouresePage a : couresePages) {
            this.couresePages.add(a);
        }

        for (DetailAttendance a : detailAttendances) {
            this.detailAttendances.add(a);
        }


        this.context = context;
        this.clickable = clickable;
    }


    @Override
    public RVDetailedAttendanceList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_item_layout, parent, false);

        return new RVDetailedAttendanceList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RVDetailedAttendanceList.MyViewHolder holder, int position) {

        CouresePage couresePage = this.couresePages.get(position);
        holder.detail_date.setText(couresePage.getDate());
        holder.sno.setText(String.valueOf(position+1));
        holder.sno.setTextColor(Color.parseColor("#F44336"));


            for(DetailAttendance d:this.detailAttendances){
                if(d.getDate().toString().toLowerCase().equals(holder.detail_date.getText().toString().toLowerCase())){
                    if (d.getStatus().toString().toLowerCase().equals("absent")) {
                        holder.detail_attendance.setText(d.getStatus());
                        holder.detail_attendance.setTextColor(Color.parseColor("#f37051"));

                    }else if(d.getStatus().toString().toLowerCase().equals("present")){
                        holder.detail_attendance.setText(d.getStatus());
                        holder.detail_attendance.setTextColor(Color.parseColor("#1ae24b"));
                    }
                    break;
                }else{
                    holder.detail_attendance.setText("");
                    holder.detail_attendance.setTextColor(Color.parseColor("#000000"));
                }
            }
        }


    @Override
    public int getItemCount() {
        return couresePages.size();
    }
}
