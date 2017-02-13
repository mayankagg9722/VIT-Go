package com.example.mayankaggarwal.viteventsapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class PostParams extends RealmObject {

    @SerializedName("semcode")
    @Expose
    private String semcode;
    @SerializedName("classnbr")
    @Expose
    private String classnbr;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("crscd")
    @Expose
    private String crscd;
    @SerializedName("crstp")
    @Expose
    private String crstp;

    public String getSemcode() {
        return semcode;
    }

    public void setSemcode(String semcode) {
        this.semcode = semcode;
    }

    public String getClassnbr() {
        return classnbr;
    }

    public void setClassnbr(String classnbr) {
        this.classnbr = classnbr;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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
