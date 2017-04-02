package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by mayankaggarwal on 17/02/17.
 */

public class CouresePage extends RealmObject {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("materialUploaded")
    @Expose
    private String materialUploaded;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaterialUploaded() {
        return materialUploaded;
    }

    public void setMaterialUploaded(String materialUploaded) {
        this.materialUploaded = materialUploaded;
    }

}