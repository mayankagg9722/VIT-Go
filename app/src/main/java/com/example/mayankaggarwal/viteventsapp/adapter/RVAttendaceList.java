package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.MainActivity;
import com.example.mayankaggarwal.viteventsapp.activities.Details;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by mayankaggarwal on 13/02/17.
 */

public class RVAttendaceList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int AD_VIEW_TYPE = 0;
    private static final int MENU_VIEW_TYPE = 1;

    public List<AttendanceList> attendanceList;
    public Activity context;
    Boolean clickable;

    JsonArray main_timetable;
    JsonArray main_faculty;
    JsonArray sub_timetable_array;
    JsonArray sub_time_array;

    JsonParser parser;
    JsonObject json;

    String myday;
    String classroom;
    String code;

    List<String> course_code_day = new ArrayList<>();
    List<String> course_classroom = new ArrayList<>();
    List<String> course_type = new ArrayList<>();
    List<String> course_time = new ArrayList<>();
    List<String> course_slot = new ArrayList<>();

    String slot;
    String type;
    String slotTime;

    int k = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public List<AttendanceList> attendanceList;
        public TextView percentage;
        public TextView course_name;
        public TextView course_code;
        public TextView classroom;
        public TextView course_type;
        public LinearLayout cardView;
        public CardView maincard;
        public TextView timeView;
        public TextView faculty;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (LinearLayout) itemView.findViewById(R.id.debaredlayout);
            maincard = (CardView) itemView.findViewById(R.id.main_card);
            percentage = (TextView) itemView.findViewById(R.id.attendance_percentage);
            course_name = (TextView) itemView.findViewById(R.id.course_name);
            course_type = (TextView) itemView.findViewById(R.id.type);
            classroom = (TextView) itemView.findViewById(R.id.classroom);
            timeView = (TextView) itemView.findViewById(R.id.slottime);
            faculty = (TextView) itemView.findViewById(R.id.faculty);
            course_type.setTextColor(Color.parseColor(SetTheme.colorName));
        }

    }

    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdViewHolder(View view) {
            super(view);
            NativeExpressAdView mAdView = (NativeExpressAdView)view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("EC94735EDFFCB883AB73D12F21BD5B00").build();
            mAdView.loadAd(adRequest);
            TextView textView=(TextView)view.findViewById(R.id.sponsor);
            textView.setTextColor(Color.parseColor(SetTheme.colorName));
//            if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
//                if(Prefs.getPrefs("showads",context).equals("false")){
//                    mAdView.setVisibility(View.GONE);
//                }else {
//                    mAdView.setVisibility(View.VISIBLE);
//                }
//            }
        }
    }

    public RVAttendaceList(List<AttendanceList> atendance, Activity context, boolean clickable) {

        parser = new JsonParser();
        this.attendanceList = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat day = new SimpleDateFormat("E");

        myday = day.format(date).toString().toUpperCase();

//        myday="SUN";

        if (!(Prefs.getPrefs("myTimetable", context).equals("notfound"))) {
            json = (JsonObject) parser.parse(Prefs.getPrefs("myTimetable", context));
            main_timetable = json.getAsJsonArray("timetable").getAsJsonArray();
            main_faculty = json.getAsJsonArray("faculties");

            setDataAccday();


            int k = 0;


            Globals.courseCodeDaySize = course_code_day.size();

            for (String code : course_code_day) {
                for (AttendanceList a : atendance) {

                    if (code.trim().toString().equals(a.getCourseCode().trim().toString())) {//(code.toString().contains(a.getCourseCode().toString())) {
                        if ((course_type.get(k).contains("ELA") || course_type.get(k).contains("LO")) && a.getCourseType().contains("Lab")) {
                            this.attendanceList.add(a);
                        } else if ((course_type.get(k).contains("ETH") || course_type.get(k).contains("TH")) && a.getCourseType().contains("Theory")) {
                            this.attendanceList.add(a);
                        } else if ((course_type.get(k).contains("SS") && a.getCourseType().contains("Soft"))) {
                            this.attendanceList.add(a);
                        }
                    }
                }
                k++;
            }

            Globals.attendanceListSize = this.attendanceList.size();

        }

        if (this.attendanceList.size() > 0) {
            MainActivity.imageView.setVisibility(View.GONE);
        } else {
            MainActivity.imageView.setVisibility(View.VISIBLE);
        }

        this.context = context;
        this.clickable = clickable;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SetTheme.onActivityCreateSetTheme(context);
        switch (viewType) {
            case AD_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad, parent, false);
                return new RVAttendaceList.NativeExpressAdViewHolder(view);
            case MENU_VIEW_TYPE:
            default:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_layout, parent, false);
                return new RVAttendaceList.MyViewHolder(itemView);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof NativeExpressAdViewHolder) {
            NativeExpressAdViewHolder nativeExpressViewHolder = (NativeExpressAdViewHolder) holder;
            ViewGroup adView=(ViewGroup)nativeExpressViewHolder.itemView;

//            adView.removeAllViews();
//            NativeExpressAdView nativeexpress=new NativeExpressAdView(context);
//            nativeexpress.setAdSize(new AdSize(320,150));
//            nativeexpress.setAdUnitId("ca-app-pub-1043169578514521/7037347897");
//            nativeexpress.loadAd(new AdRequest.Builder().build());

        } else if (holder instanceof MyViewHolder) {

            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            int pos = position;
            if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
                if(Prefs.getPrefs("showads",context).equals("true")){
                    pos=position-1;
                }else {
                    pos=position;
                }
            }
            final AttendanceList attendanceList = this.attendanceList.get(pos);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                myViewHolder.maincard.setElevation(Float.parseFloat(String.valueOf(0)));
            }

            if ((Float.parseFloat(attendanceList.getTotalClasses()) != 0)) {
                float per = ((Float.parseFloat(attendanceList.getAttended())) * 100) / (Float.parseFloat(attendanceList.getTotalClasses()));

                if (per - Math.floor(per) > 0.0) {
                    per = (int) per + 1;
                }

                if (per >= 75) {
                    myViewHolder.cardView.setBackground(context.getResources().getDrawable(R.drawable.custom_shape_notdebaared));
                    myViewHolder.percentage.setTextColor(Color.parseColor(SetTheme.colorName));
                } else {
                    myViewHolder.cardView.setBackground(context.getResources().getDrawable(R.drawable.custom_shape));
                    myViewHolder.percentage.setTextColor(Color.parseColor("#ffffff"));
                }

                myViewHolder.percentage.setText(String.valueOf((int) per) + "%");

            }


            myViewHolder.course_name.setText(attendanceList.getCourseName());


            for (JsonElement a : main_faculty) {
                if (attendanceList.getCourseName().toLowerCase().equals(a.getAsJsonObject().get("courseName").getAsString().toLowerCase())) {
                    if ((attendanceList.getCourseType().toLowerCase().contains("theory")) && (a.getAsJsonObject().get("courseType").getAsString().toLowerCase().contains("theory"))) {
                        myViewHolder.faculty.setText(a.getAsJsonObject().get("facultyName").getAsString().split("-")[0]);
                    } else if (attendanceList.getCourseType().toLowerCase().contains("lab")) {
                        myViewHolder.faculty.setText(a.getAsJsonObject().get("facultyName").getAsString().split("-")[0]);
                    }
                }
            }
            myViewHolder.classroom.setText(attendanceList.getCourseCode() + " - " + course_classroom.get(pos));
            myViewHolder.timeView.setText(course_time.get(pos));

            if (attendanceList.getCourseType().contains("Theory")) {
                myViewHolder.course_type.setText(course_slot.get(pos) + " - Theory");
            } else if (attendanceList.getCourseType().equals("Soft Skill")) {
                myViewHolder.course_type.setText(course_slot.get(pos) + " - Soft Skills");
            } else {
                myViewHolder.course_type.setText(course_slot.get(pos) + " - Lab");
            }

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Log.d("tagg",myViewHolder.faculty.getText().toString());
                    int pos=position;
                    if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
                        if(Prefs.getPrefs("showads",context).equals("true")){
                            pos=position-1;
                        }else {
                            pos=position;
                        }
                    }
                    Intent intent = new Intent(context, Details.class);
                    intent.putExtra("percentage", myViewHolder.percentage.getText().toString());
                    intent.putExtra("coursename", myViewHolder.course_name.getText().toString());
                    intent.putExtra("classroom", course_classroom.get(pos));
                    intent.putExtra("code", attendanceList.getCourseCode());
                    intent.putExtra("faculty", myViewHolder.faculty.getText());
                    intent.putExtra("attendedclass", attendanceList.getAttended().toString());
                    intent.putExtra("totalclass", attendanceList.getTotalClasses().toString());

                    intent.putExtra("classnbr", attendanceList.getPostParams().getClassnbr().toString());
                    intent.putExtra("semcode", attendanceList.getPostParams().getSemcode().toString());
                    intent.putExtra("crscd", attendanceList.getPostParams().getCrscd().toString());
                    intent.putExtra("crstp", attendanceList.getPostParams().getCrstp().toString());
                    intent.putExtra("from_date", attendanceList.getPostParams().getFromDate().toString());
                    intent.putExtra("to_date", attendanceList.getPostParams().getToDate().toString());

                    context.startActivity(intent);
                }
            });
        }

    }

    public void setDataAccday() {
        if (main_timetable.size() > 0) {
            if (myday.equals("MON")) {
                sub_timetable_array = main_timetable.get(2).getAsJsonArray();
                //finding information
                findInfo();
            } else if (myday.equals("TUE")) {
                sub_timetable_array = main_timetable.get(3).getAsJsonArray();
                //finding information
                findInfo();
            } else if (myday.equals("WED")) {
                sub_timetable_array = main_timetable.get(4).getAsJsonArray();
                //finding information
                findInfo();

            } else if (myday.equals("THU")) {
                sub_timetable_array = main_timetable.get(5).getAsJsonArray();
                //finding information
                findInfo();
            } else if (myday.equals("FRI")) {
                sub_timetable_array = main_timetable.get(6).getAsJsonArray();
                //finding information
                findInfo();
            } else if (myday.equals("SAT")) {
                sub_timetable_array = main_timetable.get(7).getAsJsonArray();
                //finding information
                //findInfo();
            } else if (myday.equals("SUN")) {
                sub_timetable_array = main_timetable.get(8).getAsJsonArray();
                //finding information
                // findInfo();
            }
        }
    }

    private void findInfo() {
        int i = 0;
        for (JsonElement st : sub_timetable_array) {
            if (st.toString().contains("-")) {
                String[] sub_day_array = null;
                sub_day_array = st.toString().trim().split("-");
                code = sub_day_array[0].replace("\"", "");
                type = sub_day_array[1];
                classroom = sub_day_array[2];
                slot = sub_day_array[3].replace("\"", "");

                //finding slot time in which are having class
                setSlotTime(type, i);

                course_code_day.add(code);
                course_classroom.add(classroom);
                course_type.add(type);
                course_time.add(slotTime.replace("\"", ""));
                course_slot.add(slot);
//                Log.d("tagg",String.valueOf(classroom+"time:"+String.valueOf(slotTime)+"slot:"+String.valueOf(slot)));
            }
            i++;
        }
    }

    private void setSlotTime(String type, int i) {
        if (type.contains("ETH") || type.contains("TH") || type.contains("SS")) {
            sub_time_array = main_timetable.get(0).getAsJsonArray();
            int j = 0;
            for (JsonElement time : sub_time_array) {
                if (j == i) {
                    if (Arrays.asList(course_time).contains(slotTime)) {
                        continue;
                    }
                    slotTime = time.toString().toLowerCase().replace("to", "-");
                }
                j++;
            }
        } else if (type.contains("ELA") || type.contains("LO")) {
            sub_time_array = main_timetable.get(1).getAsJsonArray();
            int j = 0;
            for (JsonElement time : sub_time_array) {
                if (j == i) {
                    if (Arrays.asList(course_time).contains(slotTime)) {
                        continue;
                    }
                    slotTime = time.toString().toLowerCase().replace("to", "-");
                }
                j++;
            }
        } else {
            slotTime = "";
        }
    }


    @Override
    public int getItemCount() {
            if(!(Prefs.getPrefs("showads",context).equals("notfound"))){
                if(Prefs.getPrefs("showads",context).equals("true")){
                    return attendanceList.size() + 1;
                }
            }
        return attendanceList.size();
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
}
