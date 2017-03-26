package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class RVMarks extends RecyclerView.Adapter<RVMarks.MyViewHolder> {

    private Context context;

    JsonParser parser;
    JsonArray jsonArray;

    public RVMarks(String marks, Activity context) {
        this.context=context;
        parser=new JsonParser();
        jsonArray=parser.parse(marks).getAsJsonObject().get("data").getAsJsonArray();
        Log.d("tagg","ppp:"+jsonArray.get(0).getAsJsonObject().get("Course Title").getAsString());
    }

    @Override
    public RVMarks.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_marks, parent, false);

        return new RVMarks.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVMarks.MyViewHolder holder, final int position) {


        final JsonObject object=jsonArray.get(position).getAsJsonObject();
        final JsonObject marks=object.get("marks").getAsJsonObject();

        holder.course_name.setText(object.get("Course Title").getAsString());
        holder.coursecode.setText(object.get("Course Code").getAsString()+" - "+object.get("Faculty").getAsString());
        holder.facultyname.setText(object.get("Faculty").getAsString());
//        holder.layoutMarks.setJsonData(marks);
    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course_name,coursecode,facultyname;
        CardView cardView;

//        public CustomLayoutMarks layoutMarks=new CustomLayoutMarks(context);

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.card_marks);
            course_name=(TextView)itemView.findViewById(R.id.course_name_marks);
            coursecode=(TextView)itemView.findViewById(R.id.coursecodeandtype);
            facultyname=(TextView)itemView.findViewById(R.id.facultynamemarks);
//            layoutMarks=(com.example.mayankaggarwal.viteventsapp.activities.CustomLayoutMarks)itemView.findViewById(R.id.customMarks);
            cardView.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));

        }
    }

}
