package com.example.singleactivityapp;

import com.example.singleactivityapp.InterFragmentCommunication.GreetingsFragment.GreetingsFragmentCommunicator;
import com.example.singleactivityapp.base.BaseInterface;
import com.example.singleactivityapp.persistentUI_Fragment.ListFragment.ListFragmentCommunicator;

public interface HomeInterface
extends BaseInterface,
		GreetingsFragmentCommunicator,
		ListFragmentCommunicator
{}