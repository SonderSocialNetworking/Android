package com.g08.sonder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Feed extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_feed, menu);
        return true;
    }
}
