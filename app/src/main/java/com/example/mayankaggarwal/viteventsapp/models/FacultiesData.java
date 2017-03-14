package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mayankaggarwal on 14/03/17.
 */

public class FacultiesData {

    @SerializedName("data")
    @Expose
    public List<FacultiesList> data = null;

    public List<FacultiesList> getData() {
        return data;
    }

    public void setData(List<FacultiesList> data) {
        this.data = data;
    }

}
