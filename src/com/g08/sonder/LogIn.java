package com.g08.sonder;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        Parse.initialize(this, "TWvYrlz5jTRkPrrjr4JohuDbR4zEXBc7pPWga4de", "P68SafB5lfpav8NXScXnttVRGorpcbINDvoz1aY0");

        final Button signIn = (Button) findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v){
        		
        		//grab the email from the text field
        		EditText email = (EditText) findViewById(R.id.emailField);
            	String emailVal = email.getText().toString();
            	
            	//grab the password from the text field
            	EditText pass = (EditText) findViewById(R.id.passwordField);
            	String password =pass.getText().toString();
        		
            	ParseUser.logInInBackground("email@gmail.com", "pword", new LogInCallback() {
            		//NOTE THAT ABOVE LINE CHANGED FOR CONVENIENCE
        		//ParseUser.logInInBackground(emailVal, password, new LogInCallback() {
        			  public void done(ParseUser user, ParseException e) {
        			    if (user != null) {
        			      // Hooray! The user is logged in.
        			    	//go to the main feed screen
        			    	Intent intentMain = new Intent(LogIn.this , 
                                    FeedActivity.class);
        			    	LogIn.this.startActivity(intentMain);
        			    	
        			    } else {
        			      // Sign in failed. Look at the ParseException to see what happened.
        			    	Log.v("1", "Could not Sign in");
        			    }
        			  }
        			});
        		
        	}
        });
        
        final Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // doStuff
                Intent intentMain = new Intent(LogIn.this , 
                                                   Register.class);
                    LogIn.this.startActivity(intentMain);
                    
            }
            
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        return true;
    }
}
