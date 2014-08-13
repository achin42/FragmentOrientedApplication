package com.example.singleactivityapp;

import com.example.singleactivityapp.base.SecondTierCommunicator;
import com.example.singleactivityapp.firsttier.GreetingsFragment.GreetingsFragmentCommunicator;
import com.example.singleactivityapp.firsttier.ListFragment.ListFragmentCommunicator;

public interface HomeInterface
extends SecondTierCommunicator,
		GreetingsFragmentCommunicator,
		ListFragmentCommunicator
{}