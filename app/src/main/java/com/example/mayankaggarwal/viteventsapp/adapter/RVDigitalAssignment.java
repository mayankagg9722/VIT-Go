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
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class RVDigitalAssignment extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int AD_VIEW_TYPE = 0;
    private static final int MENU_VIEW_TYPE = 1;


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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case AD_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad, parent, false);
                return new RVDigitalAssignment.NativeExpressAdViewHolder(view);
            case MENU_VIEW_TYPE:
            default:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_digital_assignment, parent, false);
                return new RVDigitalAssignment.MyViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof RVDigitalAssignment.NativeExpressAdViewHolder) {
            RVDigitalAssignment.NativeExpressAdViewHolder nativeExpressViewHolder = (RVDigitalAssignment.NativeExpressAdViewHolder) holder;
            ViewGroup adView=(ViewGroup)nativeExpressViewHolder.itemView;

//            adView.removeAllViews();
//            NativeExpressAdView nativeexpress=new NativeExpressAdView(context);
//            nativeexpress.setAdSize(new AdSize(320,150));
//            nativeexpress.setAdUnitId("ca-app-pub-1043169578514521/7037347897");
//            nativeexpress.loadAd(new AdRequest.Builder().build());

        } else if (holder instanceof RVDigitalAssignment.MyViewHolder) {

            final RVDigitalAssignment.MyViewHolder myViewHolder = (RVDigitalAssignment.MyViewHolder) holder;

            int pos = 0;
            if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
                if(Prefs.getPrefs("showads",context).equals("true")){
                    pos=position-1;
                }else {
                    pos=position;
                }
            }

            final JsonObject object=jsonArray.get(pos).getAsJsonObject();
            final JsonArray postParam=object.get("post_parameters").getAsJsonArray();

            myViewHolder.course_name.setText(object.get("Course Title").getAsString());
            myViewHolder.coursecode.setText(object.get("Course Code").getAsString());
            myViewHolder.facultyname.setText(object.get("Faculty").getAsString());
            myViewHolder.type.setText(object.get("Course Type").getAsString());
            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
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


    }

    @Override
    public int getItemCount() {

        if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
            if(Prefs.getPrefs("showads",context).equals("true")){
                return this.jsonArray.size() + 1;
            }
        }

        return this.jsonArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ) {
            if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
                if(Prefs.getPrefs("showads",context).equals("true")){
                    return AD_VIEW_TYPE;
                }
            }
        }
        return MENU_VIEW_TYPE;
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

    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdViewHolder(View view) {
            super(view);
            NativeExpressAdView mAdView = (NativeExpressAdView)view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("EC94735EDFFCB883AB73D12F21BD5B00").build();
            mAdView.loadAd(adRequest);
//            if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
//                if(Prefs.getPrefs("showads",context).equals("false")){
//                    mAdView.setVisibility(View.GONE);
//                }else {
//                    mAdView.setVisibility(View.VISIBLE);
//                }
//            }
        }
    }

}
