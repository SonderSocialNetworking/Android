package com.g08.sonder;

import com.g08.sonder.adapter.TabAdapter;
import com.parse.Parse;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseGeoPoint;

public class FeedActivity extends FragmentActivity implements
ActionBar.TabListener{
	private ViewPager viewPager;
    private TabAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Anonymous", "People", "Profile" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Parse.initialize(this, "TWvYrlz5jTRkPrrjr4JohuDbR4zEXBc7pPWga4de", "P68SafB5lfpav8NXScXnttVRGorpcbINDvoz1aY0");

        //this we be run as soon as the user is logged in


        ParseUser curUser = ParseUser.getCurrentUser();
        GPSTracker gps = new GPSTracker(getApplicationContext());


        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}