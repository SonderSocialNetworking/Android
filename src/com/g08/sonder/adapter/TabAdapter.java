
package com.g08.sonder.adapter;
 

import com.g08.sonder.AnonymousFragment;
import com.g08.sonder.ProfileFragment;
import com.g08.sonder.ProximityFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabAdapter extends FragmentPagerAdapter {
 
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new AnonymousFragment();
        case 1:
            // Games fragment activity
            return new ProximityFragment();
        case 2:
            // Movies fragment activity
            return new ProfileFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}