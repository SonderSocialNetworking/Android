package com.g08.sonder;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.net.ParseException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {


	CheckBox friendsCheckBox;

	Button bioButton;
	
	Button sendMessageButton;

	//Get the current User
	ParseUser user = ParseUser.getCurrentUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Parse.initialize(this, "TWvYrlz5jTRkPrrjr4JohuDbR4zEXBc7pPWga4de", "P68SafB5lfpav8NXScXnttVRGorpcbINDvoz1aY0");

	
		
		sendMessageButton = (Button) findViewById(R.id.sendMessage);

		//Access the TextViews by Id
		TextView nameText = (TextView) findViewById(R.id.ProfileActivityUsername);
		TextView schoolText = (TextView) findViewById(R.id.ProfileSchool);
		TextView ageText = (TextView) findViewById(R.id.ProfileAge);



		//Convert the user's full name to a char array for use in the TextView
		String userNameString = user.getString("FirstName") + " " + user.getString("LastName");
		char[] userNameArray = userNameString.toCharArray();

		nameText.setText(userNameArray, 0, userNameString.length());

		//Convert the user's school name to a char array
		String schoolName = user.getString("School");
		char[] schoolNameArray = schoolName.toCharArray();

		schoolText.setText(schoolNameArray, 0, schoolName.length());

		//Convert user's age to a char array
		String age = user.getString("Age");
		char[] ageArray = age.toCharArray();

		ageText.setText(ageArray, 0, age.length());




	
	

		sendMessageButton = (Button) findViewById(R.id.sendMessage);
		sendMessageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				EditText postField = (EditText) findViewById(R.id.makePostText);
				String postText = postField.getText().toString();


				user.put("AnonPost", postText);

				user.saveInBackground(new SaveCallback() {
					   public void done(com.parse.ParseException e) {
					     if (e == null) {


					       Toast.makeText(getApplicationContext(), "Successfully posted " + user.getString("AnonPost") + "!",
								      Toast.LENGTH_SHORT).show();

					     } else {
					    	 Toast.makeText(getApplicationContext(), "Post did not succeed",
								      Toast.LENGTH_SHORT).show();
					     }
					   }


					 });
				try {
					user.refresh();
				} catch (com.parse.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
}

}
