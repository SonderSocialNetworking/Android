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

    	private LinearLayout proxLayout;
    	private FeedActivity act;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	Log.v("1", "Proximity Fragment started");

            View rootView = inflater.inflate(R.layout.proximity_fragment, container, false);
            ParseUser curUser = ParseUser.getCurrentUser();

            //Can be removed to add speed, just for debug
            //ParseGeoPoint loc = (ParseGeoPoint) curUser.get("location");
            //Log.v("1", "Long:" + loc.getLongitude() + ":Lat:" + loc.getLatitude() + ":\n");

            act = (FeedActivity)getActivity();//Basically gets the feedactivty running the fragment
            proxLayout = (LinearLayout) rootView.findViewById(R.id.prox);
            return rootView;
        }

        @Override
        public void onResume() {
        	if(isVisible())
        	{
        		Log.v("1","AF resumed & visible so updating screen");
            	updateScreen();
        	}
        	Log.v("1", "AF 'resumed' but not visible, did not update screen");
        	super.onResume();
        };
        public void updateScreen()
        {
        	Log.v("1","Updating PF, " + act.getNearby().size() + " users nearby");
            for(ParseUser person: act.getNearby())
            {
            	proxLayout.removeViewAt(0);//Psoe, idea is that views are as in a list, so can keep removing first element
            	//and then append onee, causing a cycling effect

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
        }



    }