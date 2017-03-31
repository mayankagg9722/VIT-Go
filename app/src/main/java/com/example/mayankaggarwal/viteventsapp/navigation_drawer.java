package com.example.mayankaggarwal.viteventsapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.activities.AverageAttendance;
import com.example.mayankaggarwal.viteventsapp.activities.DigitalAssignment;
import com.example.mayankaggarwal.viteventsapp.activities.Events;
import com.example.mayankaggarwal.viteventsapp.activities.ExamSchedule;
import com.example.mayankaggarwal.viteventsapp.activities.Faculties;
import com.example.mayankaggarwal.viteventsapp.activities.Hosteller;
import com.example.mayankaggarwal.viteventsapp.activities.ImageGallery;
import com.example.mayankaggarwal.viteventsapp.activities.Marks;
import com.example.mayankaggarwal.viteventsapp.activities.Messages;
import com.example.mayankaggarwal.viteventsapp.activities.Settings;
import com.example.mayankaggarwal.viteventsapp.activities.TimeTable;
import com.example.mayankaggarwal.viteventsapp.utils.ImagePath;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class navigation_drawer extends Fragment {

    static  LinearLayout nav_layout;

    de.hdodenhof.circleimageview.CircleImageView profile;

    View v;

    public navigation_drawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

         v=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        profile=(de.hdodenhof.circleimageview.CircleImageView)v.findViewById(R.id.profile_image);

        setCards(v);

        nav_layout=(LinearLayout)v.findViewById(R.id.nav_layout);
        nav_layout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        TextView name = (TextView) v.findViewById(R.id.nametext);
        de.hdodenhof.circleimageview.CircleImageView profile=(de.hdodenhof.circleimageview.CircleImageView)v.findViewById(R.id.profile_image);
        TextView regno = (TextView) v.findViewById(R.id.regtext);
        ImageButton imageButton=(ImageButton)v.findViewById(R.id.settings);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Settings.class));
            }
        });

        name.setText(Prefs.getPrefs("name", getContext()));
        regno.setText(Prefs.getPrefs("regno", getContext()));
        name.setTextColor(Color.parseColor("#ffffff"));

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ImageGallery.class));
            }
        });

        if(!(Prefs.getPrefs("profileimage",getContext()).equals("notfound"))){
                setNavImage(v);

        }else {
            profile.setImageResource(R.drawable.unknown);
        }

        return v;
    }

    private void setCards(View v) {
        LinearLayout linearLayout=(LinearLayout)v.findViewById(R.id.listlinearlayout);
        LinearLayout linearLayouttop=(LinearLayout)v.findViewById(R.id.mycustomlinear);
        linearLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        linearLayouttop.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        ListView listView=(ListView)v.findViewById(R.id.listview);
        String[] names={"Events","Time Table","Attendance","Search Faculty","Faculty Messages","Digital Assignments","Exam Schedule","Marks","Hosteller"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),R.layout.item_list_nav,R.id.mytextcustom,names);
        listView.setSelector(R.drawable.custom_selector);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    startActivity(new Intent(getActivity(),Events.class));
                }else if(position==1){
                    startActivity(new Intent(getActivity(),TimeTable.class));
                }else if(position==2){
                    startActivity(new Intent(getActivity(),AverageAttendance.class));
                }else if(position==3){
                    startActivity(new Intent(getActivity(),Faculties.class));
                }else if(position==4){
                    startActivity(new Intent(getActivity(),Messages.class));
                }else if(position==5){
                    startActivity(new Intent(getActivity(),DigitalAssignment.class));
                }else if(position==6){
                    startActivity(new Intent(getActivity(),ExamSchedule.class));
                }else if(position==7){
                    startActivity(new Intent(getActivity(),Marks.class));
                }else if(position==8){
                    startActivity(new Intent(getActivity(),Hosteller.class));
                }
            }
        });

//        int card_id[]={R.id.timetable,R.id.attendance,R.id.course_page,R.id.marks,R.id.event,
//                R.id.search_faculty,R.id.assignment,R.id.fac_message,R.id.exam_schedule,R.id.grades,R.id.request,R.id.def_password};
//        for(int i=0;i<card_id.length;i++){
//            ((CardView)v.findViewById(card_id[i])).setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
//        }
//        CardView timetableCard=(CardView)v.findViewById(card_id[0]);
//        CardView facultycard=(CardView)v.findViewById(card_id[5]);
//        CardView attendanceCard=(CardView)v.findViewById(card_id[1]);
//        CardView eventCard=(CardView)v.findViewById(card_id[4]);
//        CardView message=(CardView)v.findViewById(card_id[7]);
//        CardView hoteller=(CardView)v.findViewById(card_id[10]);
//        CardView exam=(CardView)v.findViewById(card_id[8]);
//        CardView digitalAssignment=(CardView)v.findViewById(card_id[6]);
//        CardView marks=(CardView)v.findViewById(card_id[3]);
//
//        timetableCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),TimeTable.class));
//            }
//        });
//
//        facultycard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),Faculties.class));
//            }
//        });
//
//        attendanceCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),AverageAttendance.class));
//            }
//        });
//
//        eventCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),Events.class));
//            }
//        });
//
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),Messages.class));
//            }
//        });
//
//        hoteller.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),Hosteller.class));
//            }
//        });
//
//        exam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),ExamSchedule.class));
//            }
//        });
//
//        digitalAssignment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),DigitalAssignment.class));
//            }
//        });
//
//        marks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),Marks.class));
//            }
//        });

    }

    private void setNavImage(View v) {
            try {
                String path=ImagePath.getPath(getContext(),Uri.parse(Prefs.getPrefs("profileimage",getContext())));
//                Log.d("tagg","path:"+path);
                Bitmap myBitmap = BitmapFactory.decodeFile(path);
                profile.setImageBitmap(myBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
