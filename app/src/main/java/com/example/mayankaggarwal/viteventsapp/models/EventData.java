package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankaggarwal on 18/03/17.
 */

public class EventData {

    @SerializedName("data")
    @Expose
    public List<EventList> data = null;

    public List<EventList> getData() {
        return data;
    }

    public void setData(List<EventList> data) {
        this.data = data;
    }

}


