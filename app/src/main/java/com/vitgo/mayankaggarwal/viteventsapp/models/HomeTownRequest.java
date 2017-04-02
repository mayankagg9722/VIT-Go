package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 23/03/17.
 */

public class HomeTownRequest {
    @SerializedName("apply")
    @Expose
    public String apply;
    @SerializedName("lvtype")
    @Expose
    public String lvtype;
    @SerializedName("exitdate")
    @Expose
    public String exitdate;
    @SerializedName("sttime_hh")
    @Expose
    public String sttimeHh;
    @SerializedName("sttime_mm")
    @Expose
    public String sttimeMm;
    @SerializedName("frm_timetype")
    @Expose
    public String frmTimetype;
    @SerializedName("reentry_date")
    @Expose
    public String reentryDate;
    @SerializedName("endtime_hh")
    @Expose
    public String endtimeHh;
    @SerializedName("endtime_mm")
    @Expose
    public String endtimeMm;
    @SerializedName("to_timetype")
    @Expose
    public String toTimetype;
    @SerializedName("place")
    @Expose
    public String place;
    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("regno")
    @Expose
    public String regno;
    @SerializedName("password")
    @Expose
    public String password;

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getLvtype() {
        return lvtype;
    }

    public void setLvtype(String lvtype) {
        this.lvtype = lvtype;
    }

    public String getExitdate() {
        return exitdate;
    }

    public void setExitdate(String exitdate) {
        this.exitdate = exitdate;
    }

    public String getSttimeHh() {
        return sttimeHh;
    }

    public void setSttimeHh(String sttimeHh) {
        this.sttimeHh = sttimeHh;
    }

    public String getSttimeMm() {
        return sttimeMm;
    }

    public void setSttimeMm(String sttimeMm) {
        this.sttimeMm = sttimeMm;
    }

    public String getFrmTimetype() {
        return frmTimetype;
    }

    public void setFrmTimetype(String frmTimetype) {
        this.frmTimetype = frmTimetype;
    }

    public String getReentryDate() {
        return reentryDate;
    }

    public void setReentryDate(String reentryDate) {
        this.reentryDate = reentryDate;
    }

    public String getEndtimeHh() {
        return endtimeHh;
    }

    public void setEndtimeHh(String endtimeHh) {
        this.endtimeHh = endtimeHh;
    }

    public String getEndtimeMm() {
        return endtimeMm;
    }

    public void setEndtimeMm(String endtimeMm) {
        this.endtimeMm = endtimeMm;
    }

    public String getToTimetype() {
        return toTimetype;
    }

    public void setToTimetype(String toTimetype) {
        this.toTimetype = toTimetype;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

