package com.g08.sonder;

import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends Activity {

	ParseUser user = ParseUser.getCurrentUser();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		Button saveChanges = (Button) findViewById(R.id.Save);//button to save changes to user and go back to main activity
	
    	
    	saveChanges.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				//change and add user first and last names
		    	EditText newfirstName = (EditText) findViewById(R.id.firstNameField);
		    	String first = newfirstName.getText().toString();
		    	EditText newlastName = (EditText) findViewById(R.id.lastNameField);
		    	String last = newlastName.getText().toString();
		    	user.put("FirstName", first);
		    	user.put("LastName", last);
		    	
		    	//edit school
		    	EditText newSchool = (EditText) findViewById(R.id.editSchool);
		    	String school = newSchool.getText().toString();
		    	user.put("School", school);
		    	
		    	//edit bio
		    	EditText userBio = (EditText) findViewById(R.id.bioEdit);
		    	String bio = userBio.getText().toString();
		    	user.put("Bio", bio);
				
				
		        startActivity(new Intent(EditProfile.this, FeedActivity.class));
			}
			
		});
    	

		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

}
