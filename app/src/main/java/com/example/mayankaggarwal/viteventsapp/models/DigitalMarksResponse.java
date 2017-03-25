package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class DigitalMarksResponse {
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("data")
    @Expose
    public List<DigitalMarksData> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DigitalMarksData> getData() {
        return data;
    }

    public void setData(List<DigitalMarksData> data) {
        this.data = data;
    }

}
