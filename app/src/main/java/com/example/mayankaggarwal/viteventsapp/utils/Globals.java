package com.example.mayankaggarwal.viteventsapp.utils;


import android.app.DatePickerDialog;

import com.example.mayankaggarwal.viteventsapp.models.CouresePage;
import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;
import com.example.mayankaggarwal.viteventsapp.models.EventList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mayankaggarwal on 25/02/17.
 */

public class Globals {

    public static List<List<CouresePage>> couresePages = new ArrayList<>();

    public static List<List<DetailAttendance>> detailAttendances = new ArrayList<>();

    public static List<String> courseCode=new ArrayList<>();

    public static List<String>  courseType=new ArrayList<>();

    public static int doneFetching=0;

    public static String faculty_email;

    public static String faculty_venue;

    public static String faculty_intercom;

    public static String faculty_designation;

    public static List<String> faculty_openhours=new ArrayList<>();

    public static EventList register_event=null;

    public static int fetchEvent=0;

    public static int fetchMessage=0;

    public static String dayName=null;

}
