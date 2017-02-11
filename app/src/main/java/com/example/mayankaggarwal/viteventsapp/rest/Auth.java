package com.example.mayankaggarwal.viteventsapp.rest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

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

    public static void login(String regno, String password, final Activity activity,final OnLoginCallback onLoginCallback){

        ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.regno = regno;
        loginRequest.password = password;


        Call<LoginResponse> login = apiInterface.login(loginRequest);

        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("response", String.valueOf(response));

                try {
                    if (response.body() != null && response.body().success) {

                        Prefs.setPrefs("token", response.body().token, activity);
                        Prefs.setPrefs("login", "1", activity);


                        onLoginCallback.onSuccess();
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
        String token=Prefs.getPrefs("token",context);
        return token;
    }


    public interface OnLoginCallback {
        void onSuccess();
        void onFailure();
    }




}
