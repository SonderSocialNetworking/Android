//package com.parse.starter;

//change
package com.g08.sonder;
//change

import android.app.Activity;
import android.os.Bundle;

import com.parse.ParseAnalytics;





public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		setContentView(R.layout.activity_feed); //changed from the above line
		ParseAnalytics.trackAppOpened(getIntent());
	}
}
