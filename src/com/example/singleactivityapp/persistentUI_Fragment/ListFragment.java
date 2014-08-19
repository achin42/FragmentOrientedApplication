package com.example.singleactivityapp.persistentUI_Fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.singleactivityapp.R;
import com.example.singleactivityapp.base.DrawerItemBaseFragment;

public class ListFragment extends DrawerItemBaseFragment {
	private static final String TAG = "List Fragment";
	
	private ListDetailInterface listDetailInterface;
	
	private ListView listView;
	
	@Override
	public String getTagText() {
		return TAG;
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Create cached rootView. View state will remain persistent over transitions.
		return createPersistentView(inflater, container, savedInstanceState, R.layout.list_fragment);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if(!hasInitializedRootView) {
			hasInitializedRootView = true;
			
			if(!(getActivity() instanceof ListDetailInterface)) {
				throw new ClassCastException("Hosting activity must implement ListDetailInterface");
			} else {
				listDetailInterface = (ListDetailInterface) getActivity();
			}
		
			ArrayList<String> words = new ArrayList<String>();
			for(int i = 1 ; i <= 30 ; i++) {
				words.add("Value " + i);
			}
			
			final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, words);
			listView = (ListView) view.findViewById(R.id.list_fragment_list);
			listView.setAdapter(listAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					listDetailInterface.showListDetail(listAdapter.getItem(arg2));
				}
			});
		}
	}
}
