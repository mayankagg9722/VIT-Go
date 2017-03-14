package com.example.mayankaggarwal.viteventsapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;


import com.example.mayankaggarwal.viteventsapp.Faculties;
import com.example.mayankaggarwal.viteventsapp.FacultyInformation;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceRequest;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceResponse;
import com.example.mayankaggarwal.viteventsapp.models.CouresePage;
import com.example.mayankaggarwal.viteventsapp.models.CouresePageResponse;
import com.example.mayankaggarwal.viteventsapp.models.CoursePageRequest;
import com.example.mayankaggarwal.viteventsapp.models.DARequest;
import com.example.mayankaggarwal.viteventsapp.models.DAResponse;
import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesData;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesList;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetails;
import com.example.mayankaggarwal.viteventsapp.models.FacultyDetailsRequest;
import com.example.mayankaggarwal.viteventsapp.models.TimetableRequest;
import com.example.mayankaggarwal.viteventsapp.rest.ApiClient;
import com.example.mayankaggarwal.viteventsapp.rest.ApiInterface;
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
// //               Log.d("tagg", "in async");
                List<AttendanceList> attendenceList = attendance.execute().body().data;
// //             Log.d("tagg",String.valueOf(attendenceList));
//                Realm realm=RealmController.getInstance().getRealm();
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
// //                   Log.d("tagg",e.getCourseCode().toString());
                }
                realm.close();
            } catch (Exception e) {
                e.printStackTrace();
// //               Log.d("tagg", "exceptionthrowm");
//                updateCallback.onFailure();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of async");
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
//                Log.d("tagg", "in timetable async");

                //store time table in shared preferences
                Prefs.setPrefs("myTimetable", timetable.execute().body().toString(), activity);

            } catch (Exception e) {
                e.printStackTrace();
//                Log.d("tagg", "exceptionthrowm");
//                updateCallback.onFailure();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
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

//            Log.d("tagg",classnbr);

            final Call<DAResponse> daResponseCall = apiInterface.detaialAttendance(daRequest);

            try {
//                Log.d("tagg", "in async");
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
//                Log.d("tagg", "exceptionthrowm");
//                updateCallback.onFailure();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
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

//            Log.d("tagg",classnbr);

            final Call<CouresePageResponse> coursePageRequestCall = apiInterface.getCoursePage(coursePageRequest);

            try {
//                Log.d("tagg", "in async");
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
// //                   Log.d("tagg",e.getCourseCode().toString());
                }
                realm.close();

            } catch (Exception e) {
                e.printStackTrace();
// //               Log.d("tagg", "exceptionthrowm");
//                updateCallback.onFailure();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
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

//            Log.d("tagg",classnbr);

            final Call<FacultiesData> facultiesListCall = apiInterface.getFaculties();

            try {
//                Log.d("tagg", "in async");
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
// //                   Log.d("tagg",e.getCourseCode().toString());
                }
                realm.close();

            } catch (Exception e) {
                e.printStackTrace();
// //               Log.d("tagg", "exceptionthrowm");
//                updateCallback.onFailure();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
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

//            Log.d("G", "doInBackground: "+facultyDetailsRequest.empid);


            final Call<FacultyDetails> facultyDetailsCall = apiInterface.getFacultyDetails(facultyDetailsRequest);

            facultyDetailsCall.enqueue(new Callback<FacultyDetails>() {
                @Override
                public void onResponse(Call<FacultyDetails> call, Response<FacultyDetails> response) {
                    if(response.body().getCode().equals("200")){

                        Globals.faculty_designation = response.body().getDesignation().toString();
                        Globals.faculty_venue = response.body().getVenue().toString();
                        Globals.faculty_email = response.body().getEmail().toString();
                        Globals.faculty_intercom = response.body().getIntercom().toString();
                        Globals.faculty_openhours = response.body().getOpenHours();
                        Log.d("TAG", "onResponse: "+"working cool...."+Globals.faculty_email);

                        FacultyInformation.deg.setText(Globals.faculty_designation);
                        FacultyInformation.venue.setText(Globals.faculty_venue);
                        FacultyInformation.intercom.setText(Globals.faculty_intercom);
                        FacultyInformation.mail.setText(Globals.faculty_email);
                    }
                }
                @Override
                public void onFailure(Call<FacultyDetails> call, Throwable t) {

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
}
