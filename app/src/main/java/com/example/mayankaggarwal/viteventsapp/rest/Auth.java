package com.example.mayankaggarwal.viteventsapp.rest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.LoginRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginResponse;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class Auth {

    public static void login(String regno, String password, final Activity activity, final OnLoginCallback onLoginCallback) {


        ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.regno = regno;
        loginRequest.password = password;


        Call<LoginResponse> login = apiInterface.login(loginRequest);

        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                try {
                    if (response.body() != null) {
                        if (response.body().code.equals("200")) {
                            Prefs.setPrefs("loggedIn","true", activity);
                            Prefs.setPrefs("Name", response.body().message.toString(), activity);
                            onLoginCallback.onSuccess();
                        } else {
                            final AlertDialog.Builder alert=new AlertDialog.Builder(activity);
                            LayoutInflater inflater = activity.getLayoutInflater();
                            final View dialogView = inflater.inflate(R.layout.error_window, null);
                            ImageButton ok= (ImageButton) dialogView.findViewById(R.id.errorImageOkButton);
                            alert.setView(dialogView);
                            final AlertDialog alertDialog=alert.create();

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("click","ckicking ckicking ckicking ckicking");
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();
                            onLoginCallback.onFailure();
                        }
                    } else {
                        onLoginCallback.onFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onLoginCallback.onFailure();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onLoginCallback.onFailure();
            }
        });

    }


    public static String getToken(Context context) {
        String token = Prefs.getPrefs("token", context);
        return token;
    }


    public interface OnLoginCallback {
        void onSuccess();

        void onFailure();
    }


}
