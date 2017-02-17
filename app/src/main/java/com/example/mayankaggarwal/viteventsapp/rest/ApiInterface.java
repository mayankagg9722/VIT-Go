package com.example.mayankaggarwal.viteventsapp.rest;

import com.example.mayankaggarwal.viteventsapp.models.AttendanceRequest;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceResponse;
import com.example.mayankaggarwal.viteventsapp.models.DARequest;
import com.example.mayankaggarwal.viteventsapp.models.DAResponse;
import com.example.mayankaggarwal.viteventsapp.models.LoginRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginResponse;
import com.example.mayankaggarwal.viteventsapp.models.TimetableRequest;
import com.example.mayankaggarwal.viteventsapp.models.TimetableResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public interface ApiInterface {

    @POST("checkLogin")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("getAttendance")
    Call<AttendanceResponse> attendance(@Body AttendanceRequest attendanceRequest);

    @POST("getTimetable")
    Call<JsonObject> timetable(@Body TimetableRequest timetableRequest);

    @POST("detailAttendance")
    Call<DAResponse> detaialAttendance(@Body DARequest daRequest);

}
