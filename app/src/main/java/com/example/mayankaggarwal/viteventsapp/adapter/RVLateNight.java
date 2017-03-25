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
import com.example.mayankaggarwal.viteventsapp.models.LateNightData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 25/03/17.
 */

public class RVLateNight extends RecyclerView.Adapter<RVLateNight.MyViewHolder> {

    private Context context;
    private List<LateNightData> lateNightDataList=new ArrayList<>();


    public RVLateNight(List<LateNightData> lateNightDatas, Activity context) {
        this.context=context;

        for(LateNightData e:lateNightDatas){
            this.lateNightDataList.add(e);
        }

    }

    @Override
    public RVLateNight.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leave, parent, false);

        return new RVLateNight.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVLateNight.MyViewHolder holder, int position) {

        final LateNightData late=this.lateNightDataList.get(position);

        holder.applyid.setText(late.getVenue());
        holder.type.setText("Late Night");
        holder.from.setText(late.getFrom());
        holder.to.setText(late.getTo());
        holder.status.setText(late.getStatus());

    }

    @Override
    public int getItemCount() {
        return this.lateNightDataList.size();
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
