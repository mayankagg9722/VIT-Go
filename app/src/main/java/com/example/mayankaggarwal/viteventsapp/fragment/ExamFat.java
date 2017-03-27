package com.example.mayankaggarwal.viteventsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVExamFat;
import com.example.mayankaggarwal.viteventsapp.adapter.RVExamSceduleTwo;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFat extends Fragment {

    private  RecyclerView recyclerView;

    public ExamFat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;
        view=inflater.inflate(R.layout.fragment_exam_fat, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.exam);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        if(!(Prefs.getPrefs("examschedule",getActivity()).equals("notfound"))){
//            Log.d("tagg","ggg");
            recyclerView.setAdapter(new RVExamFat(Prefs.getPrefs("examschedule",getActivity()),getActivity()));
        }
        else {
//            Log.d("tagg","kkk");
        }
        return view;
    }

}
