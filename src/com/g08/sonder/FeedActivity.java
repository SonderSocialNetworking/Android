package com.g08.sonder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.g08.sonder.adapter.TabAdapter;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FeedActivity extends FragmentActivity implements
ActionBar.TabListener{
	private ViewPager viewPager;
    private TabAdapter mAdapter;
    private ActionBar actionBar;
    public static double maxRadius = 1;//1 mile
    public static int maxNearby = 20;//Allow fetching of up to 20 users
    public static List<ParseUser> nearby = new ArrayList<ParseUser>();


    // Tab titles
    private String[] tabs = { "Anonymous", "People", "Profile" };


    //Array pass by reference hack ;)
    boolean[] tracking = {false};//Lets us know that the gps tracker is updating
    boolean[] updating = {false};//Lets us know that the nearby list is updating
    GPSTracker gps = new GPSTracker(this, updating);
    //List<ParseUser> nearby = new ArrayList<ParseUser>();





    public GPSTracker getGPS()
    {
    	return gps;
    }
    /*
     *update and save the user's gps info (optional) and update the nearby vector (manditory)
     */
    public boolean updateNearby()
    {

    	Log.v("1","Called update nearby");
    	updating[0] = true; //pass by reference :) Should let us know that the function is updating still, even outside of task
    	AsyncTask<Void, Void, Boolean[]> update = new updateTask().execute();
    	int tries = 0;

    	//TODO This is part of the crash reasons
    	while(update.getStatus() != AsyncTask.Status.FINISHED && tries <= 10)
    	{
    		tries++;
    		Log.v("1","Waiting .5s for update nearby; update task running");
    		Log.v("1","Status of update task is:" + update.getStatus());
    		try {

    			Thread.sleep(500);
    			Log.v("1","Waking up");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	Log.v("1","Update task done running, tries=" + tries);
    	Log.v("1","Nearby contains:" + nearby.size()  + " users");

    	return true;//Supposed to indicate that everything is done
    }
    /*
     * Returns the list of users nearby to user
     */
    public List<ParseUser> getNearby()
    {
    	return nearby;
    }
    public void setNearby(List<ParseUser> newNearby)//eventually fix public for larger scale
    {
    	nearby = newNearby;//PSOE
    }



    /*
     * <parameters, progress, result>
     */
  	private class updateTask extends AsyncTask<Void,Void,Boolean[]>{



		/* sets the result of doin background to the result variable
  		 * (non-Javadoc)
  		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
  		 */
  		@Override
  	    protected void onPostExecute(Boolean[] run/*Void... result*/) {
  			//run[0] = false;
  			//indicates that we are done running, pass by reference
  			updating[0] = false;
  			super.onPostExecute(run);
  			Log.v("1","Update Task done executing");

  	    }

		@Override
		protected Boolean[] doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//We save user data in background for movement
			//Will use save in foreground for this as user is waiting for update.
			Log.v("1","Initializing an update");

	    	ParseQuery<ParseUser> query =  ParseUser.getQuery();
	    	getNearby(query);//Sets up 'nearby' array
	    	//The geotracker does NOT update nearby; to avoid problems accessing it if data has been inadvertantly changed.
			Boolean[] output = {false};//Possible memory leak, but variable not required; just a check.
			return output;//
		}
		/*
	     * Generates a list of up nearby users
	     * ParseQuery<ParseUser> query - The query object that will be used to search the ParseUsers
	     * int miles - The max number of miles range to search for users
	     * int users - The max number of users to try to find
	     */
	    protected void getNearby(ParseQuery<ParseUser> query)//run the first time with the max distance looking to explore over
	    {
	    	//query.include("location");
	        //query.whereNear("location", loc);
	    	ParseGeoPoint location = new ParseGeoPoint(getGPS().getLatitude(),getGPS().getLongitude());
	        query.whereWithinMiles("location", location, maxRadius);
	        query.setLimit(maxNearby);//The number of entries get
	        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
	        //generateGPSUsers(1);

	        try {
				nearby = query.find();//Sets the nearby vector to what we find
				//Found in foreground!
				Log.v("1","Updated the nearby vector");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.v("1","getNearby throws an exception");
				e.printStackTrace();
				Log.v("1",e.getMessage());
			}


	    }
	    public void printNearby()
	    {
	    	Log.v("2","Printing the" + " " + nearby.size() + " nearby users to " + ParseUser.getCurrentUser().get("location").toString());
	    	for(int i = 0; i < nearby.size();i++)
	    	{
	    		Log.v("2","Name:" + (String)nearby.get(i).getUsername() + " Post:" + (String)nearby.get(i).get("AnonPost"));
	    		//Log.v("2",((String) ((ParseGeoPoint)in.get(i).get("location")).milesTo((ParseGeoPoint)ParseUser.getCurrentUser().get("location")));
	    	}

	    }

  	}
  	/*
     * Generated an number(amount) of ParseUsers
     * ParseUsers each have a random int username, password = pword, and a random location
     */
    public void generateGPSUsers(int amount)
    {
    	for(int i = 0; i < amount;i++)
    	{
    		ParseUser user = new ParseUser();
    		Random rand = new Random();



    		//current GPS location: Decimal Minutes (GPS) : N34	25.24985  W-119 41.89139

    		double x = (.01 * rand.nextDouble()) + 34.409;
    		double y = (.01 * rand.nextDouble()) - 119.8423;
    		//double x = (.01 * rand.nextDouble());
    		//double y = (.01 * rand.nextDouble());

    		ParseGeoPoint add = new ParseGeoPoint(x,y);
    		int z = rand.nextInt(9999999);
    		user.setUsername("" + z);
    		user.setPassword("pword");


    		user.put("location", add);
    		user.put("locationX", x);
    		user.put("locationY", y);
    		user.put("AnonPost", "My username is:" + z);
    		user.signUpInBackground(new SignUpCallback() {
      		  public void done(ParseException e) {
      		    if (e == null) {
      		      // Hooray! Let them use the app now.
      		    	Log.v("2","GPSUserCreated");
      		    } else {
      		      // Sign up didn't succeed. Look at the ParseException
      		      // to figure out what went wrong
      		    	Log.v("2","GPSUserNotCreated");
      		    	Log.v("2", e.getMessage());
      		    }
      		  }
      		});

    	}
    	Log.v("1","" + amount + " users added");
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
        //Log.v("1","GENERATING 10 0,0 ish GPS USERS");
        //generateGPSUsers(10);


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
	 * Operations to perform when the tag is deselected by the user
	 */
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}


}
