package com.example.mayankaggarwal.viteventsapp.rest;

import com.example.mayankaggarwal.viteventsapp.models.AttendanceRequest;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceResponse;
import com.example.mayankaggarwal.viteventsapp.models.CancelNightRequest;
import com.example.mayankaggarwal.viteventsapp.models.CancelRequest;
import com.example.mayankaggarwal.viteventsapp.models.CouresePageResponse;
import com.example.mayankaggarwal.viteventsapp.models.CoursePageRequest;
import com.example.mayankaggarwal.viteventsapp.models.DARequest;
import com.example.mayankaggarwal.viteventsapp.models.DAResponse;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksRequest;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksResponse;
import com.example.mayankaggarwal.viteventsapp.models.EventData;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesData;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetails;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetailsRequest;
import com.example.mayankaggarwal.viteventsapp.models.HomeTownRequest;
import com.example.mayankaggarwal.viteventsapp.models.LateNightResponse;
import com.example.mayankaggarwal.viteventsapp.models.LateRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginResponse;
import com.example.mayankaggarwal.viteventsapp.models.RegisterEventRequest;
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

    @POST("registerEvent")
    Call<JsonObject> getEventRegister(@Body RegisterEventRequest registerEventRequest);

    @POST("getMessages")
    Call<JsonObject> getMessages(@Body LoginRequest loginRequest);

    @POST("getLeaves")
    Call<JsonObject> getLeaves(@Body LoginRequest loginRequest);

    @POST("getLateHourPermission")
    Call<LateNightResponse> getLateNight(@Body LoginRequest loginRequest);

    @POST("cancelLeave")
    Call<JsonObject> cancelLeave(@Body CancelRequest cancelRequest);

    @POST("cancelLateHour")
    Call<JsonObject> cancelLateNight(@Body CancelNightRequest cancelNightRequest);

    @POST("applyHometownLeave")
    Call<JsonObject> applyHomeTown(@Body HomeTownRequest homeTownRequest);

    @POST("getExamSchedule")
    Call<JsonObject> getExamSchedule(@Body LoginRequest loginRequest);

    @POST("applyLateHour")
    Call<JsonObject> applyLateNight(@Body LateRequest lateRequest);

    @POST("getDigitalAssignments")
    Call<JsonObject> getDigitalAssignment(@Body LoginRequest loginRequest);

    @POST("getDigitalAssignmentMarks")
    Call<DigitalMarksResponse> getDigitalAssignmentMarks(@Body DigitalMarksRequest digitalMarksRequest);

}
