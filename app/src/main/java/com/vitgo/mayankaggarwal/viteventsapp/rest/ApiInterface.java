package com.vitgo.mayankaggarwal.viteventsapp.rest;

import com.vitgo.mayankaggarwal.viteventsapp.models.AttendanceRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.AttendanceResponse;
import com.vitgo.mayankaggarwal.viteventsapp.models.CancelNightRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.CancelRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.CouresePageResponse;
import com.vitgo.mayankaggarwal.viteventsapp.models.CoursePageRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.DARequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.DAResponse;
import com.vitgo.mayankaggarwal.viteventsapp.models.DigitalMarksRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.DigitalMarksResponse;
import com.vitgo.mayankaggarwal.viteventsapp.models.EventData;
import com.vitgo.mayankaggarwal.viteventsapp.models.FacultiesData;
import com.vitgo.mayankaggarwal.viteventsapp.models.FacultyDetails;
import com.vitgo.mayankaggarwal.viteventsapp.models.FacultyDetailsRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.HomeTownRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.LateNightResponse;
import com.vitgo.mayankaggarwal.viteventsapp.models.LateRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.LoginRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.LoginResponse;
import com.vitgo.mayankaggarwal.viteventsapp.models.RegisterEventRequest;
import com.vitgo.mayankaggarwal.viteventsapp.models.TimetableRequest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public interface ApiInterface {

    @POST("checkCookieLogin")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("getCookieAttendance")
    Call<AttendanceResponse> attendance(@Body AttendanceRequest attendanceRequest);

    @POST("getCookieTimetable")
    Call<JsonObject> timetable(@Body TimetableRequest timetableRequest);

    @POST("detailCookieAttendance")
    Call<DAResponse> detaialAttendance(@Body DARequest daRequest);

    @POST("getCookieCoursePage")
    Call<CouresePageResponse> getCoursePage(@Body CoursePageRequest coursePageRequest);

    @GET("faculties.json")
    Call<FacultiesData> getFaculties();

    @POST("getCookieFaculty")
    Call<FacultyDetails> getFacultyDetails(@Body FacultyDetailsRequest facultyDetailsRequest);

    @GET("getCookieEventsAndroid")
    Call<EventData> getEvent();

    @POST("registerCookieEvent")
    Call<JsonObject> getEventRegister(@Body RegisterEventRequest registerEventRequest);

    @POST("getCookieMessages")
    Call<JsonObject> getMessages(@Body LoginRequest loginRequest);

    @POST("getCookieLeaves")
    Call<JsonObject> getLeaves(@Body LoginRequest loginRequest);

    @POST("getCookieLateHourPermission")
    Call<LateNightResponse> getLateNight(@Body LoginRequest loginRequest);

    @POST("cancelCookieLeave")
    Call<JsonObject> cancelLeave(@Body CancelRequest cancelRequest);

    @POST("cancelCookieLateHour")
    Call<JsonObject> cancelLateNight(@Body CancelNightRequest cancelNightRequest);

    @POST("applyCookieHometownLeave")
    Call<JsonObject> applyHomeTown(@Body HomeTownRequest homeTownRequest);

    @POST("getCookieExamSchedule")
    Call<JsonObject> getExamSchedule(@Body LoginRequest loginRequest);

    @POST("applyCookieLateHour")
    Call<JsonObject> applyLateNight(@Body LateRequest lateRequest);

    @POST("getCookieDigitalAssignments")
    Call<JsonObject> getDigitalAssignment(@Body LoginRequest loginRequest);

    @POST("getCookieDigitalAssignmentMarks")
    Call<DigitalMarksResponse> getDigitalAssignmentMarks(@Body DigitalMarksRequest digitalMarksRequest);

    @POST("getCookieMarks2")
    Call<JsonObject> getMarks(@Body LoginRequest loginRequest);


}
