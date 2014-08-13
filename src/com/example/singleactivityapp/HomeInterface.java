package com.example.singleactivityapp;

import com.example.singleactivityapp.InterFragmentCommunication.GreetingsFragment.GreetingsFragmentCommunicator;
import com.example.singleactivityapp.base.SecondTierCommunicator;
import com.example.singleactivityapp.persistentUI_Fragment.ListFragment.ListFragmentCommunicator;

public interface HomeInterface
extends SecondTierCommunicator,
		GreetingsFragmentCommunicator,
		ListFragmentCommunicator
{}