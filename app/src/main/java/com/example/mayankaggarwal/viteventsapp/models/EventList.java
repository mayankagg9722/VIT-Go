package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;

/**
 * Created by mayankaggarwal on 18/03/17.
 */

public class EventList extends RealmObject {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("going")
    @Expose
    private String going;
    @SerializedName("fees")
    @Expose
    private String fees;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("chapterName")
    @Expose
    private String chapterName;
    @SerializedName("chapterId")
    @Expose
    private String chapterId;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("fieldsAndroid")
    @Expose
    private String fieldsAndroid = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getFields() {
        return fieldsAndroid;
    }

    public void setFields(String fieldsAndroid) {
        this.fieldsAndroid = fieldsAndroid;
    }

}