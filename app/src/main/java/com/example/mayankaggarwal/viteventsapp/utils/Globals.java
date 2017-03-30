package com.example.mayankaggarwal.viteventsapp.utils;


import android.app.Activity;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.activities.DigitalMarks;
import com.example.mayankaggarwal.viteventsapp.models.CouresePage;
import com.example.mayankaggarwal.viteventsapp.models.DetailAttendance;
import com.example.mayankaggarwal.viteventsapp.models.DigitalMarksData;
import com.example.mayankaggarwal.viteventsapp.models.EventList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayankaggarwal on 25/02/17.
 */

public class Globals {

    public static List<List<CouresePage>> couresePages = new ArrayList<>();

    public static List<List<CouresePage>> singleCopycouresePages = new ArrayList<>();

    public static List<List<DetailAttendance>> detailAttendances = new ArrayList<>();

    public static List<List<DetailAttendance>> singleCopydetailAttendances = new ArrayList<>();

    public static int attendanceListSize=0;
    public static int courseCodeDaySize=0;


    public static List<String> digitalAssignmentMarks=new ArrayList<>();
    public  static List<String> digitalCourseCode=new ArrayList<>();
    public  static List<String> digitalCourseType=new ArrayList<>();


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

    public static int fetchAssignment=0;

    public static int fetchMarks=0;

    public static int fetchMessage=0;

    public static String dayName=null;

    public static int firstCallFaculty=0;

    public static int gridorliner=0;

    public static List<EventList> eventList=new ArrayList<>();


    public  static  List<EventList> getEventList (Activity activity){
        List<EventList> eventLists= RealmController.with(activity).getEvents();

        List<EventList> eventList=new ArrayList<>();

        for(EventList e:eventLists){
            eventList.add(e);
        }

        return eventList;
    }




}
