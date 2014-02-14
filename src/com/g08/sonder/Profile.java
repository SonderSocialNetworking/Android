package com.g08.sonder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Profile extends Activity {

	
	CheckBox friendsCheckBox;
	
	Button bioButton;
	Button profileFriendsListButton;
	Button profilePostsButton;
	Button sendMessageButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		friendsCheckBox = (CheckBox) findViewById(R.id.friendCheckBox);
		isFriendCheckBox();
		
		profileFriendsListButton =(Button) findViewById(R.id.FriendsList);
		profilePostsButton =(Button) findViewById(R.id.PostsButton);
		sendMessageButton = (Button) findViewById(R.id.sendMessage);
		
		setUpOnClickListeners();
		
		
	}
	
	private void setUpOnClickListeners() {
		
		bioButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				
			}
			
		});
		
		profileFriendsListButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent fList = new Intent(v.getContext(), FriendsList.class);
				startActivityForResult(fList,0);
			}
			
		});
		
		profilePostsButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		sendMessageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
		
			}
			
		});
	}

	private void isFriendCheckBox(){
		friendsCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
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
