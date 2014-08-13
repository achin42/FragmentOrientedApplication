package com.example.singleactivityapp.BackStackHandling;

import com.example.singleactivityapp.R;
import com.example.singleactivityapp.base.BaseFragment;
import com.example.singleactivityapp.firsttier.FirstTierFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class StepFragment extends FirstTierFragment {
	private static final String TAG = "Step Fragment";
	
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
		return inflater.inflate(R.layout.step_fragment, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		// Go to First Step Button
		((Button) view.findViewById(R.id.step_fragment_go_to_1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				hostActivity.showSecondTierFragment(new FirstStepFragment(), true);
			}
		});
		
		// Add Multiple Fragments Button
		((Button) view.findViewById(R.id.step_fragment_go_to_2)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				BaseFragment[] secondTierFragments = new BaseFragment[2];
				secondTierFragments[0] = new FirstStepFragment();
				secondTierFragments[1] = new SecondStepFragment();
				hostActivity.addMultipleSecondTierFragments(secondTierFragments);
			}
		});
	}
}
