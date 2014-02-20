package com.g08.sonder;
 

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
 
public class ProfileFragment extends Fragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        
       Button toFullProfile = (Button) rootView.findViewById(R.id.fullProfile);
        toFullProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intentMain = new Intent(getActivity(), 
                        Profile.class);
		    	getActivity().startActivity(intentMain);
            }
        }); 
        return rootView;
      }    
        
    
}