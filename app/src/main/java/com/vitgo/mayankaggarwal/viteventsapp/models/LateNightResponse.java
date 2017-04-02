package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankaggarwal on 25/03/17.
 */

public class LateNightResponse {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("data")
    @Expose
    public List<LateNightData> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<LateNightData> getData() {
        return data;
    }

    public void setData(List<LateNightData> data) {
        this.data = data;
    }

}
