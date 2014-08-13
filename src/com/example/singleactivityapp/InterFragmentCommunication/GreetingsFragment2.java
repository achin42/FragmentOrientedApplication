package com.example.singleactivityapp.InterFragmentCommunication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.singleactivityapp.R;
import com.example.singleactivityapp.Utils.FacebookUtils;
import com.example.singleactivityapp.base.BaseFragment;
import com.facebook.Session;

public class GreetingsFragment2 extends BaseFragment {
	private static final String TAG = "Greetings Fragment 2";
	private static final String EXTRA_NAME = "extra_name";
	
	private RelativeLayout facebookButton;
	private ImageView facebookImage;
	private TextView facebookText;
	private TextView greetingsText;
	
	public static GreetingsFragment2 instance(String name) {
		Bundle arguments = new Bundle();
		arguments.putString(EXTRA_NAME, name);
		GreetingsFragment2 greetingsFragment2 = new GreetingsFragment2();
		greetingsFragment2.setArguments(arguments);
		return greetingsFragment2;
	}
	
	@Override
	public String getTagText() {
		return TAG;
	}

	@Override
	public boolean onBackPressed() {
		secondTierCommunicator.popBackStack();
		return true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.greetings_fragment_2, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		facebookButton = (RelativeLayout) getView().findViewById(R.id.greetings_fragment_2_facebook_button);
		facebookImage = (ImageView) getView().findViewById(R.id.greetings_fragment_2_facebook_button_image);
		facebookText = (TextView) getView().findViewById(R.id.greetings_fragment_2_facebook_text);
		greetingsText = (TextView) view.findViewById(R.id.greetings_fragment_2_greetings_text);
		
		String name = getArguments().getString(EXTRA_NAME);
		greetingsText.setText("Hey " + name + "!\nHave a nice day!");
		
		facebookButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Session facebookSession = Session.getActiveSession();
				if(facebookSession.isOpened()) {
					FacebookUtils.postOnWall(getActivity(), "From Single Activity Fragment", greetingsText.getText().toString(), "www.google.com");
				} else {
					secondTierCommunicator.onClickLogin();
				}
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Session facebookSession = Session.getActiveSession();
		if(facebookSession.isOpened()) {
			facebookImage.setBackgroundResource(R.drawable.facebook_active_states);
			facebookButton.setBackgroundColor(getResources().getColor(R.color.light_blue));
			facebookText.setTextColor(getResources().getColor(android.R.color.white));
		} else {
			facebookImage.setBackgroundResource(R.drawable.facebook_inactive_states);
			facebookButton.setBackgroundColor(getResources().getColor(R.color.light_gray));
			facebookText.setTextColor(getResources().getColor(R.color.text_gray));
		}
	}
	
	public interface GreetingsFragment2Communicator {
		public String getName();
	}
}
