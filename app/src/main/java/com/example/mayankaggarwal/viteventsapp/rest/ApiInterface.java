package com.example.mayankaggarwal.viteventsapp.rest;

import com.example.mayankaggarwal.viteventsapp.models.LoginRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public interface ApiInterface {

    @POST("checkLogin")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}
