package com.g08.sonder;
 

import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class ProfileFragment extends Fragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        
        
		
		Button profileFriendsListButton =(Button) rootView.findViewById(R.id.FriendsList);
		Button profilePostsButton =(Button) rootView.findViewById(R.id.SondersButton);
		Button sendMessageButton = (Button) rootView.findViewById(R.id.sendMessage);
		
		//Access the TextViews by Id
		TextView nameText = (TextView) rootView.findViewById(R.id.ProfileActivityUsername);
		TextView schoolText = (TextView) rootView.findViewById(R.id.ProfileSchool);
		TextView ageText = (TextView) rootView.findViewById(R.id.ProfileAge);
		
		
		ParseUser user = ParseUser.getCurrentUser();
		
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
		
		
		
		profileFriendsListButton = (Button) rootView.findViewById(R.id.FriendsList);
		profileFriendsListButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				/*Intent fList = new Intent(v.getContext(), FriendsList.class);
				startActivityForResult(fList,0);*/
				
				
			}
			
		});
		
		/*profilePostsButton = (Button) rootView.findViewById(R.id.SondersButton);
		profilePostsButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				 Intent intentMain = new Intent(getActivity() , 
                         FeedActivity.class);
				 Profile.this.startActivity(intentMain);
				
			}
			
		});*/
		
		sendMessageButton = (Button) rootView.findViewById(R.id.sendMessage);
		sendMessageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				EditText postField = (EditText) getView().findViewById(R.id.makePostText);
				String postText = postField.getText().toString();
				
				ParseUser user = ParseUser.getCurrentUser();
				user.put("AnonPost", postText);
				
				user.saveInBackground(new SaveCallback() {
					   public void done(com.parse.ParseException e) {
					     if (e == null) {
			
					       
					       
					       
					     } else {
					    	 
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
        
       /*Button toFullProfile = (Button) rootView.findViewById(R.id.fullProfile);
        toFullProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intentMain = new Intent(getActivity(), 
                        Profile.class);
		    	getActivity().startActivity(intentMain);
            }
        }); */
        return rootView;
      }    
        
    
}