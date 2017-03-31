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
import com.example.mayankaggarwal.viteventsapp.activities.Messages;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 21/03/17.
 */

public class RVMessages extends RecyclerView.Adapter<RVMessages.MyViewHolder>  {


    private Context context;
//    private List<EventList> eventList=new ArrayList<>();

    JsonParser parser;
    JsonObject jsonObject;
    JsonArray messagesList;

    public RVMessages(String messages, Activity context) {
        this.context=context;

        if(messages.length()>0){
            parser=new JsonParser();
            if(messages!=null){
                jsonObject=parser.parse(messages).getAsJsonObject();
                messagesList=jsonObject.get("messages").getAsJsonArray();
            }

            if(messagesList.size()>0){
                Messages.imageView.setVisibility(View.GONE);
            }else {
                Messages.imageView.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public RVMessages.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_messages, parent, false);

        return new RVMessages.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RVMessages.MyViewHolder holder, int position) {

        JsonObject message= (JsonObject) messagesList.get(position);

        setMessageLyoutContent(message,holder);

    }

    private void setMessageLyoutContent(JsonObject message,RVMessages.MyViewHolder holder) {
        holder.facname.setVisibility(View.VISIBLE);
        holder.subj.setVisibility(View.VISIBLE);
        holder.content.setVisibility(View.VISIBLE);
        holder.customtext.setVisibility(View.VISIBLE);
        holder.date.setVisibility(View.VISIBLE);
        holder.custom.setVisibility(View.VISIBLE);

        if(message.size()==4){
            String facultyname=message.get("0").toString().replace("\"","");
            String subj=message.get("1").toString().replace("\"","");
            String content=message.get("2").toString().replace("\"","");
            String date=message.get("3").toString().replace("\"","");
            String customtext=message.get("0").toString().replace("\"","");

            holder.facname.setText(facultyname);
            holder.subj.setText(subj);
            holder.content.setText(content);
            holder.date.setText(date);
            holder.customtext.setText(customtext.substring(0,1).toUpperCase());

        }else if(message.size()==3){
            String facultyname=message.get("0").toString().replace("\"","");
            String content=message.get("1").toString().replace("\"","");
            String date=message.get("2").toString().replace("\"","");
            String customtext=message.get("0").toString().replace("\"","");

            holder.facname.setText(facultyname);
            holder.subj.setVisibility(View.GONE);
            holder.content.setText(content);
            holder.date.setText(date);
            holder.customtext.setText(customtext.substring(0,1).toUpperCase());

        }else if(message.size()==2){
            String content=message.get("0").toString().replace("\"","");
            String date=message.get("1").toString().replace("\"","");
            holder.facname.setVisibility(View.GONE);
            holder.subj.setVisibility(View.GONE);
            holder.content.setText(content);
            holder.date.setText(date);
            holder.custom.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.messagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView custom;
        CardView contentcard;
        TextView customtext;
        TextView facname;
        TextView date;
        TextView content;
        TextView subj;

        public MyViewHolder(View itemView) {
            super(itemView);
            custom=(CardView)itemView.findViewById(R.id.customcard);
            contentcard=(CardView)itemView.findViewById(R.id.contentcard);
            facname=(TextView)itemView.findViewById(R.id.facultyname);
            subj=(TextView)itemView.findViewById(R.id.coursename);
            date=(TextView)itemView.findViewById(R.id.datemessage);
            customtext=(TextView)itemView.findViewById(R.id.customletter);
            content=(TextView)itemView.findViewById(R.id.message);

            custom.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
            contentcard.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
        }
    }

}
