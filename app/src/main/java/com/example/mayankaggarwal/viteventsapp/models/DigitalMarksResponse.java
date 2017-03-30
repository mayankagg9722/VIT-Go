package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
    public List<JsonObject> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<JsonObject> getData() {
        return data;
    }

    public void setData(List<JsonObject> data) {
        this.data = data;
    }

}
