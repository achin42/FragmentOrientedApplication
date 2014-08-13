package com.example.singleactivityapp.base;

import android.os.Bundle;



public abstract class DrawerItemBaseFragment extends BaseFragment {
	
	protected DrawerActivityInterface hostActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!(getActivity()  instanceof BaseActivityInterface)) {
			throw new ClassCastException("Hosting activity must implement DrawerActivityInterface");
		} else {
			hostActivity = (DrawerActivityInterface) getActivity();
		}
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		
		hostActivity.setSelectedDrawerItem(this);
	}
}
