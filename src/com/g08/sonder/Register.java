package com.g08.sonder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
//import android.content.Intent;
//import android.view.Menu;
import android.view.View;
import android.widget.EditText;
//import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;

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
        Parse.initialize(this, "TWvYrlz5jTRkPrrjr4JohuDbR4zEXBc7pPWga4de", "P68SafB5lfpav8NXScXnttVRGorpcbINDvoz1aY0");
        
        //ParseObject.registerSubclass(ParseObject.class);
        
        final Button signIn = (Button) findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	//Log.v("1","DEBUG1");
            	ParseUser user = new ParseUser();
            	
            	//Log.v("1","DEBUG2");
            	
            	//Grabs username from the email field
            	EditText email = (EditText) findViewById(R.id.emailField);
            	String emailVal = email.getText().toString();
            	EditText email2 = (EditText) findViewById(R.id.confirmEmailField);
            	String emailVal2 = email2.getText().toString();
            	boolean matches;
            	
            	if(emailVal.equals(emailVal2))
            	{
            		matches = true;
            	}
            	else
            		matches = false;
            	
            	
            	String userEmail = emailVal;
            	user.setUsername(userEmail);
            	
            	//grab user password from the password field
            	
            	EditText pass = (EditText) findViewById(R.id.passwordField);
            	String password =pass.getText().toString();
            	user.setPassword(password);
            	
            	user.setEmail(emailVal);
            	
            	//Add user first and last names
            	EditText firstName = (EditText) findViewById(R.id.firstNameField);
            	String first = firstName.getText().toString();
            	EditText lastName = (EditText) findViewById(R.id.lastNameField);
            	String last = lastName.getText().toString();
            	user.put("FirstName", first);
            	user.put("LastName", last);
            	
            	//Add user birthday
            	EditText birthday = (EditText) findViewById(R.id.birth_date);
            	String birth = birthday.getText().toString();
            	user.put("Birthday", birth);
            	
            	
            	//Radio selection
            	RadioGroup rg=(RadioGroup)findViewById(R.id.gender_group);
            	  String radiovalue=  ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString(); 
            	
            	user.put("Gender", radiovalue);
            	user.put("School", "UCSB");
            	user.put("Age", "18-23");
            	
            	// other fields can be set just like with ParseObject
            	//user.put("phone", "650-555-0000");
            	
            	user.signUpInBackground(new SignUpCallback() {
            		  public void done(ParseException e) {
            		    if (e == null) {
            		      // Hooray! Let them use the app now.
            		    	Log.v("1","UserCreated");
            		    	Intent intentMain = new Intent(Register.this , 
                                    LogIn.class);
            		    	Register.this.startActivity(intentMain);
            		    } else {
            		      // Sign up didn't succeed. Look at the ParseException
            		      // to figure out what went wrong
            		    	Log.v("1","UserNotCreated");
            		    	Log.v("2", e.getMessage());
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
