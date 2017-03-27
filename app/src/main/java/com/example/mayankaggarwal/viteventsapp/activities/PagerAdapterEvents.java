package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.mayankaggarwal.viteventsapp.utils.Globals;

/**
 * Created by mayankaggarwal on 26/03/17.
 */

public class PagerAdapterEvents  extends FragmentPagerAdapter {


    private int PAGE_COUNT = 0;

    public PagerAdapterEvents(FragmentManager fm, Activity activity) {
        super(fm);
        PAGE_COUNT=Globals.getEventList(activity).size();
//        Log.d("tagg","size:"+ Globals.eventList.size());
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SwipeEventFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count", position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
