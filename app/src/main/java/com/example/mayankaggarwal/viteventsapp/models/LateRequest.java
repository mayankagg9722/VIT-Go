package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 25/03/17.
 */

public class LateRequest {
    @SerializedName("cvFaculty")
    @Expose
    public String cvFaculty;
    @SerializedName("frmdate")
    @Expose
    public String frmdate;
    @SerializedName("frmtm")
    @Expose
    public String frmtm;
    @SerializedName("todate")
    @Expose
    public String todate;
    @SerializedName("totm")
    @Expose
    public String totm;
    @SerializedName("txtVenue")
    @Expose
    public String txtVenue;
    @SerializedName("txtRsn")
    @Expose
    public String txtRsn;
    @SerializedName("regno")
    @Expose
    public String regno;
    @SerializedName("password")
    @Expose
    public String password;

    public String getCvFaculty() {
        return cvFaculty;
    }

    public void setCvFaculty(String cvFaculty) {
        this.cvFaculty = cvFaculty;
    }

    public String getFrmdate() {
        return frmdate;
    }

    public void setFrmdate(String frmdate) {
        this.frmdate = frmdate;
    }

    public String getFrmtm() {
        return frmtm;
    }

    public void setFrmtm(String frmtm) {
        this.frmtm = frmtm;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getTotm() {
        return totm;
    }

    public void setTotm(String totm) {
        this.totm = totm;
    }

    public String getTxtVenue() {
        return txtVenue;
    }

    public void setTxtVenue(String txtVenue) {
        this.txtVenue = txtVenue;
    }

    public String getTxtRsn() {
        return txtRsn;
    }

    public void setTxtRsn(String txtRsn) {
        this.txtRsn = txtRsn;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
