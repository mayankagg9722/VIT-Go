package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 14/02/17.
 */

public class TimetableRequest {
    @SerializedName("regno")
    @Expose
    public String regno;
    @SerializedName("password")
    @Expose
    public String password;
}
