package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.activities.Marks;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        if(marks!=null){
            jsonArray=parser.parse(marks).getAsJsonObject().get("data").getAsJsonArray();
        }

        if(jsonArray.size()>0){
            Marks.imageView.setVisibility(View.GONE);
        }else {
            Marks.imageView.setVisibility(View.VISIBLE);
        }
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
        holder.coursecode.setText(object.get("Course Code").getAsString()+" - "+object.get("Course Type").getAsString());
        holder.facultyname.setText(object.get("Faculty").getAsString());
        addTextFields(marks,holder.customMarksLayout);
    }

    private void addTextFields(JsonObject marks,LinearLayout customMarksLayout) {

        customMarksLayout.removeAllViews();

        // gson to json mapping of key & value pairs

        Set<Map.Entry<String, JsonElement>> entrySet = marks.entrySet();

        if(entrySet.size()<=0){
                TextInputLayout textInputLayout = new TextInputLayout(context);
                TextView name = new TextView(context);
                name.setLineSpacing(0f,1.1f);
                textInputLayout.addView(name);
                name.setText("Marks:");
                name.setTextColor(Color.parseColor("#ffffff"));
                name.setTextSize(13);
                customMarksLayout.addView(textInputLayout);
        }else {
            for(Map.Entry<String,JsonElement> entry : entrySet){
                TextInputLayout textInputLayout = new TextInputLayout(context);
                TextView name = new TextView(context);
                name.setLineSpacing(0f,1.18f);
                name.setText(entry.getKey()+" : "+entry.getValue().getAsString());
                textInputLayout.addView(name);
                name.setTextColor(Color.parseColor("#ffffff"));
                name.setTextSize(13);
                customMarksLayout.addView(textInputLayout);
            }
        }



    }

    @Override
    public int getItemCount() {
        return this.jsonArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course_name,coursecode,facultyname;
        CardView cardView;
        LinearLayout customMarksLayout;

//        public CustomLayoutMarks layoutMarks=new CustomLayoutMarks(context);

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.card_marks);
            course_name=(TextView)itemView.findViewById(R.id.course_name_marks);
            coursecode=(TextView)itemView.findViewById(R.id.coursecodeandtype);
            facultyname=(TextView)itemView.findViewById(R.id.facultynamemarks);
            cardView.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
            customMarksLayout=(LinearLayout)itemView.findViewById(R.id.marskcustomlayout);

        }
    }

}
