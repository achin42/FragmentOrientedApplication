package com.example.singleactivityapp.base;

public interface DrawerActivityInterface extends BaseActivityInterface {
	public void lockDrawer();
	public void unlockDrawer();
	public void setSelectedDrawerItem(DrawerItemBaseFragment fragment);
}