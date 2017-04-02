package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 23/03/17.
 */

public class CancelRequest {

    @SerializedName("leave_id")
    @Expose
    public String leave_id;
    @SerializedName("regno")
    @Expose
    public String regno;
    @SerializedName("password")
    @Expose
    public String password;
}
