package com.example.mayankaggarwal.viteventsapp.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;


public class SwipeFragment extends android.support.v4.app.Fragment {


    ImageView imageView;
//    TextView textView;
    private int count=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        count=bundle.getInt("count");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int [] drawables={R.drawable.monday,R.drawable.tuesday,R.drawable.wednesday,R.drawable.thursday,
                R.drawable.friday,R.drawable.saturday,R.drawable.saturday};
        View view;
        view=inflater.inflate(R.layout.fragment_swipe,container,false);
//        imageView=(ImageView)view.findViewById(R.id.swipeimage);
//        imageView.setImageResource("This is "+Integer.toString(bundle.getInt("count"))+" swipe screen..");
//        textView=(TextView)view.findViewById(R.id.textswipe);
//        textView.setText(Integer.toString(count));
        imageView=(ImageView)view.findViewById(R.id.dayimage);
        imageView.setImageResource(drawables[count]);
        return view;
    }



}
