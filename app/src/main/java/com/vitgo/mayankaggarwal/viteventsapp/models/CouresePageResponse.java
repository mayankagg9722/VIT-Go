package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankaggarwal on 17/02/17.
 */

public class CouresePageResponse {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("data")
    @Expose
    public List<CouresePage> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CouresePage> getData() {
        return data;
    }

    public void setData(List<CouresePage> data) {
        this.data = data;
    }

}
