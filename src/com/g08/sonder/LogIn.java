package com.g08.sonder;

import com.parse.Parse;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class LogIn extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        Parse.initialize(this, "QCR8kZi3YL8CG8lbG5swwA6F4PwwB3r4zlqrzTP8", "UeHShg5arhtbCEuUm0euLJVm5Y9L0V2FNMYLxQR1");

        
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
