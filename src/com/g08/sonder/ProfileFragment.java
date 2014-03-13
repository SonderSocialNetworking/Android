package com.g08.sonder;
 

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
 
public class ProfileFragment extends Fragment {
	ParseUser user = ParseUser.getCurrentUser();
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);		
		
		//Access the TextViews by Id
		TextView nameText = (TextView) rootView.findViewById(R.id.ProfileActivityUsername);
		TextView schoolText = (TextView) rootView.findViewById(R.id.ProfileSchool);
		TextView ageText = (TextView) rootView.findViewById(R.id.ProfileAge);
		TextView bioText = (TextView) rootView.findViewById(R.id.ProfileBio);
		
		Button sendMessageButton = (Button) rootView.findViewById(R.id.sendMessage);//button to make post/send message depending on whose profile
		Button editProfileButton =(Button) rootView.findViewById(R.id.editProfileButton);// button to allow user to edit information displayed

		
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
		
		//convert bio to char array nd display on profile
		String bio = user.getString("Bio");
		char[] bioArray = bio.toCharArray();
		
		bioText.setText(bioArray, 0, bio.length());
		
		
		
		
		
		editProfileButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
		        startActivity(new Intent(getActivity(), EditProfile.class));
			}
			
		});
		
		
		//Get the imageButton for the profile Pic
		final ImageButton profPic = (ImageButton) rootView.findViewById(R.id.ProfileActivityPhoto);
		
		
		ParseFile file;
		file = user.getParseFile("Pic");
		if(file!=null)
		{
		
			byte[] bytesArray = null;
			try {
				bytesArray = file.getData();
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
			
		
			Bitmap bmp = BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);
		
			profPic.setImageBitmap(bmp);
		
		}
		profPic.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, 1); 
				
				
				
				
				
			}
			
			
			
		});
		
		EditText textField = (EditText) rootView.findViewById(R.id.makePostText);
		textField.setHint(user.getString("AnonPost"));
		
		
		sendMessageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				EditText postField = (EditText) getView().findViewById(R.id.makePostText);
				String postText = postField.getText().toString();
				
				//ParseUser user = ParseUser.getCurrentUser();
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
				Toast.makeText(v.getContext(), "You've made a post!",
					      Toast.LENGTH_SHORT).show();
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
    
    public void onActivityResult(int requestCode, int resultCode, 
    	       Intent imageReturnedIntent) {
    	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

    	    switch(requestCode) { 
    	    case 1:
    	    	
    	        if(resultCode == Activity.RESULT_OK){  
    	        	Log.d("1","Check here");
    	            Uri selectedImage = imageReturnedIntent.getData();
    	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

    	            Cursor cursor = getActivity().getContentResolver().query(
    	                               selectedImage, filePathColumn, null, null, null);
    	            cursor.moveToFirst();

    	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
    	            String filePath = cursor.getString(columnIndex);
    	            cursor.close();


    	            Bitmap profSelectedImage = BitmapFactory.decodeFile(filePath);
    	            
    	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	            
    	            Bitmap smaller = Bitmap.createScaledBitmap(profSelectedImage, 400, 400, false);
    	            smaller.compress(Bitmap.CompressFormat.JPEG, 20, stream);
    	            byte[] data = stream.toByteArray();
    	            
    	            ParseFile picFile = new ParseFile(data);
    	            picFile.saveInBackground(new SaveCallback() {
  					   public void done(com.parse.ParseException e) {
   					     if (e == null) {
   			
   					       Log.d("1", "Picture saved");
   					       
   					       
   					     } else {
   					    	 Log.d("1","Picture did not save");
   					    	 
   					     }
   					   }

   					
   					 });
    	            
    	            //ParseUser user = ParseUser.getCurrentUser();
    	            
    	            user.put("Pic", picFile);
    	            user.saveInBackground(new SaveCallback() {
 					   public void done(com.parse.ParseException e) {
 					     if (e == null) {
 			
 					       Log.d("1", "Picture saved to profile");
 					       
 					       
 					     } else {
 					    	 Log.d("1","Picture not on profile");
 					    	 
 					     }
 					   }

 					
 					 });
    	            try {
						user.refresh();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    	        }
    	    }
    	}
        
    
}