package com.example.singleactivityapp.backHandledFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.singleactivityapp.R;
import com.example.singleactivityapp.base.DrawerItemBaseFragment;

public class BackTapperFragment extends DrawerItemBaseFragment {
	private static final String TAG = "Child List Fragment";
	
	private boolean hasText1Changed = false;
	private boolean hasText2Changed = false;
	
	@Override
	public String getTagText() {
		return TAG;
	}

	@Override
	public boolean onBackPressed() {
		if(!hasText1Changed) {
			((TextView) getView().findViewById(R.id.back_tapper_fragment_text_1)).setText("After change");
			hasText1Changed = true;
			return true;
		} else if(!hasText2Changed) {
			((TextView) getView().findViewById(R.id.back_tapper_fragment_text_2)).setText("After change");
			hasText2Changed = true;
			return true;
		}
		return false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.back_tapper_fragment, null);
	}
}
