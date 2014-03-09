package com.g08.sonder;

import com.g08.sonder.adapter.TabAdapter;
import com.parse.Parse;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FeedActivity extends FragmentActivity implements
ActionBar.TabListener{
	private ViewPager viewPager;
    private TabAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Anonymous", "People", "Profile" };
    static GPSTracker gps;
    
    
    public static GPSTracker getGPS()
    {
    	return gps;
    }

    /*
     * Runs when the FeedActivity is started
     * Signs in user and generates GPS information with GPSTracker
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Parse.initialize(this, "TWvYrlz5jTRkPrrjr4JohuDbR4zEXBc7pPWga4de", "P68SafB5lfpav8NXScXnttVRGorpcbINDvoz1aY0");

        ParseUser curUser = ParseUser.getCurrentUser();
        gps = new GPSTracker(getApplicationContext());

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
    /*
     * Operations to perform when the user reselects a tab
     */
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
	/*
     * Operations to perform when the user selects a tab initially
     */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		viewPager.setCurrentItem(tab.getPosition());

	}
	/*
	 * Operations to perfom when the tag is deselected by the user
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}