package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankaggarwal on 24/03/17.
 */

public class ExamScheduleResponse {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("CAT1")
    @Expose
    public List<CAT1> cAT1 = null;
    @SerializedName("CAT2")
    @Expose
    public List<CAT2> cAT2 = null;
    @SerializedName("fat")
    @Expose
    public List<Fat> fat = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CAT1> getCAT1() {
        return cAT1;
    }

    public void setCAT1(List<CAT1> cAT1) {
        this.cAT1 = cAT1;
    }

    public List<CAT2> getCAT2() {
        return cAT2;
    }

    public void setCAT2(List<CAT2> cAT2) {
        this.cAT2 = cAT2;
    }

    public List<Fat> getFat() {
        return fat;
    }

    public void setFat(List<Fat> fat) {
        this.fat = fat;
    }

}
