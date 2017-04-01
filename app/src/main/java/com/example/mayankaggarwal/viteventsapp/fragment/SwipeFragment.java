package com.example.mayankaggarwal.viteventsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.mayankaggarwal.viteventsapp.R;


public class SwipeFragment extends android.support.v4.app.Fragment {


    ImageView imageView;
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

        int [] drawables={R.drawable.monday,R.drawable.tuesday,R.drawable.wednesday,R.drawable.thursday,
                R.drawable.friday,R.drawable.saturday,R.drawable.saturday};
        View view;
        view=inflater.inflate(R.layout.fragment_swipe,container,false);
        imageView=(ImageView)view.findViewById(R.id.dayimage);
        imageView.setImageResource(drawables[count]);
        return view;
    }



}
