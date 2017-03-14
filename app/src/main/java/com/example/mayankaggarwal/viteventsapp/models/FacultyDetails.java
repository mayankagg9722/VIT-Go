package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by mayankaggarwal on 14/03/17.
 */

public class FacultyDetails {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("venue")
    @Expose
    public String venue;
    @SerializedName("intercom")
    @Expose
    public String intercom;
    @SerializedName("designation")
    @Expose
    public String designation;
    @SerializedName("openHours")
    @Expose
    public List<String> openHours = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getIntercom() {
        return intercom;
    }

    public void setIntercom(String intercom) {
        this.intercom = intercom;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<String> getOpenHours() {
        return openHours;
    }

    public void setOpenHours(List<String> openHours) {
        this.openHours = openHours;
    }


}
