package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class DigitalMarksRequest {
    @SerializedName("regno")
    @Expose
    public String regno;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("sem")
    @Expose
    public String sem;
    @SerializedName("classnbr")
    @Expose
    public String classnbr;
    @SerializedName("crscd")
    @Expose
    public String crscd;
    @SerializedName("crstp")
    @Expose
    public String crstp;

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

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getClassnbr() {
        return classnbr;
    }

    public void setClassnbr(String classnbr) {
        this.classnbr = classnbr;
    }

    public String getCrscd() {
        return crscd;
    }

    public void setCrscd(String crscd) {
        this.crscd = crscd;
    }

    public String getCrstp() {
        return crstp;
    }

    public void setCrstp(String crstp) {
        this.crstp = crstp;
    }

}
