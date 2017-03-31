package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.activities.DigitalAssignment;
import com.example.mayankaggarwal.viteventsapp.activities.DigitalMarks;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class RVDigitalAssignment extends RecyclerView.Adapter<RVDigitalAssignment.MyViewHolder> {

    private Context context;

    JsonParser parser;
    JsonArray jsonArray;

    public RVDigitalAssignment(String digitalAssignment, Activity context) {
        this.context=context;
        parser=new JsonParser();
        if(digitalAssignment!=null){
            jsonArray=parser.parse(digitalAssignment).getAsJsonObject().get("data").getAsJsonArray();
        }

        if(jsonArray.size()>0){
            DigitalAssignment.imageView.setVisibility(View.GONE);
        }else {
            DigitalAssignment.imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public RVDigitalAssignment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_digital_assignment, parent, false);

        return new RVDigitalAssignment.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVDigitalAssignment.MyViewHolder holder, final int position) {

        final JsonObject object=jsonArray.get(position).getAsJsonObject();
        final JsonArray postParam=object.get("post_parameters").getAsJsonArray();

        holder.course_name.setText(object.get("Course Title").getAsString());
        holder.coursecode.setText(object.get("Course Code").getAsString());
        holder.facultyname.setText(object.get("Faculty").getAsString());
        holder.type.setText(object.get("Course Type").getAsString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DigitalMarks.class);
                intent.putExtra("sem",postParam.get(0).getAsString());
                intent.putExtra("classnbr",postParam.get(1).getAsString());
                intent.putExtra("crscd",postParam.get(2).getAsString());
                intent.putExtra("crstp",postParam.get(3).getAsString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course_name,coursecode,facultyname,type;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.main_card_digital);
            course_name=(TextView)itemView.findViewById(R.id.course_name_digital);
            coursecode=(TextView)itemView.findViewById(R.id.coursecode);
            facultyname=(TextView)itemView.findViewById(R.id.facultynamedigital);
            type=(TextView)itemView.findViewById(R.id.typedigital);

            type.setTextColor(Color.parseColor(SetTheme.colorName));
        }
    }

}
