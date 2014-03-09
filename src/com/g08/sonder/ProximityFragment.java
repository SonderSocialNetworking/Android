package com.g08.sonder;
 

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class ProximityFragment extends Fragment {
 
  
        
        public List<ParseUser> nearby;//list of the nearby user nodes, the preferred size can be set below
    	//The max number of users we are willing to look for; will moderate the size of the feed
    	public final int maxNearby = 50;
    	//The max radius we are willing to check for maxNearby users
    	public final int maxRadius = 250;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {


        	GPSTracker gps = new GPSTracker(getActivity());
            View rootView = inflater.inflate(R.layout.proximity_fragment, container, false);
            ParseUser curUser = ParseUser.getCurrentUser();
            ParseGeoPoint loc = new ParseGeoPoint(5,10);
            //ParseGeoPoint loc = (ParseGeoPoint) curUser.get("location");
            Log.v("1", "Long:" + loc.getLongitude() + ":Lat:" + loc.getLatitude() + ":\n");
            ParseQuery<ParseUser> query =  ParseUser.getQuery();

            int radius = 1;
            do
            {
            	getNearby(query, radius,maxNearby);
            	radius *= 2;
            }while(radius < maxRadius && nearby.size() < maxNearby);

            printNearby();
            //Here the up to maxNearby User objects are stored in the list 'nearby'
            //Now just display their names and messages

            LinearLayout proxLayout = (LinearLayout) rootView.findViewById(R.id.prox);
            
            for(ParseUser person: nearby)
            {
            	
            	
            	TextView view = new TextView(getActivity());
            	String resulting = person.get("username").toString();
            	
            	view.setText(resulting);
            	view.setMinimumHeight(200);
            	view.setTextSize(20);
            	view.setPadding(20, 0, 0, 20);
            	view.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            	view.setBackgroundResource(R.drawable.border_ui);
            	
            	proxLayout.addView(view);
            }





            return rootView;
        }
        //users is the desired amount of users
        public void getNearby(ParseQuery<ParseUser> query, int miles, int users)//run the first time with the max distance looking to explore over
        {
        	//query.include("location");
            //query.whereNear("location", loc);
            query.whereWithinMiles("location", (ParseGeoPoint)ParseUser.getCurrentUser().get("location"), miles);
            query.setLimit(users);//The number of entries get
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            //generateGPSUsers(1);

            try {
    			nearby = query.find();
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			Log.v("1",e.getMessage());
    		}
            /*
            	query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            Log.v("1","Found " + objects.size() + " matches");

                            saveNearby(objects);
                            printNearby();

                        } else {
                        	Log.v("1","Error finding matches");
                        	Log.v("1",e.getMessage());
                        }
                    }
                });
                */



        }
        public void saveNearby(List<ParseUser> objects)
        {
        	nearby = objects;
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
