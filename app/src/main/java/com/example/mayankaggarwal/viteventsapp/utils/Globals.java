package com.example.mayankaggarwal.viteventsapp.utils;


import com.example.mayankaggarwal.viteventsapp.models.CouresePage;
import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;

import java.util.ArrayList;
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

}
