package com.example.mayankaggarwal.viteventsapp.rest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.example.mayankaggarwal.viteventsapp.activities.Hosteller;
import com.example.mayankaggarwal.viteventsapp.activities.LeaveRequest;
import com.example.mayankaggarwal.viteventsapp.activities.Events;
import com.example.mayankaggarwal.viteventsapp.activities.FacultyInformation;
import com.example.mayankaggarwal.viteventsapp.activities.OutingRequest;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceRequest;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceResponse;
import com.example.mayankaggarwal.viteventsapp.models.CancelNightRequest;
import com.example.mayankaggarwal.viteventsapp.models.CancelRequest;
import com.example.mayankaggarwal.viteventsapp.models.CouresePage;
import com.example.mayankaggarwal.viteventsapp.models.CouresePageResponse;
import com.example.mayankaggarwal.viteventsapp.models.CoursePageRequest;
import com.example.mayankaggarwal.viteventsapp.models.DARequest;
import com.example.mayankaggarwal.viteventsapp.models.DAResponse;
import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksData;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksRequest;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksResponse;
import com.example.mayankaggarwal.viteventsapp.models.EventData;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesData;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesList;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetails;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetailsRequest;
import com.example.mayankaggarwal.viteventsapp.models.HomeTownRequest;
import com.example.mayankaggarwal.viteventsapp.models.LateNightData;
import com.example.mayankaggarwal.viteventsapp.models.LateNightResponse;
import com.example.mayankaggarwal.viteventsapp.models.LateRequest;
import com.example.mayankaggarwal.viteventsapp.models.LoginRequest;
import com.example.mayankaggarwal.viteventsapp.models.RegisterEventRequest;
import com.example.mayankaggarwal.viteventsapp.models.TimetableRequest;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class Data {

    public static void updateAttendance(final Activity activity, final UpdateCallback updateCallback) {
        GetAttendance getAttendance = new GetAttendance(updateCallback);
        getAttendance.execute(activity);
    }

    public static void updateTimetable(final Activity activity, final UpdateCallback updateCallback) {

        GetTimetable getTimetable = new GetTimetable(updateCallback);
        getTimetable.execute(activity);

    }

    public static void updateFaculty(final Activity activity, final UpdateCallback updateCallback) {
        GetFaculties getFaculties = new GetFaculties(updateCallback);
        getFaculties.execute(activity);
    }

    public static void getFacultyDetails(final Activity activity, final UpdateCallback updateCallback) {
        GetFacultyDetails getFacultyDetails = new GetFacultyDetails(updateCallback);
        getFacultyDetails.execute(activity);
    }

    public static void getEvent(final Activity activity, final UpdateCallback updateCallback) {
        GetEvent getEvent = new GetEvent(updateCallback);
        getEvent.execute(activity);
    }

    public static void getEventRegister(final Activity activity, String id, List<String> field, final UpdateCallback updateCallback) {
        GetEventRegister getEventRegister = new GetEventRegister(updateCallback, id, field);
        getEventRegister.execute(activity);
    }

    public static void getMessages(final Activity activity, final UpdateCallback updateCallback) {
        GetMessages getMessages = new GetMessages(updateCallback);
        getMessages.execute(activity);
    }

    public static void getLeaves(final Activity activity, final UpdateCallback updateCallback) {
        GetLeaves getLeaves = new GetLeaves(updateCallback);
        getLeaves.execute(activity);
    }

    public static void getLateNight(final Activity activity, final UpdateCallback updateCallback) {
        GetLateNight getLateNight = new GetLateNight(updateCallback);
        getLateNight.execute(activity);
    }

    public static void submitHometownLeave(final Activity activity,final HomeTownRequest homeTownRequest, final UpdateCallback updateCallback) {
        SubmitHomeTownLeave submitHomeTownLeave = new SubmitHomeTownLeave(updateCallback,homeTownRequest);
        submitHomeTownLeave.execute(activity);
    }

    public static void submitLateNight(final Activity activity, final LateRequest lateRequest, final UpdateCallback updateCallback) {
        SubmitLateNight submitLateNight = new SubmitLateNight(updateCallback,lateRequest);
        submitLateNight.execute(activity);
    }

    public static void cancelLeave(final Activity activity,String leave_id, final UpdateCallback updateCallback) {
        CancelLeave cancelLeave = new CancelLeave(updateCallback,leave_id);
        cancelLeave.execute(activity);
    }

    public static void cancelLateHour(final Activity activity,String leave_id, final UpdateCallback updateCallback) {
        CancelLateNight cancleLateNight = new CancelLateNight(updateCallback,leave_id);
        cancleLateNight.execute(activity);
    }


    public static void getExamShedule(final Activity activity, final UpdateCallback updateCallback) {
        GetExamShedule getExamShedule = new GetExamShedule(updateCallback);
        getExamShedule.execute(activity);
    }

    public static void getDigitalAssignment(final Activity activity, final UpdateCallback updateCallback) {
        GetDigitalAssignment getDigitalAssignment = new GetDigitalAssignment(updateCallback);
        getDigitalAssignment.execute(activity);
    }

    public static void getDigitalAssignmentMarsk(final Activity activity,final DigitalMarksRequest digitalMarksRequest, final UpdateCallback updateCallback) {
        GetDigitalMarks getDigitalMarks = new GetDigitalMarks(updateCallback,digitalMarksRequest);
        getDigitalMarks.execute(activity);
    }

    public static void getMarks(final Activity activity, final UpdateCallback updateCallback) {
        GetMarks getMarks = new GetMarks(updateCallback);
        getMarks.execute(activity);
    }

    public static void updateDetailAttendance(final Activity activity, final UpdateCallback updateCallback) {
        GetDeatilAttendance getDeatilAttendance = new GetDeatilAttendance(updateCallback);
        getDeatilAttendance.execute(activity);
    }

    public static void updateCoursepage(final Activity activity, final UpdateCallback updateCallback) {
        GetCoursePage getCoursePage = new GetCoursePage(updateCallback);
        getCoursePage.execute(activity);
    }

    public static void internetConnection(final UpdateCallback updateCallback) {
        InternetConnection intenetConnection = new InternetConnection(updateCallback);
        intenetConnection.execute();
    }


    public static class GetAttendance extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetAttendance(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];
            final String regno = Prefs.getPrefs("regno", activity);
            final String password = Prefs.getPrefs("password", activity);

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            AttendanceRequest attendenceRequest = new AttendanceRequest();
            attendenceRequest.regno = regno;
            attendenceRequest.password = password;

            final Call<AttendanceResponse> attendance = apiInterface.attendance(attendenceRequest);


            try {
                List<AttendanceList> attendenceList = attendance.execute().body().data;
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(AttendanceList.class);
                realm.commitTransaction();
                for (final AttendanceList e : attendenceList) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
                }
                realm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }
    }

    public static class GetTimetable extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetTimetable(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            final String regno = Prefs.getPrefs("regno", activity);
            final String password = Prefs.getPrefs("password", activity);

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            TimetableRequest timetableRequest = new TimetableRequest();
            timetableRequest.regno = regno;
            timetableRequest.password = password;

            final Call<JsonObject> timetable = apiInterface.timetable(timetableRequest);

            try {
                Prefs.setPrefs("myTimetable", timetable.execute().body().toString(), activity);

            } catch (Exception e) {
                e.printStackTrace();

            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }
    }


    public static class GetDeatilAttendance extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetDeatilAttendance(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            final String classnbr = activity.getIntent().getStringExtra("classnbr");
            ;
            final String semcode = activity.getIntent().getStringExtra("semcode");
            final String crscd = activity.getIntent().getStringExtra("crscd");
            final String crstp = activity.getIntent().getStringExtra("crstp");
            final String fromDate = activity.getIntent().getStringExtra("from_date");
            final String toDate = activity.getIntent().getStringExtra("to_date");

            final String regno = Prefs.getPrefs("regno", activity);
            final String password = Prefs.getPrefs("password", activity);

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            DARequest daRequest = new DARequest();
            daRequest.classnbr = classnbr;
            daRequest.semcode = semcode;
            daRequest.crscd = crscd;
            daRequest.crstp = crstp;
            daRequest.fromDate = fromDate;
            daRequest.toDate = toDate;
            daRequest.regno = regno;
            daRequest.password = password;


            final Call<DAResponse> daResponseCall = apiInterface.detaialAttendance(daRequest);

            try {
                List<DetailAttendance> detailAttendances = daResponseCall.execute().body().data;


                List<DetailAttendance> detailedAttendance = new ArrayList<>();
                for (final DetailAttendance e : detailAttendances) {
                    detailedAttendance.add(e);
                }

                Globals.detailAttendances.add(detailedAttendance);


                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(DetailAttendance.class);
                realm.commitTransaction();

                for (final DetailAttendance e : detailAttendances) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
                }
                realm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }

    }

    public static class GetCoursePage extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetCoursePage(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            final String classnbr = activity.getIntent().getStringExtra("classnbr");
            ;
            final String semcode = activity.getIntent().getStringExtra("semcode");
            final String crscd = activity.getIntent().getStringExtra("crscd");
            final String crstp = activity.getIntent().getStringExtra("crstp");
            final String fromDate = activity.getIntent().getStringExtra("from_date");
            final String toDate = activity.getIntent().getStringExtra("to_date");
            final String regno = Prefs.getPrefs("regno", activity);
            final String password = Prefs.getPrefs("password", activity);

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            CoursePageRequest coursePageRequest = new CoursePageRequest();
            coursePageRequest.classnbr = classnbr;
            coursePageRequest.semcode = semcode;
            coursePageRequest.crscd = crscd;
            coursePageRequest.crstp = crstp;
            coursePageRequest.fromDate = fromDate;
            coursePageRequest.toDate = toDate;
            coursePageRequest.regno = regno;
            coursePageRequest.password = password;


            final Call<CouresePageResponse> coursePageRequestCall = apiInterface.getCoursePage(coursePageRequest);

            try {
                List<CouresePage> couresePages = coursePageRequestCall.execute().body().data;


                List<CouresePage> couresePage = new ArrayList<>();

                for (final CouresePage e : couresePages) {
                    couresePage.add(e);
                }

                Globals.couresePages.add(couresePage);


                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(CouresePage.class);
                realm.commitTransaction();

                for (final CouresePage e : couresePages) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
                }
                realm.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }

    }

    public static class GetFaculties extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetFaculties(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);


            final Call<FacultiesData> facultiesListCall = apiInterface.getFaculties();
            try {
                List<FacultiesList> facultiesDatas = facultiesListCall.execute().body().data;

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(FacultiesList.class);
                realm.commitTransaction();

                for (final FacultiesList e : facultiesDatas) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
                }
                realm.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }

    }


    public static class GetFacultyDetails extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetFacultyDetails(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            final String empid = activity.getIntent().getStringExtra("empid");
            final String regno = Prefs.getPrefs("regno", activity);
            final String password = Prefs.getPrefs("password", activity);

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            FacultyDetailsRequest facultyDetailsRequest = new FacultyDetailsRequest();
            facultyDetailsRequest.empid = empid;
            facultyDetailsRequest.regno = regno;
            facultyDetailsRequest.password = password;

            final Call<FacultyDetails> facultyDetailsCall = apiInterface.getFacultyDetails(facultyDetailsRequest);

            facultyDetailsCall.enqueue(new Callback<FacultyDetails>() {
                @Override
                public void onResponse(Call<FacultyDetails> call, Response<FacultyDetails> response) {
                    if (response.body().getCode().equals("200")) {

                        Globals.faculty_designation = response.body().getDesignation().toString();
                        Globals.faculty_venue = response.body().getVenue().toString();
                        Globals.faculty_email = response.body().getEmail().toString();
                        Globals.faculty_intercom = response.body().getIntercom().toString();
                        Globals.faculty_openhours = response.body().getOpenHours();
//                        Log.d("TAG", "onResponse: " + "working cool...." + Globals.faculty_email);

                        FacultyInformation.deg.setText(Globals.faculty_designation);
                        FacultyInformation.venue.setText(Globals.faculty_venue);
                        FacultyInformation.intercom.setText(Globals.faculty_intercom);
                        FacultyInformation.mail.setText(Globals.faculty_email);
                        FacultyInformation.freehour.setText(Globals.faculty_openhours.toString().replace("[", " ").replace("]", " ")
                                .replace(",", " and "));

                        CustomProgressDialog.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<FacultyDetails> call, Throwable t) {
                    updateCallback.onFailure();
                }
            });

            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
            updateCallback.onUpdate();
        }

    }


    public static class GetEvent extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetEvent(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];


            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            final Call<EventData> eventDataCall = apiInterface.getEvent();

            try {
                List<EventList> eventLists = eventDataCall.execute().body().data;

                Prefs.setPrefs("eventslist", eventLists.toString(), activity);

                List<EventList> events = new ArrayList<>();

                Globals.eventList.clear();

                for (final EventList e : eventLists) {
                    events.add(e);
                    Globals.eventList.add(e);
                }

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(EventList.class);
                realm.commitTransaction();

                for (final EventList e : events) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
                }
                realm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
            updateCallback.onUpdate();
        }

    }

    public static class GetEventRegister extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String id;
        List<String> fields;

        GetEventRegister(UpdateCallback updateCallback, String id, final List<String> field) {
            this.updateCallback = updateCallback;
            this.id = id;
            this.fields = field;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];


            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            final RegisterEventRequest registerEventRequest = new RegisterEventRequest();

            registerEventRequest.regno = Prefs.getPrefs("regno", activity);
            registerEventRequest.password = Prefs.getPrefs("password", activity);
            registerEventRequest.name = Prefs.getPrefs("name", activity);
            ;
            registerEventRequest.eventId = id;
            registerEventRequest.fields = fields;


            final Call<JsonObject> jsonObjectCall = apiInterface.getEventRegister(registerEventRequest);

            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    Log.d("tagg",response.body().getAsJsonObject().get("data").toString());
                    if (response.body().get("data").toString().contains("success")) {
                        Intent intent = new Intent(activity, Events.class);
                        intent.putExtra("eventid", id);
                        activity.finish();
                        activity.startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }

    }


    public static class GetMessages extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetMessages(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];


            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.regno = Prefs.getPrefs("regno", activity);
            loginRequest.password = Prefs.getPrefs("password", activity);

            final Call<JsonObject> apiInterfaceMessages = apiInterface.getMessages(loginRequest);

            apiInterfaceMessages.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Prefs.setPrefs("messages", response.body().toString(), activity);
                    updateCallback.onUpdate();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    updateCallback.onFailure();
                }
            });
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {
        }

    }

    public static class CancelLeave extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String leave_id;

        CancelLeave(UpdateCallback updateCallback,String leave_id) {
            this.updateCallback = updateCallback;
            this.leave_id=leave_id;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            CancelRequest cancelRequest = new CancelRequest();
            cancelRequest.regno = Prefs.getPrefs("regno", activity);
            cancelRequest.password = Prefs.getPrefs("password", activity);
            cancelRequest.leave_id=leave_id;

            final Call<JsonObject> apiInterfaceMessages = apiInterface.cancelLeave(cancelRequest);

            apiInterfaceMessages.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("tagg",response.body().toString());
                    updateCallback.onUpdate();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    updateCallback.onFailure();
                }
            });
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {

        }

    }

    public static class CancelLateNight extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        String permit_id;

        CancelLateNight(UpdateCallback updateCallback,String leave_id) {
            this.updateCallback = updateCallback;
            this.permit_id=leave_id;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            CancelNightRequest cancelLateNight = new CancelNightRequest();
            cancelLateNight.regno = Prefs.getPrefs("regno", activity);
            cancelLateNight.password = Prefs.getPrefs("password", activity);
            cancelLateNight.pvPermitID=permit_id;

            final Call<JsonObject> apiInterfaceMessages = apiInterface.cancelLateNight(cancelLateNight);

            apiInterfaceMessages.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("tagg",response.body().toString());
                    updateCallback.onUpdate();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    updateCallback.onFailure();
                }
            });
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {

        }

    }



    public static class GetLeaves extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetLeaves(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.regno = Prefs.getPrefs("regno", activity);
            loginRequest.password = Prefs.getPrefs("password", activity);

            final Call<JsonObject> apiInterfaceMessages = apiInterface.getLeaves(loginRequest);

            apiInterfaceMessages.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonArray names = response.body().get("approvingAuthorities").getAsJsonArray();

                    Prefs.setPrefs("leaves",response.body().get("leaves").toString(),activity);

                    for (JsonElement str : names) {
                        LeaveRequest.fac_name.add(str.getAsString());
                        OutingRequest.fac_name.add(str.getAsString());
                    }

                    JsonArray ids = response.body().get("approvingAuthoritiesPostValues").getAsJsonArray();

                    for (JsonElement st : ids) {
                        LeaveRequest.fac_id.add(st.getAsString());
                        OutingRequest.fac_id.add(st.getAsString());
                    }

                    updateCallback.onUpdate();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    updateCallback.onFailure();
                }
            });
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {

        }

    }

    public static class GetLateNight extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetLateNight(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.regno = Prefs.getPrefs("regno", activity);
            loginRequest.password = Prefs.getPrefs("password", activity);

            final Call<LateNightResponse> apiInterfaceMessages = apiInterface.getLateNight(loginRequest);

            try {
                List<LateNightData> lateNightData = apiInterfaceMessages.execute().body().data;

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(LateNightData.class);
                realm.commitTransaction();

                for (final LateNightData e : lateNightData) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
                }
                realm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }

    }

    public static class SubmitHomeTownLeave extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        HomeTownRequest homeTownRequest;

        SubmitHomeTownLeave(UpdateCallback updateCallback,HomeTownRequest homeTownRequest) {
            this.updateCallback = updateCallback;
            this.homeTownRequest=homeTownRequest;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            homeTownRequest.regno = Prefs.getPrefs("regno", activity);
            homeTownRequest.password = Prefs.getPrefs("password", activity);

            final Call<JsonObject> apiInterfaceMessages = apiInterface.applyHomeTown(homeTownRequest);

            apiInterfaceMessages.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    Log.d("tagg", response.body().toString());
                    if(response.body().get("code").getAsString().equals("200")){
                        activity.finish();
                        activity.startActivity(new Intent(activity, Hosteller.class));
                    }
                    updateCallback.onUpdate();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    updateCallback.onFailure();
                }
            });
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {

        }

    }

    public static class SubmitLateNight extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        LateRequest lateRequest;

        SubmitLateNight(UpdateCallback updateCallback,LateRequest lateRequest) {
            this.updateCallback = updateCallback;
            this.lateRequest=lateRequest;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            lateRequest.regno = Prefs.getPrefs("regno", activity);
            lateRequest.password = Prefs.getPrefs("password", activity);

            final Call<JsonObject> apiInterfaceMessages = apiInterface.applyLateNight(lateRequest);

            apiInterfaceMessages.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("tagg", response.body().toString());
                    if(response.body().get("code").getAsString().equals("200")){
                        activity.finish();
                        activity.startActivity(new Intent(activity, Hosteller.class));
                    }
                    updateCallback.onUpdate();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    updateCallback.onFailure();
                }
            });
            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {

        }

    }


    public static class GetExamShedule extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetExamShedule(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];


            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.regno = Prefs.getPrefs("regno", activity);
            loginRequest.password = Prefs.getPrefs("password", activity);


            final Call<JsonObject> eventDataCall = apiInterface.getExamSchedule(loginRequest);

            try {

                JsonObject jsonObject=eventDataCall.execute().body();

                Prefs.setPrefs("examschedule",jsonObject.toString(),activity);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
            updateCallback.onUpdate();
        }

    }

    public static class GetDigitalAssignment extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetDigitalAssignment(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.regno = Prefs.getPrefs("regno", activity);
            loginRequest.password = Prefs.getPrefs("password", activity);

            final Call<JsonObject> attendance = apiInterface.getDigitalAssignment(loginRequest);

            try {

                JsonObject jsonObject=attendance.execute().body();

                Prefs.setPrefs("digitalassignment",jsonObject.toString(),activity);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }
    }

    public static class GetDigitalMarks extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;
        DigitalMarksRequest digitalMarksRequest;

        GetDigitalMarks(UpdateCallback updateCallback,DigitalMarksRequest digitalMarksRequest) {
            this.updateCallback = updateCallback;
            this.digitalMarksRequest=digitalMarksRequest;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            this.digitalMarksRequest.regno = Prefs.getPrefs("regno", activity);
            this.digitalMarksRequest.password = Prefs.getPrefs("password", activity);

            final Call<DigitalMarksResponse> digitalAssignmentMarks = apiInterface.getDigitalAssignmentMarks(digitalMarksRequest);

            try {
                List<DigitalMarksData> digitalMarksResponses = digitalAssignmentMarks.execute().body().data;
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(DigitalMarksData.class);
                realm.commitTransaction();

                for (final DigitalMarksData e : digitalMarksResponses) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
                }
                realm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }
    }

    public static class GetMarks extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetMarks(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity = params[0];

            ApiInterface apiInterface = new ApiClient().getClient(activity).create(ApiInterface.class);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.regno = Prefs.getPrefs("regno", activity);
            loginRequest.password = Prefs.getPrefs("password", activity);

            final Call<JsonObject> marks = apiInterface.getMarks(loginRequest);

            try {

                JsonObject jsonObject=marks.execute().body();

                Prefs.setPrefs("marksjson",jsonObject.toString(),activity);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            updateCallback.onUpdate();
        }
    }


    public static class InternetConnection extends AsyncTask<Void, Void, Boolean> {

        UpdateCallback updateCallback;

        InternetConnection(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Runtime runtime = Runtime.getRuntime();
            try {

                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                int exitValue = ipProcess.waitFor();
                return (exitValue == 0);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool == true) {
                updateCallback.onUpdate();
            } else {
                updateCallback.onFailure();
            }
        }

    }


    public interface UpdateCallback {
        void onUpdate();

        void onFailure();
    }


//    public static class DownloadImageTask extends AsyncTask<String, Void, Integer> {
//        ImageView bmImage;
//        Activity activity;
//        Picasso picasso;
//
//        public DownloadImageTask(ImageView bmImage,Activity activity) {
//            this.bmImage = bmImage;
//            this.activity=activity;
//        }
//
//        protected Integer doInBackground(String... urls) {
//
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return 0;
//        }
//
//        protected void onPostExecute(int result) {
//            Log.d("tagg", "onPostExecute: "+result);
//            if(result!=null){
//                float aspectRatio = result.getWidth() /
//                        (float) result.getHeight();
//                int width = 500;
//                int height = Math.round(width / aspectRatio);
//
//                result = Bitmap.createScaledBitmap(
//                        result, width, height, false);
//                bmImage.setImageBitmap(result);
//            }else{
//                bmImage.setImageResource(R.drawable.unknown);
//            }
//        }
//    }

}
