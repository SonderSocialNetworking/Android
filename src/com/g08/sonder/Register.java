package com.g08.sonder;

import android.os.Bundle;
import android.app.Activity;
//import android.content.Intent;
//import android.view.Menu;
import android.view.View;
//import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Button;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;

import com.parse.ParseUser;
import com.parse.ParseObject;
import com.parse.SignUpCallback;
import com.parse.SaveCallback;
import android.util.Log;

public class Register extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        //ParseObject.registerSubclass(ParseObject.class);
        
        final Button signIn = (Button) findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	//Log.v("1","DEBUG1");
            	final ParseObject user = new ParseObject("User");
            	user.put("name", "UsersName");
            	//Log.v("1","DEBUG2");
            	//user.setUsername("my name");
            	///user.setPassword("my pass");
            	///user.setEmail("email@example.com");
            	  
            	// other fields can be set just like with ParseObject
            	//user.put("phone", "650-555-0000");
            	
            	user.saveInBackground(new SaveCallback() {
            	  public void done(ParseException e) {
            	    if (e == null) {
            	    	Log.v("1","User Added");
            	    	// Hooray! Let them use the app now.
            	    } else {
            	    	//Log.v("1","DEBUG5");
            	    	Log.v("1",e.toString());
            	    	// Sign up didn't succeed. Look at the ParseException
            	      // to figure out what went wrong
            	    }
            	  }
            	
            	});
            	Log.v("1","DEBUG6");
            
            
            
            
            
            }
        });
        
	}
	
	
	
	
	
	
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radio_male:
	            if (checked)
	                
	            break;
	        case R.id.radio_female:
	            if (checked)
	                
	            break;
	    }
	    
	}
}
