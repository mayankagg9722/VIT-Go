package com.example.mayankaggarwal.viteventsapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.mayankaggarwal.viteventsapp.MainActivity;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.rest.Auth;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.rest.Data;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;

public class LoginFragment extends SlideFragment {

    private EditText username;
    private EditText password;
    private ImageButton login;
    private boolean loggedIn = false;
    private ProgressBar progressBar;


    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        hideKeyboard(root);

        username = (EditText) root.findViewById(R.id.regno);
        password = (EditText) root.findViewById(R.id.password);
        login = (ImageButton) root.findViewById(R.id.login);


        progressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);


        username.setEnabled(!loggedIn);
        password.setEnabled(!loggedIn);
        login.setEnabled(!loggedIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                Auth.login(username.getText().toString(), password.getText().toString(), getActivity(), new Auth.OnLoginCallback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        CustomProgressDialog.showProgress(getActivity(),"Fetching Attendance and Timetable..." );

                        //fetch timetable
                        fetchTimetable(getActivity());
                    }
                    @Override
                    public void onFailure() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });

        return root;
    }

    private void hideKeyboard(View root) {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    View view = v.getRootView().findFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    return true;
                }
                return true;
            }
        });
    }

    private void fetchAttendance(final Activity activity) {
        if (!(RealmController.with(activity).hasAttendance())) {
//            Log.d("tagg", "skip already has attendance");
            Data.updateAttendance(activity, new Data.UpdateCallback() {
                @Override
                public void onUpdate() {
//                    Log.d("tagg", "success api");
                    CustomProgressDialog.hideProgress();
                    getActivity().finish();
                    startActivity(new Intent(activity, MainActivity.class));
                }

                @Override
                public void onFailure() {

//                    Log.d("tagg", "fail api");
                    //agin try to fetch attendance
                    CustomProgressDialog.hideProgress();
                }
            });
        }else{
            CustomProgressDialog.hideProgress();
        }
    }

    private void fetchTimetable(final Activity activity) {

        Data.updateTimetable(activity, new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                //Log.d("tagg","success api");
                //fetching attendance
                fetchAttendance(activity);
            }
            @Override
            public void onFailure() {
//              Log.d("tagg","fail api");
                Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                CustomProgressDialog.hideProgress();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean canGoForward() {
        return loggedIn;
    }
}
