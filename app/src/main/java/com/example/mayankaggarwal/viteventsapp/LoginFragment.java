package com.example.mayankaggarwal.viteventsapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;

public class LoginFragment extends SlideFragment {

    private EditText username;

    private EditText password;

    private ImageButton login;

    private boolean loggedIn = false;



    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        username = (EditText) root.findViewById(R.id.regno);
        password = (EditText) root.findViewById(R.id.password);
        login = (ImageButton) root.findViewById(R.id.login);

        username.setEnabled(!loggedIn);
        password.setEnabled(!loggedIn);
        login.setEnabled(!loggedIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setEnabled(false);
                password.setEnabled(false);
                login.setEnabled(false);

                Intent intent=new Intent(container.getContext(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
//        loginHandler.removeCallbacks(loginRunnable);
        super.onDestroy();
    }

    @Override
    public boolean canGoForward() {
        return loggedIn;
    }
}
