package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksData;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class RVDigitalMarks extends RecyclerView.Adapter<RVDigitalMarks.MyViewHolder> {

    private Context context;

    List<DigitalMarksData> digitalMarksResponseList=new ArrayList<>();

    public RVDigitalMarks(List<DigitalMarksData> digitalMarksDatas, Activity context) {
        this.context=context;

        for(DigitalMarksData d:digitalMarksDatas){
            this.digitalMarksResponseList.add(d);
        }

    }

    @Override
    public RVDigitalMarks.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_digital_marks, parent, false);

        return new RVDigitalMarks.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVDigitalMarks.MyViewHolder holder, final int position) {

        final DigitalMarksData digitalMarks=this.digitalMarksResponseList.get(position);

        holder.title.setText(digitalMarks.getTitle());
        holder.duedate.setText(digitalMarks.getDueDate());
        holder.status.setText(digitalMarks.getStatus());
        holder.score.setText(digitalMarks.getScore());


    }

    @Override
    public int getItemCount() {
        return this.digitalMarksResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,status,duedate,score;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.main_card_digital);
            title=(TextView)itemView.findViewById(R.id.titledigital);
            duedate=(TextView)itemView.findViewById(R.id.duedate);
            status=(TextView)itemView.findViewById(R.id.status);
            score=(TextView)itemView.findViewById(R.id.score);
            cardView.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
        }
    }

}
