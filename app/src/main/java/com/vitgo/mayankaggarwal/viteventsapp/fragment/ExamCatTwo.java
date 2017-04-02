package com.vitgo.mayankaggarwal.viteventsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vitgo.mayankaggarwal.viteventsapp.R;
import com.vitgo.mayankaggarwal.viteventsapp.adapter.RVExamSceduleTwo;
import com.vitgo.mayankaggarwal.viteventsapp.utils.Prefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamCatTwo extends Fragment {


    private  RecyclerView recyclerView;
    public static ImageView imageView;

    public ExamCatTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;
        view=inflater.inflate(R.layout.fragment_exam_cat_two, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.exam);
        imageView=(ImageView)view.findViewById(R.id.noclass);
        imageView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        if(!(Prefs.getPrefs("examschedule",getActivity()).equals("notfound"))){
            recyclerView.setAdapter(new RVExamSceduleTwo(Prefs.getPrefs("examschedule",getActivity()),getActivity()));
        }
        return view;
    }

}
