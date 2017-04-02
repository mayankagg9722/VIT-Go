package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by mayankaggarwal on 25/03/17.
 */

public class LateNightData extends RealmObject {
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("pvPermitID")
    @Expose
    private String pvPermitID;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPvPermitID() {
        return pvPermitID;
    }

    public void setPvPermitID(String pvPermitID) {
        this.pvPermitID = pvPermitID;
    }

}
