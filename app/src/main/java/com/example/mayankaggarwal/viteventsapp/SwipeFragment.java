package com.example.mayankaggarwal.viteventsapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class SwipeFragment extends android.support.v4.app.Fragment {


    ImageView imageView;
    public static int pos=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_swipe,container,false);
        imageView=(ImageView)view.findViewById(R.id.swipeimage);
        Bundle bundle=getArguments();
        pos=bundle.getInt("count");
        Log.d("tagg","Pos:"+SwipeFragment.pos);
//        imageView.setImageResource("This is "+Integer.toString(bundle.getInt("count"))+" swipe screen..");
        return view;
    }

}
