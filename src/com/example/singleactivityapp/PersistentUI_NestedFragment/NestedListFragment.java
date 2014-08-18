package com.example.singleactivityapp.PersistentUI_NestedFragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.singleactivityapp.Home;
import com.example.singleactivityapp.R;
import com.example.singleactivityapp.persistentUI_Fragment.ListDetailFragment;

public class NestedListFragment extends Fragment {
	public static final String TAG = "List Fragment As Child";
			
	private View _rootView;
	private boolean hasInitializedRootView = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_rootView == null) {
            // Inflate the layout for this fragment
            _rootView = inflater.inflate(R.layout.list_fragment, null);
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove _rootView from the existing parent view group
            // (it will be added back).
            ((ViewGroup)_rootView.getParent()).removeView(_rootView);
        }
		
		return _rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if(!hasInitializedRootView) {
			hasInitializedRootView = true;
			
			final ArrayList<String> words = new ArrayList<String>();
			for(int i = 1 ; i <= 30 ; i++) {
				words.add("Value " + i);
			}
			
			final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, words);
			ListView listView = (ListView) view.findViewById(R.id.list_fragment_list);
			listView.setAdapter(listAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// This fragment is not a usual case, so ignoring standards and just
					// showing it without tampering rest of the code to accomodate this.
					((Home) getActivity()).addFragment(ListDetailFragment.instance(words.get(arg2)), true);
				}
			});
		}
	}
}
