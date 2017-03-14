package com.example.mayankaggarwal.viteventsapp;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class navigation_drawer extends Fragment {

    static  LinearLayout nav_layout;

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
//         Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        setCards(v);

        nav_layout=(LinearLayout)v.findViewById(R.id.nav_layout);

        TextView name = (TextView) v.findViewById(R.id.nametext);
        de.hdodenhof.circleimageview.CircleImageView profile=(de.hdodenhof.circleimageview.CircleImageView)v.findViewById(R.id.profile_image);
        TextView regno = (TextView) v.findViewById(R.id.regtext);

        name.setText(Prefs.getPrefs("name", getContext()));
        regno.setText(Prefs.getPrefs("regno", getContext()));

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ImageGallery.class));
            }
        });

        if(Prefs.getPrefs("readPermission",getContext()).equals("1")){
            setNavImage(v);
        }
        return v;
    }

    private void setCards(View v) {
        int card_id[]={R.id.timetable,R.id.attendance,R.id.course_page,R.id.marks,R.id.event,
                R.id.search_faculty,R.id.assignment,R.id.fac_message,R.id.exam_schedule,R.id.grades,R.id.request,R.id.def_password};
        for(int i=0;i<card_id.length;i++){
            ((CardView)v.findViewById(card_id[i])).setCardBackgroundColor(Color.parseColor(SetTheme.colorName));
        }
        CardView timetableCard=(CardView)v.findViewById(card_id[0]);
        CardView facultycard=(CardView)v.findViewById(card_id[5]);

        timetableCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TimeTable.class));
            }
        });

        facultycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Faculties.class));
            }
        });


    }

    private void setNavImage(View v) {

        try {
            de.hdodenhof.circleimageview.CircleImageView profile=(de.hdodenhof.circleimageview.CircleImageView)v.findViewById(R.id.profile_image);
            Prefs.setPrefs("profileimage",Prefs.getPrefs("profileimage",getContext()),getContext());
            Bitmap photo = null;
            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver()
                    , Uri.parse(Prefs.getPrefs("profileimage",getActivity())));
//            photo = getResizedBitmap(photo, 400, 400);
            profile.setImageBitmap(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
