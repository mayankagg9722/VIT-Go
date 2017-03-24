package com.example.mayankaggarwal.viteventsapp.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mayankaggarwal.viteventsapp.MainActivity;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.adapter.RVAttendaceList;
import com.example.mayankaggarwal.viteventsapp.adapter.RVExamShedule;
import com.example.mayankaggarwal.viteventsapp.adapter.RVLeave;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment {


   private  RecyclerView recyclerView;
    public ExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;
        Log.d("tagg","working");
        view=inflater.inflate(R.layout.fragment_exam, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.exam);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RVExamShedule(RealmController.with(this).getCatone(),getActivity()));
//        recyclerView.setAdapter(new RVExamShedule(RealmController.with(this).getCattwo(),getActivity()));
//        recyclerView.setAdapter(new RVExamShedule(RealmController.with(this).getCatone(),getActivity()));
        return view;
    }

}
