package com.example.mayankaggarwal.viteventsapp.rest;

import com.example.mayankaggarwal.viteventsapp.models.AttendanceRequest;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceResponse;
import com.example.mayankaggarwal.viteventsapp.models.CouresePageResponse;
import com.example.mayankaggarwal.viteventsapp.models.CoursePageRequest;
import com.example.mayankaggarwal.viteventsapp.models.DARequest;
import com.example.mayankaggarwal.viteventsapp.models.DAResponse;
import com.example.mayankaggarwal.viteventsapp.models.EventData;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesData;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesList;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetails;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetailsRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginResponse;
import com.example.mayankaggarwal.viteventsapp.models.TimetableRequest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    @POST("getCoursePage")
    Call<CouresePageResponse> getCoursePage(@Body CoursePageRequest coursePageRequest);

    @GET("faculties.json")
    Call<FacultiesData> getFaculties();

    @POST("getFaculty")
    Call<FacultyDetails> getFacultyDetails(@Body FacultyDetailsRequest facultyDetailsRequest);

    @GET("getEventsAndroid")
    Call<EventData> getEvent();


}
