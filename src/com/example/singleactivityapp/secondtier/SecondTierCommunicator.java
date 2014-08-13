package com.example.singleactivityapp.secondtier;

public interface SecondTierCommunicator {
	public void lockDrawer();
	public void unlockDrawer();
	public void setSelectedFragment(SecondTierFragment secondTierFragment);
	public void popBackStack();
	public void popBackStackTillTag(String tag);
	public void showSecondTierFragment(SecondTierFragment secondTierFragment, boolean withAnimation);
	public void addMultipleSecondTierFragments(SecondTierFragment secondTierFragments[]);
	public void onClickLogin();
}