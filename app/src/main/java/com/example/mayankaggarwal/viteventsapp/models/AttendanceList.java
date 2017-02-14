package com.example.mayankaggarwal.viteventsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class AttendanceList extends RealmObject {

    @SerializedName("courseCode")
    @Expose
    private String courseCode;
    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("courseType")
    @Expose
    private String courseType;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("attended")
    @Expose
    private String attended;
    @SerializedName("totalClasses")
    @Expose
    private String totalClasses;

    @SerializedName("postParams")
    @Expose
    private PostParams postParams;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(String totalClasses) {
        this.totalClasses = totalClasses;
    }

    public PostParams getPostParams() {
        return postParams;
    }

    public void setPostParams(PostParams postParams) {
        this.postParams = postParams;
    }



}