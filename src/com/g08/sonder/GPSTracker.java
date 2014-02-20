package com.g08.sonder;

import android.location.LocationManager;
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

public class GPSTracker extends Activity {

	//The minimum distance to change Updates in meters 
		private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 20; 

	//The minimum time between updates 
		private static final long MIN_TIME_BW_UPDATES = (100 * 60 * 5); // 5 minutes

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

	  private void updateWithNewLocation(Location location) {
		    TextView myLocationText;
		    //myLocationText = (TextView)findViewById(R.id.myLocationText);


		    if (location != null) {      
		      double latitude = location.getLatitude();     //returns latitude
		      double longitude = location.getLongitude();   //returns longitude		      
		      String addressString;

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
/*		          sb.append(address.getLocality()).append("\n");
	          	  sb.append(address.getPostalCode()).append("\n");
		          sb.append(address.getCountryName());
*/		        }
		        addressString = sb.toString();
		      } catch (IOException exception) {}
		    }
		    //test output  
		    //myLocationText.setText("Your Current Position is:\n" + addressString)
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
}