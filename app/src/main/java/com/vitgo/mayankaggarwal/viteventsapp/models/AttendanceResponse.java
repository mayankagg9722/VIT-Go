package com.vitgo.mayankaggarwal.viteventsapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class AttendanceResponse {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("data")
    @Expose
    public List<AttendanceList> data =new ArrayList<>();

    @SerializedName("showAds")
    @Expose
    public Boolean showAds;


}

