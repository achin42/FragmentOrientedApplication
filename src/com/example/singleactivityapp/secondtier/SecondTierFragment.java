package com.example.singleactivityapp.secondtier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.singleactivityapp.firsttier.FirstTierFragment;

public abstract class SecondTierFragment extends Fragment {
	protected SecondTierCommunicator secondTierCommunicator;
	
	/**
	 * Used to save from reinitializing Views when onViewCreated is called
	 * again after a popBackStack() call. To be used only when caching
	 * view state.
	 */
	public boolean hasInitializedRootView = false;
	private View _rootView;
	
	/**
	 * Will be used as handle to save transactions in backstack
	 * @return tag text
	 */
	public abstract String getTagText();
	
	/**
	 * To enable fragments capture back-press event and utilize it before
	 * it's used in the hosting Activity.
	 * @return true if consumed, else false
	 */
	public abstract boolean onBackPressed();
	
	
	/**
	 * Must be used if view caching is required from onCreateView of child 
	 * fragment to create the rootview for it.
	 * 
	 * This is a trick to retain View state of a fragment when removed and 
	 * added back.
	 * 
	 * However, this is not ideal and essentially a hack. This contradicts 
	 * Fragment's goal of being memory-friendly. This method keeps cached
	 * view state of a fragment when until it is referenced.
	 * 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @param layout
	 * @return rootView for fragment, either new or a cached one.
	 */
	public View createPersistentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layout) {
		if (_rootView == null) {
            // Inflate the layout for this fragment
            _rootView = inflater.inflate(layout, null);
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!(getActivity()  instanceof SecondTierCommunicator)) {
			throw new ClassCastException("Hosting activity must implement SecondTierCommunicator");
		} else {
			secondTierCommunicator = (SecondTierCommunicator) getActivity();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// Mark this fragment as the selected Fragment.
		secondTierCommunicator.setSelectedFragment(this);
		
		if(this instanceof FirstTierFragment) {
			// If foreground fragment is first tier, unlock drawer
			secondTierCommunicator.unlockDrawer();
		} else {
			// If foreground fragment is second tier, lock drawer
			secondTierCommunicator.lockDrawer();
		}
	}
	
	public interface SecondTierCommunicator {
		public void lockDrawer();
		public void unlockDrawer();
		public void setSelectedFragment(SecondTierFragment secondTierFragment);
		public void popBackStack();
		public void popBackStackTillTag(String tag);
		public void showSecondTierFragment(SecondTierFragment secondTierFragment, boolean withAnimation);
		public void addMultipleSecondTierFragments(SecondTierFragment secondTierFragments[]);
		public void onClickLogin();
	}
}
