package com.vitgo.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayankaggarwal on 17/02/17.
 */

public class DARequest {

    @SerializedName("semcode")
    @Expose
    public String semcode;
    @SerializedName("classnbr")
    @Expose
    public String classnbr;
    @SerializedName("from_date")
    @Expose
    public String fromDate;
    @SerializedName("to_date")
    @Expose
    public String toDate;
    @SerializedName("crscd")
    @Expose
    public String crscd;
    @SerializedName("crstp")
    @Expose
    public String crstp;
    @SerializedName("regno")
    @Expose
    public String regno;
    @SerializedName("password")
    @Expose
    public String password;

}
