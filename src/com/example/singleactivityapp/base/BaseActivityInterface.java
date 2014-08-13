package com.example.singleactivityapp.base;

public interface BaseActivityInterface {
	public void lockDrawer();
	public void unlockDrawer();
	public void setSelectedFragment(BaseFragment secondTierFragment);
	public void popBackStack();
	public void popBackStackTillTag(String tag);
	public void showSecondTierFragment(BaseFragment secondTierFragment, boolean withAnimation);
	public void addMultipleSecondTierFragments(BaseFragment secondTierFragments[]);
	public void onClickLogin();
}