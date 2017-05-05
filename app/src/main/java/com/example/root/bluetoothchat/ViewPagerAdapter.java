package com.example.root.bluetoothchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DAN on 01/05/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    int numTabs;

    public ViewPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);

        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PairDeviceFragment pair = new PairDeviceFragment();
                return pair;
            case 1:
                NewDeviceFragment newDV = new NewDeviceFragment();
                return newDV;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
