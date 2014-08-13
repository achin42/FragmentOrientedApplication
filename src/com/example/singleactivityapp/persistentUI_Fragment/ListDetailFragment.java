package com.example.singleactivityapp.persistentUI_Fragment;

import com.example.singleactivityapp.R;
import com.example.singleactivityapp.base.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListDetailFragment extends BaseFragment {
	private static final String TAG = "List Detail Fragment";
	private static final String EXTRA_WORD_TO_SHOW = "extra_word_to_show";
	
	private String word; 

	public static ListDetailFragment instance(String wordToShow) {
		Bundle arguments = new Bundle();
		arguments.putString(EXTRA_WORD_TO_SHOW, wordToShow);
		ListDetailFragment listDetailFragment = new ListDetailFragment();
		listDetailFragment.setArguments(arguments);
		return listDetailFragment;
	}
	
	@Override
	public String getTagText() {
		return TAG;
	}

	@Override
	public boolean onBackPressed() {
		secondTierCommunicator.popBackStack();
		return true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_detail_fragment, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		word = getArguments().getString(EXTRA_WORD_TO_SHOW);
		
		((TextView) view.findViewById(R.id.list_detail_fragment_text)).setText(word);
	}
}
