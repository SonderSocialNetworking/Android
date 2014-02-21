package com.g08.sonder;

import com.parse.ParseException;
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
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {

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
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

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

    @Override
    public void onLocationChanged(Location location) {
    	ParseUser curUser = ParseUser.getCurrentUser();
    	 if(canGetLocation()){
         	double xCoor = getLatitude();
         	double yCoor = getLongitude();
         	//ParseGeoPoint loc = new ParseGeoPoint(5,10);
         	ParseGeoPoint loc = new ParseGeoPoint(xCoor,yCoor);
         	Log.v("1","Got Location:" + loc);
         	curUser.put("location", loc);
         	curUser.saveInBackground();
         	try {
				curUser.refresh();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         	
         }
         else
         {
         	Log.v("1","GPS has not been enabled, Location not changed");
         }

    	Log.v("1","Location Changed");
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}

/*
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;
import android.location.Criteria;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import java.util.Locale;





*/


/*

public class GPSTracker extends Activity {

	//The minimum distance to change Updates in meters
		private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 20;

	//The minimum time between updates
		private static final long MIN_TIME_BW_UPDATES = (100 * 60 * 5); // 5 minutes
	//current location
		private Location loc;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_feed);

		LocationManager locationmanager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		//Criteria determines which provider is the best to use in the user's circumstances
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);
		String provider = locationmanager.getBestProvider(criteria, true);

		Location recent_location = locationmanager.getLastKnownLocation(provider);
//		LocationListener locationlistener = new MyLocationListener();

		updateWithNewLocation(recent_location);

		locationmanager.requestLocationUpdates(provider,MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListener);
		}

	void updateWithNewLocation(Location location) {
			TextView myLocationText = null;
			//myLocationText = (TextView)findViewById(R.id.myLocationText);

			String addressString;
			if (location != null) {
			double latitude = location.getLatitude();     //returns latitude
			double longitude = location.getLongitude();   //returns longitude


			//Geocoder will translate longitude and latitude into address form
			Geocoder gc = new Geocoder(this, Locale.getDefault());

			try {
				List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
				Address address = addresses.get(0);

				for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
					sb.append(address.getAddressLine(i)).append("\n");

		//appends city name, zipcode, country, disabled for now
//		          sb.append(address.getLocality()).append("\n");
//	          	  sb.append(address.getPostalCode()).append("\n");
	//	          sb.append(address.getCountryName());
				}
				addressString = sb.toString();
				myLocationText.setText("Your Current Position is:\n" + addressString);
			} catch (IOException exception) {}
			}
			//test output

		}

		private final LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
			updateWithNewLocation(location); }

			@Override
			public void onProviderDisabled(String provider) {}

			@Override
			public void onProviderEnabled(String provider) {}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
		};
}*/