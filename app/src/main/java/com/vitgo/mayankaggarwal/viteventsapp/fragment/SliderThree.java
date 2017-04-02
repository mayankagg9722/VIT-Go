package com.vitgo.mayankaggarwal.viteventsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitgo.mayankaggarwal.viteventsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderThree extends Fragment {


    public SliderThree() {
        // Required empty public constructor
    }

    public static SliderThree newInstance() {
        return new SliderThree();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider_three, container, false);
    }

}
