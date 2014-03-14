package com.g08.sonder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.location.LocationManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;



public class GPSTracker extends Service implements LocationListener {

	/*
	 * The Context/Activity in which the GPStracker is running
	 */
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    boolean[] done = {false};

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 15; // 15 seconds

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context, boolean[] run) {
        this.mContext = context;
        Log.v("1", "New GPS Tracker Created");
        //getLocation();
        //Ignore below warning; no need to cast the instance; we just want it to execute




        //new getLocationTask().execute();//gets the location in the background

        //need to set location in getLocation>?


    }


    /*
     * Updates the GPSTracker with the User's location
     */
    public Location getLocation() {
        try {
        	done[0] = false;
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            	done[0] = true;
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            done[0] = true;
                            Log.v("1","Done with getLocation method except saving");




                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                done[0] = true;
                                Log.v("1","Done with getLocation method");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ParseUser curUser = ParseUser.getCurrentUser();
    	/*
    	 * Going to save the users data here, needs to be done before update
    	 * So may cause lag.
    	 * Gps auto updates based on location, so may not need to save, or make optional
    	 *
    	 */
		ParseGeoPoint loc = new ParseGeoPoint(getLatitude(), getLongitude());
		curUser.put("location", loc);
    	curUser.put("locationY", getLatitude());
    	curUser.put("locationX", getLongitude());
    	curUser.saveInBackground(new SaveCallback() {
    		public void done(ParseException e) {
    		     if (e == null) {
    		       Log.v("1", "Gps location changed; gps successfully saved in background after moving");
    		       Log.v("1","New Location = " + ":" + getLatitude() + ":" + getLongitude());
    		       done[0] = true;
    		     } else {
    		    	 Log.v("1", "Gps location changed; gps had an error saving in background");
    		    	 Log.v("1",e.getMessage());
    		     }
    		   }});
        return location;//Can return location even if not done saving
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }



    public class getLocationTask extends AsyncTask<Void,Void,Boolean[]>{
    	protected void doInBackground(){
    		Log.v("1","RAN THE OTHER METHOD");
    	}

		@Override
		protected Boolean[] doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Location tempLoc = location;
			getLocation();
			int tries = 0;
			while(!done[0] || tries > 10)
			{
				tries++;
				Log.v("1","Waited " + tries + "times for .5s for location to be returned in getLocationTask");
				try {
					this.get(500, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			if(tries > 20)
			{
				Log.v("1","Took more than 10 tries, done trying to update in background");
			}
			else
			{
				Log.v("1","Took less than 10 tries, done trying to update in background");
			}
			Boolean[] done = {true};
			return done;
		}
		@Override
		protected void onPostExecute(Boolean[] result) {
			// TODO Auto-generated method stub

			super.onPostExecute(result);
		}
    }



    /*
     * This function is automatically exectuted when the GPSTracker detectst that it has changed location
     */
    @Override
    public void onLocationChanged(Location location) {
    	ParseUser curUser = ParseUser.getCurrentUser();
    	 if(canGetLocation()){

    		 //PSOE
    		 //getLocation();

    		Log.v("1","User changing location");
    		//done[0] = false;
    		Log.v("1", "Xbefore:" + curUser.get("locationX") + ":Ybefore:" + curUser.get("locationY"));
         	double xCoor = getLatitude();
         	double yCoor = getLongitude();
         	ParseGeoPoint loc = new ParseGeoPoint(xCoor,yCoor);
         	//ParseGeoPoint loc = new ParseGeoPoint(xCoor,yCoor);
         	//Log.v("1","Got Location:" + loc);
         	//curUser.put("location", loc);


         	//PUT THE LOCATION!!!!
         	//Save the seperate coordinates in ad
         	curUser.put("location", loc);//Stores the ParseGeoPoint for functions
         	curUser.put("locationX", xCoor);
         	curUser.put("locationY", yCoor);
         	Log.v("1","Put the values:" + xCoor + "&" + yCoor);

			curUser.saveInBackground(new SaveCallback() {
				   public void done(com.parse.ParseException e) {
				     if (e == null) {
				    	 Log.v("1","Location saved after location change");



				     } else {
				    	 Log.v("1","Error while saving in background from location change");
				    	 e.printStackTrace();

				     }
				   }


				 });

         }
         else
         {
         	Log.v("1","GPS has not been enabled, Location not changed");
         }

    	Log.v("1","Location Changed");
    	Log.v("1", "Xafter:" + curUser.get("locationX") + ":Yafter:" + curUser.get("locationY"));
    }

    /*
     * Runs when the GPSTracker's Provider has been disabled
     * (non-Javadoc)
     * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
     */
    @Override
    public void onProviderDisabled(String provider) {
    }
    /*
     * Runs when the GPSTracker's Provider has been enabled
     * (non-Javadoc)
     * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
     */
    @Override
    public void onProviderEnabled(String provider) {
    }

    /*
     * Runs when the status of the GPS tracker has been chaned (provider etc)
     * (non-Javadoc)
     * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}