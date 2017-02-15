package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by mayankaggarwal on 14/02/17.
 */

public class TimetableResponse  {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("timetable")
    @Expose
    private List<List<String>> timetable = null;
    @SerializedName("faculties")
    @Expose
    private List<Faculty> faculties = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<List<String>> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<List<String>> timetable) {
        this.timetable = timetable;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

}

