package com.vitgo.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitgo.mayankaggarwal.viteventsapp.activities.Faculties;
import com.vitgo.mayankaggarwal.viteventsapp.activities.FacultyInformation;
import com.vitgo.mayankaggarwal.viteventsapp.R;
import com.vitgo.mayankaggarwal.viteventsapp.activities.LateNightRequest;
import com.vitgo.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Created by mayankaggarwal on 14/03/17.
 */

public class RVFaculties extends RecyclerView.Adapter<RVFaculties.MyViewHolder> {


    public Activity context;
    Boolean clickable;
    JsonParser parser;
    JsonArray jsonArray;
    JsonArray jsonArrayCopy;


    public RVFaculties(String faculties, Activity context, boolean clickable) {


        parser = new JsonParser();
        jsonArray = parser.parse(faculties).getAsJsonArray();
        jsonArrayCopy = parser.parse(faculties).getAsJsonArray();


        if (jsonArray.size() > 0) {
            Faculties.imageView.setVisibility(View.GONE);
        } else {
            Faculties.imageView.setVisibility(View.VISIBLE);
        }

        this.context = context;
        this.clickable = clickable;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView school;
        TextView empid;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.faculty_name);
            school = (TextView) itemView.findViewById(R.id.school_name);
            card = (CardView) itemView.findViewById(R.id.faculty_card);
            empid = (TextView) itemView.findViewById(R.id.empid);
            card.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
        }
    }

    public void filter(String text) {
        if (this.jsonArray.size() > 0) {
            jsonArray = new JsonArray();
            if (text.isEmpty()) {
                jsonArray = jsonArrayCopy;
//            faculties.addAll(facultiesListCopy);
            } else {
                text = text.toLowerCase();
                for (int i = 0; i < jsonArrayCopy.size(); i++) {
                    if (jsonArrayCopy.get(i).getAsJsonObject().get("name").toString().toLowerCase().contains(text)
                            || jsonArrayCopy.get(i).getAsJsonObject().get("school").toString().toLowerCase().contains(text)) {
                        jsonArray.add(jsonArrayCopy.get(i));
                    }
                }
//            for(FacultiesList f:facultiesListCopy){
//                if(f.getName().toString().toLowerCase().contains(text) || f.getSchool().toString().toLowerCase().contains(text)){
//                    faculties.add(f);
//                }
//            }
            }
            notifyDataSetChanged();
        }
    }


    @Override
    public RVFaculties.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SetTheme.onActivityCreateSetTheme(context);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faculty_item, parent, false);
        return new RVFaculties.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RVFaculties.MyViewHolder holder, int position) {

        JsonObject faculties = this.jsonArray.get(position).getAsJsonObject();

//        FacultiesList faculties=this.faculties.get(position);

        holder.name.setText(faculties.get("name").getAsString());
        holder.school.setText(faculties.get("school").getAsString());
        holder.empid.setText(faculties.get("empid").getAsString());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context.getIntent().getStringExtra("comefrom") != null) {
                    if (context.getIntent().getStringExtra("comefrom").equals("latenight")) {
                        Intent intent = new Intent(context, LateNightRequest.class);
                        intent.putExtra("empid", holder.empid.getText());
                        intent.putExtra("profname", holder.name.getText().toString());
                        context.startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(context, FacultyInformation.class);
                    intent.putExtra("profname", holder.name.getText().toString());
                    intent.putExtra("profschool", holder.school.getText().toString());
                    intent.putExtra("empid", holder.empid.getText());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

}
