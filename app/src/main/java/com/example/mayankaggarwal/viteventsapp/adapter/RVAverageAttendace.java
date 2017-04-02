package com.example.mayankaggarwal.viteventsapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.activities.AverageAttendance;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.activities.Details;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 15/03/17.
 */

public class RVAverageAttendace extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int AD_VIEW_TYPE = 0;
    private static final int MENU_VIEW_TYPE = 1;

    public List<AttendanceList> attendanceList;
    public Activity context;
    Boolean clickable;

    JsonArray main_timetable;
    JsonArray main_faculty;

    JsonParser parser;
    JsonObject json;


    List<String> course_room = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView percentage;
        public TextView course_name;
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
        }
    }

    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdViewHolder(View view) {
            super(view);
            NativeExpressAdView mAdView = (NativeExpressAdView) view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("EC94735EDFFCB883AB73D12F21BD5B00").build();
            mAdView.loadAd(adRequest);
            TextView textView=(TextView)view.findViewById(R.id.sponsor);
            textView.setTextColor(Color.parseColor(SetTheme.colorName));
//            if (!(Prefs.getPrefs("showads", context).equals("notfound"))) {
//                if (Prefs.getPrefs("showads", context).equals("false")) {
//                    mAdView.setVisibility(View.GONE);
//                } else {
//                    mAdView.setVisibility(View.VISIBLE);
//                }
//            }
        }
    }

    public RVAverageAttendace(List<AttendanceList> atendance, Activity context, boolean clickable) {

        parser = new JsonParser();
        this.attendanceList = new ArrayList<>();

        if (!(Prefs.getPrefs("myTimetable", context).equals("notfound"))) {
            json = (JsonObject) parser.parse(Prefs.getPrefs("myTimetable", context));
            main_timetable = json.getAsJsonArray("timetable").getAsJsonArray();
            main_faculty = json.getAsJsonArray("faculties");


            AverageAttendance.avg_per = 0;

            for (AttendanceList a : atendance) {
                this.attendanceList.add(a);
                float k = fetchPercentage(a.getAttended(), a.getTotalClasses());
                AverageAttendance.avg_per += k;
            }

            if (atendance.size() > 0) {
                AverageAttendance.avg_per = AverageAttendance.avg_per / atendance.size();
            }

            AverageAttendance.avgnumber.setText(String.valueOf(AverageAttendance.avg_per));


            fetchClass(main_timetable, attendanceList);
        }

        if (this.attendanceList.size() > 0) {
            AverageAttendance.imageView.setVisibility(View.GONE);
        } else {
            AverageAttendance.imageView.setVisibility(View.VISIBLE);
        }

        this.context = context;
        this.clickable = clickable;

    }

    private void fetchClass(JsonArray main_timetable, List<AttendanceList> attendanceList) {

        for (AttendanceList a : attendanceList) {
            String str = a.getCourseCode() + " - " + a.getPostParams().getCrstp();
            for (JsonElement ele : main_timetable.getAsJsonArray()) {
                int flag = 0;
                for (JsonElement k : ele.getAsJsonArray()) {
                    if (k.toString().contains(str)) {
                        String classno = k.toString().split("-")[2];
                        course_room.add(classno);
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    break;
                }
            }
        }
    }

    private float fetchPercentage(String a, String b) {
        float per = ((Float.parseFloat(a)) * 100) / (Float.parseFloat(b));
        if (per - Math.floor(per) > 0.0) {
            per = (int) per + 1;
        }
        return per;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SetTheme.onActivityCreateSetTheme(context);
        switch (viewType) {
            case AD_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad, parent, false);
                return new RVAverageAttendace.NativeExpressAdViewHolder(view);
            case MENU_VIEW_TYPE:
            default:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_layout, parent, false);
                return new RVAverageAttendace.MyViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RVAverageAttendace.NativeExpressAdViewHolder) {
            RVAverageAttendace.NativeExpressAdViewHolder nativeExpressViewHolder = (RVAverageAttendace.NativeExpressAdViewHolder) holder;
            ViewGroup adView = (ViewGroup) nativeExpressViewHolder.itemView;
        } else if (holder instanceof RVAverageAttendace.MyViewHolder) {
            final RVAverageAttendace.MyViewHolder myViewHolder = (RVAverageAttendace.MyViewHolder) holder;

            int pos = 0;
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
            myViewHolder.course_type.setText(attendanceList.getSlot());
            myViewHolder.classroom.setText(course_room.get(pos));


            if (attendanceList.getCourseType().contains("Theory")) {
                myViewHolder.timeView.setText(attendanceList.getCourseCode() + " - Theory");
            } else if (attendanceList.getCourseType().equals("Soft Skill")) {
                myViewHolder.timeView.setText(attendanceList.getCourseCode() + " - Soft Skills");
            } else {
                myViewHolder.timeView.setText(attendanceList.getCourseCode() + " - Lab");
            }

            for (JsonElement a : main_faculty) {
                if (attendanceList.getCourseName().toLowerCase().contains(a.getAsJsonObject().get("courseName").getAsString().toLowerCase())) {
                    if ((attendanceList.getCourseType().toLowerCase().contains("theory")) && (a.getAsJsonObject().get("courseType").getAsString().toLowerCase().contains("theory"))) {
                        myViewHolder.faculty.setText(a.getAsJsonObject().get("facultyName").getAsString().split("-")[0]);
                    } else if (attendanceList.getCourseType().toLowerCase().contains("lab")) {
                        myViewHolder.faculty.setText(a.getAsJsonObject().get("facultyName").getAsString().split("-")[0]);
                    }
                }
            }

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Log.d("tagg",myViewHolder.faculty.getText().toString());
                    Intent intent = new Intent(context, Details.class);
                    intent.putExtra("percentage", myViewHolder.percentage.getText().toString());

                    intent.putExtra("coursename", myViewHolder.course_name.getText().toString());

                    intent.putExtra("classroom", myViewHolder.classroom.getText());

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
