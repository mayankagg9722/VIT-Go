package com.example.mayankaggarwal.viteventsapp.RealmFiles;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.mayankaggarwal.viteventsapp.models.AttendanceList;
import com.example.mayankaggarwal.viteventsapp.models.CouresePage;
import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.models.FacultiesList;
import com.example.mayankaggarwal.viteventsapp.models.PostParams;


import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        Realm.init(application);
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm instance
    public void refresh() {

//        realm.refresh();
    }


    public void clearAll(){
        realm.beginTransaction();
        realm.delete(AttendanceList.class);
        realm.delete(CouresePage.class);
        realm.delete(DetailAttendance.class);
        realm.delete(PostParams.class);
        realm.commitTransaction();
    }

////    find all objects
    public RealmResults<AttendanceList> getAtendance() {
        return realm.where(AttendanceList.class).findAll();
    }

    public RealmResults<FacultiesList> getFaculty() {
        return realm.where(FacultiesList.class).findAll();
    }

    public RealmResults<CouresePage> getCoursePage() {
        return realm.where(CouresePage.class).findAll();
    }

    public RealmResults<DetailAttendance> getDetailAttendance() {
        return realm.where(DetailAttendance.class).findAll();
    }

    public RealmResults<EventList> getEvents() {
        return realm.where(EventList.class).findAll();
    }


   //// query a single item with the given id
//    public AttendanceList getAttendance(String id) {
//        return realm.where(AttendanceList.class).equalTo("id", id).findFirst();
//    }


    public boolean hasAttendance() {
        return !realm.where(AttendanceList.class).findAll().isEmpty();
    }

    public boolean hasFaculty() {
        return !realm.where(FacultiesList.class).findAll().isEmpty();
    }




}

