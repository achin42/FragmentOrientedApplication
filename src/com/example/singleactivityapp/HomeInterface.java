package com.example.singleactivityapp;

import com.example.singleactivityapp.InterFragmentCommunication.GreetingsFragment.GreetingsFragmentCommunicator;
import com.example.singleactivityapp.base.BaseActivityInterface;
import com.example.singleactivityapp.base.DrawerActivityInterface;
import com.example.singleactivityapp.persistentUI_Fragment.ListFragment.ListFragmentCommunicator;

public interface HomeInterface
extends BaseActivityInterface,
		DrawerActivityInterface,
		GreetingsFragmentCommunicator,
		ListFragmentCommunicator
{}