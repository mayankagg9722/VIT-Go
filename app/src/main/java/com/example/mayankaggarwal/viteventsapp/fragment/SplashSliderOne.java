package com.example.mayankaggarwal.viteventsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankaggarwal.viteventsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashSliderOne extends Fragment {


    public SplashSliderOne() {
        // Required empty public constructor
    }

    public static SplashSliderOne newInstance() {
        return new SplashSliderOne();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_slider_one, container, false);
    }

}
