package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.activities.FacultyInformation;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesList;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 14/03/17.
 */

public class RVFaculties extends RecyclerView.Adapter<RVFaculties.MyViewHolder> {


    public Activity context;
    Boolean clickable;
    public List<FacultiesList> faculties,facultiesListCopy;


    public  RVFaculties(List<FacultiesList> faculties, Activity context, boolean clickable){

        this.faculties = new ArrayList<>();

        for (FacultiesList a : faculties) {
            this.faculties.add(a);
        }

        facultiesListCopy=new ArrayList<>();

        for (FacultiesList a : faculties) {
            this.facultiesListCopy.add(a);
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
            name=(TextView)itemView.findViewById(R.id.faculty_name);
            school=(TextView)itemView.findViewById(R.id.school_name);
            card=(CardView)itemView.findViewById(R.id.faculty_card);
            empid=(TextView)itemView.findViewById(R.id.empid);
        }
    }

    public void filter(String text){
        faculties.clear();
        if(text.isEmpty()){
            faculties.addAll(facultiesListCopy);
        }else{
            text=text.toLowerCase();
            for(FacultiesList f:facultiesListCopy){
                if(f.getName().toString().toLowerCase().contains(text) || f.getSchool().toString().toLowerCase().contains(text)){
                    faculties.add(f);
                }
            }
        }
        notifyDataSetChanged();
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
        FacultiesList faculties=this.faculties.get(position);
        holder.name.setText(faculties.getName());
        holder.school.setText(faculties.getSchool());
        holder.empid.setText(faculties.getEmpid());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,FacultyInformation.class);
                intent.putExtra("profname",holder.name.getText().toString());
                intent.putExtra("profschool",holder.school.getText().toString());
                intent.putExtra("empid",holder.empid.getText());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.faculties.size();
    }


}
