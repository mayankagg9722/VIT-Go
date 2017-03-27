package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

import static com.example.mayankaggarwal.viteventsapp.R.id.status;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class DigitalMarksData extends RealmObject{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("score")
    @Expose
    private String score;

    @SerializedName("status")
    @Expose
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
