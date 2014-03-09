
package com.g08.sonder;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

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

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 15; // 15 seconds

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        Log.v("1", "New GPS Tracker Created");
        //getLocation();
        //Ignore below warning; no need to cast the instance; we just want it to execute
        new getLocationTask().execute();//gets the location in the background

        //need to set location in getLocation>?


    }


    /*
     * Updates the GPSTracker with the User's location
     */
    public Location getLocation() {
        try {
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





                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
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
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
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



    public class getLocationTask extends AsyncTask{
    	protected void doInBackground(){
    		Log.v("1","RAN THE OTHER METHOD");
    	}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			
			Log.v("1","Done getting location in background");
			getLocation();
			Log.v("1","Done getting location in background");
			return null;
		}
    }



    /*
     * This function is automatically exectuted when the GPSTracker detectst that it has changed location
     */
    @Override
    public void onLocationChanged(Location location) {
    	ParseUser curUser = ParseUser.getCurrentUser();
    	 if(canGetLocation()){
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

