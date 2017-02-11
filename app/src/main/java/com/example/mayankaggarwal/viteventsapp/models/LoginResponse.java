package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class LoginResponse {
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("success")
    @Expose
    public Boolean success;
   }
