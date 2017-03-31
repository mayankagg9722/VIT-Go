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
import com.example.mayankaggarwal.viteventsapp.activities.DigitalMarks;
import com.example.mayankaggarwal.viteventsapp.activities.Marks;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class RVDigitalMarks extends RecyclerView.Adapter<RVDigitalMarks.MyViewHolder> {

    private Context context;
    JsonParser parser;
    JsonArray jsonArray;


    public RVDigitalMarks(String digitalMarksDatas, Activity context) {
        this.context=context;
        parser=new JsonParser();
        jsonArray= parser.parse(digitalMarksDatas).getAsJsonArray();

        if(jsonArray.size()>0){
            DigitalMarks.imageView.setVisibility(View.GONE);
        }else {
            DigitalMarks.imageView.setVisibility(View.VISIBLE);
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

        final JsonObject ob= this.jsonArray.get(position).getAsJsonObject();

        holder.title.setText(ob.get("title").getAsString());
        holder.duedate.setText(ob.get("dueDate").getAsString());
        holder.status.setText(ob.get("status").getAsString());
        holder.score.setText(ob.get("score").getAsString());
        holder.uploadstatus.setText(ob.get("uploadStatus").getAsString());

    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,status,duedate,score,uploadstatus;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.main_card_digital);
            title=(TextView)itemView.findViewById(R.id.titledigital);
            duedate=(TextView)itemView.findViewById(R.id.duedate);
            status=(TextView)itemView.findViewById(R.id.status);
            score=(TextView)itemView.findViewById(R.id.score);
            uploadstatus=(TextView)itemView.findViewById(R.id.uploadstatus);
            cardView.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
        }
    }

}
