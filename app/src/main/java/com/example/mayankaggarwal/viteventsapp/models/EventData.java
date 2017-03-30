package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankaggarwal on 18/03/17.
 */

public class EventData {

    @SerializedName("data")
    @Expose
    public List<JsonObject> data = null;

    public List<JsonObject> getData() {
        return data;
    }

    public void setData(List<JsonObject> data) {
        this.data = data;
    }

    @SerializedName("count")
    @Expose
    public int count;

}


