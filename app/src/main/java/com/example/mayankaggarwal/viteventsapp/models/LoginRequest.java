package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class LoginRequest {

    @SerializedName("regno")
    @Expose
    public String regno;
    @SerializedName("password")
    @Expose
    public String password;
}
