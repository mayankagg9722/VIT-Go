package com.example.mayankaggarwal.viteventsapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;


import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceRequest;
import com.example.mayankaggarwal.viteventsapp.models.AttendanceResponse;
import com.example.mayankaggarwal.viteventsapp.models.TimetableRequest;
import com.example.mayankaggarwal.viteventsapp.models.TimetableResponse;
import com.example.mayankaggarwal.viteventsapp.rest.ApiClient;
import com.example.mayankaggarwal.viteventsapp.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class Data {

    public static void updateAttendance(final Activity activity,final UpdateCallback updateCallback) {
        GetAttendance getAttendance = new GetAttendance(updateCallback);
        getAttendance.execute(activity);
    }

    public static void updateTimetable (final Activity activity,final UpdateCallback updateCallback){

        GetTimetable getTimetable = new GetTimetable(updateCallback);
        getTimetable.execute(activity);

    }


    public static class GetAttendance extends AsyncTask<Activity, Void, Integer> {

        UpdateCallback updateCallback;

        GetAttendance(UpdateCallback updateCallback) {
            this.updateCallback = updateCallback;
        }

        @Override
        protected Integer doInBackground(Activity... params) {
            final Activity activity=params[0];
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
// //                Log.d("tagg",String.valueOf(attendenceList));
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(AttendanceList.class);
                realm.commitTransaction();
                for (final AttendanceList e : attendenceList ) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(e);
                        }
                    });
// //                   Log.d("tagg",e.getCourseCode().toString());
                }
                realm.close();
            }catch (Exception e){e.printStackTrace();
// //               Log.d("tagg", "exceptionthrowm");
                updateCallback.onFailure();
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
            final Activity activity=params[0];

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
                Prefs.setPrefs("myTimetable",timetable.execute().body().toString(),activity);

            }catch (Exception e){e.printStackTrace();
 //               Log.d("tagg", "exceptionthrowm");
                updateCallback.onFailure();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            Log.d("tagg","out of timetable async");
            updateCallback.onUpdate();
        }
    }

    public interface UpdateCallback {
        void onUpdate();
        void onFailure();
    }
}
