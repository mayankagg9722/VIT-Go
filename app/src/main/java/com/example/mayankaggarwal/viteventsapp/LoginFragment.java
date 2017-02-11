package com.example.mayankaggarwal.viteventsapp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.rest.Auth;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;

public class LoginFragment extends SlideFragment {

    private EditText username;

    private EditText password;

    private ImageButton login;

    private boolean loggedIn = false;

    ProgressBar progressBar;



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


        progressBar = new ProgressBar(root.getContext());
        progressBar.setVisibility(View.INVISIBLE);

        username.setEnabled(!loggedIn);
        password.setEnabled(!loggedIn);
        login.setEnabled(!loggedIn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setEnabled(false);
                password.setEnabled(false);
                login.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Logging in. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                Auth.login(username.getText().toString(), password.getText().toString(), getActivity(), new Auth.OnLoginCallback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                        dialog.hide();
                    }

                    @Override
                    public void onFailure() {
                        progressBar.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(getContext())
                                .setMessage("Invalid Credentials!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        dialog.hide();
                    }
                });

//                Intent intent=new Intent(container.getContext(),MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();
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
