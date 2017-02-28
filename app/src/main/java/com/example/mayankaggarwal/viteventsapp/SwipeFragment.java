package com.example.mayankaggarwal.viteventsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SwipeFragment extends android.support.v4.app.Fragment {


    ImageView imageView;
    TextView textView;
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
        View view;
        view=inflater.inflate(R.layout.fragment_swipe,container,false);
//        imageView=(ImageView)view.findViewById(R.id.swipeimage);
//        imageView.setImageResource("This is "+Integer.toString(bundle.getInt("count"))+" swipe screen..");
        textView=(TextView)view.findViewById(R.id.textswipe);
        textView.setText(Integer.toString(count));
        return view;
    }



}
