package com.example.singleactivityapp.base;

import android.os.Bundle;



public abstract class DrawerItemBaseFragment extends BaseFragment {
	
	protected DrawerActivityInterface drawerActivityInterface;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!(getActivity() instanceof DrawerActivityInterface)) {
			throw new ClassCastException("Hosting activity must implement DrawerActivityInterface");
		} else {
			drawerActivityInterface = (DrawerActivityInterface) getActivity();
		}
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		
		drawerActivityInterface.setSelectedDrawerItem(this);
	}
}
