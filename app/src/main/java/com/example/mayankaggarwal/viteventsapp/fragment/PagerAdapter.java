package com.example.mayankaggarwal.viteventsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mayankaggarwal.viteventsapp.fragment.SwipeFragment;

/**
 * Created by mayankaggarwal on 28/02/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    private final int PAGE_COUNT = 7;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SwipeFragment();
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
