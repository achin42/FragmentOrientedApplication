package com.example.singleactivityapp;

import com.example.singleactivityapp.firsttier.GreetingsFragment.GreetingsFragmentCommunicator;
import com.example.singleactivityapp.firsttier.ListFragment.ListFragmentCommunicator;
import com.example.singleactivityapp.secondtier.SecondTierFragment.SecondTierCommunicator;

public interface HomeInterface
extends SecondTierCommunicator,
		GreetingsFragmentCommunicator,
		ListFragmentCommunicator
{}