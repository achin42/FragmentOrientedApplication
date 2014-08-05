package com.example.singleactivityapp.firsttier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.singleactivityapp.R;

public class GreetingsFragment extends FirstTierFragment {
	private static final String TAG = "Greetings Fragment";

	private GreetingsFragmentCommunicator greetingsFragmentCommunicator;
	
	private EditText nameEditText;
	private Button greetingsButton;
	
	@Override
	public String getTagText() {
		return TAG;
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.greetings_fragment, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(!(getActivity() instanceof GreetingsFragmentCommunicator)) {
			throw new ClassCastException("Hosting activity must implement GreetingsFragmentCommunicator");
		} else {
			greetingsFragmentCommunicator = (GreetingsFragmentCommunicator) getActivity();
		}
		
		initUI();
	}
	
	private void initUI() {
		nameEditText = (EditText) getView().findViewById(R.id.greetings_fragment_name_edit);
		greetingsButton = (Button) getView().findViewById(R.id.greetings_fragment_see_greetings);
		
		greetingsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String name = nameEditText.getText().toString().trim();
				if(name.equals("")) {
					Toast.makeText(getActivity(), "Enter name to get greeted", Toast.LENGTH_SHORT).show();
				} else {
					greetingsFragmentCommunicator.showGreetings(name);
				}
			}
		});
	}
	
	public interface GreetingsFragmentCommunicator {
		public void showGreetings(String name);
	}
}