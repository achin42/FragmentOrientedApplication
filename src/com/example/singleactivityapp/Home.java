package com.example.singleactivityapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.singleactivityapp.BackStackHandling.BackStackHandlerFragment;
import com.example.singleactivityapp.InterFragmentCommunication.GreetingsRequestFragment;
import com.example.singleactivityapp.InterFragmentCommunication.GreetingsFragment;
import com.example.singleactivityapp.PersistentUI_NestedFragment.ParentFragment;
import com.example.singleactivityapp.backHandledFragment.BackHandledFragment;
import com.example.singleactivityapp.base.BaseFragment;
import com.example.singleactivityapp.base.DrawerItemBaseFragment;
import com.example.singleactivityapp.persistentUI_Fragment.ListDetailFragment;
import com.example.singleactivityapp.persistentUI_Fragment.ListFragment;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;

public class Home extends FragmentActivity implements HomeInterface {

	private DrawerLayout drawer;
	private Button facebookButton;
	private BaseFragment selectedFragment;
	private DrawerItemBaseFragment selectedDrawerItemFragment;
	
	private boolean isWarnedToClose = false;
	
	private boolean isDrawerLocked = false;
	
	// FACEBOOK --------------
  	private UiLifecycleHelper uiHelper;
  	
  	private Session session;
  	private Session.StatusCallback callback = new SessionStatusCallback();
    // -----------------------
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		openDrawer();
		
		showDrawerItemFragment(new GreetingsRequestFragment());
		
		initUI();
		
		// Set up Facebook
		setUpFacebookInOnCreate(arg0);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Session.getActiveSession().removeCallback(callback);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		uiHelper.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	// Thats it for back press handling of whole app. Fragments will
	// be first asked to consume.
	@Override
	public void onBackPressed() {
		// Check if selectedFragment is not consuming back press
		if(!selectedFragment.onBackPressed()) {
			// If not consumed, handle it.
			handleBackPressInThisActivity();
		}
	}
	
	
	/**
	 * Will close this Activity if double back pressed within 2000 ms
	 */
	private void handleBackPressInThisActivity() {
		if(isWarnedToClose) {
			finish();
		} else {
			isWarnedToClose = true;
			Toast.makeText(this, "Activity: Tap again to close application", Toast.LENGTH_SHORT).show();
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					isWarnedToClose = false;
				}
			}, 2000);
		}
	}
	
	public void openDrawer() {
		drawer.openDrawer(Gravity.LEFT);
	}
	
	public void closeDrawer() {
		if(drawer.isDrawerOpen(Gravity.LEFT)) {
			drawer.closeDrawer(Gravity.LEFT);
		}
	}
    
    @Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		this.selectedFragment = selectedFragment;
		
		if(selectedFragment instanceof DrawerItemBaseFragment) {
			// If foreground fragment is drawer item, unlock drawer
			unlockDrawer();
		} else {
			// If foreground fragment is not drawer item, lock drawer
			lockDrawer();
		}
	}
	

	
	private void initUI() {
		facebookButton = (Button) findViewById(R.id.home_facebook_button);
		
		((TextView) findViewById(R.id.drawer_inter_fragment_fragment_row)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!(selectedDrawerItemFragment instanceof GreetingsRequestFragment)) {
					showDrawerItemFragment(new GreetingsRequestFragment());
				}
				
				closeDrawer();
			}
		});
		
		((TextView) findViewById(R.id.drawer_back_handled_fragment_row)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!(selectedDrawerItemFragment instanceof BackHandledFragment)) {
					showDrawerItemFragment(new BackHandledFragment());
				}
				
				closeDrawer();
			}
		});
		
		((TextView) findViewById(R.id.drawer_back_stack_row)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!(selectedDrawerItemFragment instanceof BackStackHandlerFragment)) {
					showDrawerItemFragment(new BackStackHandlerFragment());
				}
				
				closeDrawer();
			}
		});
		
		((TextView) findViewById(R.id.drawer_persistent_ui_fragment_row)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!(selectedDrawerItemFragment instanceof ListFragment)) {
					showDrawerItemFragment(new ListFragment());
				}
				
				closeDrawer();
			}
		});
		
		((TextView) findViewById(R.id.drawer_persistent_ui_nested_fragment_row)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!(selectedDrawerItemFragment instanceof ParentFragment)) {
					showDrawerItemFragment(new ParentFragment());
				}
				
				closeDrawer();
			}
		});
		
		
	}
	
	private void lockDrawer() {
		if(isDrawerLocked) {
			isDrawerLocked = false;
	    	drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		}
    }
    
	private void unlockDrawer() {
    	if(!isDrawerLocked) {
    		isDrawerLocked = true;
        	drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    	}
    }
	
	private void showDrawerItemFragment(DrawerItemBaseFragment firstTierFragment) {
		// Clear backstack if app is not at a first-tier fragment
		// and drawer is not locked, so that app could be switched between
		// any first-tier fragment from anywhere.
		if(!(selectedFragment instanceof DrawerItemBaseFragment) && !isDrawerLocked) {
			clearBackStack();
		}
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.home_frame_layout_for_fragments, firstTierFragment, firstTierFragment.getTagText());
        ft.commit();
	}

	@Override
	public void addFragment(BaseFragment fragment, boolean withAnimation) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		if(withAnimation) {
			// TO ENABLE FRAGMENT ANIMATION
			// Format: setCustomAnimations(old_frag_exit, new_frag_enter, old_frag_enter, new_frag_exit); 
			ft.setCustomAnimations(R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_right);
		}
		
		ft.replace(R.id.home_frame_layout_for_fragments, fragment, fragment.getTagText());
		ft.addToBackStack(fragment.getTagText());
        ft.commit();
	}
	
	/**
	 * Clears transaction backstack. In this case, after this method
	 * all (or any) extended fragment(s) will get removed and
	 * view will resort back to current drawer-item fragment.
	 * 
	 * Will only be useful when we are not locking the drawer while in
	 * extended fragments and can switch between drawer-item fragments
	 * from anywhere in the app.
	 */
	private void clearBackStack() {
    	final FragmentManager fragmentManager = getSupportFragmentManager();
    	while (fragmentManager.getBackStackEntryCount() != 0) {
    		fragmentManager.popBackStackImmediate();
    	}
    }
	
	@Override
	public void popBackStack() {
		getSupportFragmentManager().popBackStackImmediate();
	}
	
	@Override
	public void popBackStackTillTag(String tag) {
		getSupportFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	@Override
	public void showGreetings(String name) {
		addFragment(GreetingsFragment.instance(name), false);
	}

	@Override
	public void showListDetail(String word) {
		addFragment(ListDetailFragment.instance(word), true);
	}
	
	
	@Override
	public void setSelectedDrawerItem(DrawerItemBaseFragment fragment) {
		this.selectedDrawerItemFragment = fragment;
	}

	
	/**
	 * Just to demonstrate how it is transaction backstack rather than fragment
	 * backstack. Not a method to be used normally. 
	 */
	@Override
	public void addMultipleFragments(BaseFragment[] fragments) {
		// Initialize a Fragment Transaction.
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		// Record all steps for the transaction.
		for(int i = 0 ; i < fragments.length ; i++) {
			ft.setCustomAnimations(R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_right);
			ft.replace(R.id.home_frame_layout_for_fragments, fragments[i], fragments[i].getTagText());
		}
		
		// Add the transaction to backStack with tag of first added fragment
		ft.addToBackStack(fragments[0].getTagText());
		
		// Commit the transaction.
        ft.commit();
	}
	
	
	
	
	
	//**********************************************************//**********************************************************
	  //********************** FACEBOOK **************************//**********************************************************
	  //**********************************************************//**********************************************************
	    private void setUpFacebookInOnCreate(Bundle savedInstanceState) {
	    	uiHelper = new UiLifecycleHelper(this, callback);
	        uiHelper.onCreate(savedInstanceState);
	        
	        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

	        session = Session.getActiveSession();
	        if (session == null) {
	            if (savedInstanceState != null) {
	                session = Session.restoreSession(this, null, callback, savedInstanceState);
	            }
	            if (session == null) {
	                session = new Session(this);
	            }
	            Session.setActiveSession(session);
	            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	                session.openForRead(new Session.OpenRequest(this).setCallback(callback));
	            }
	        }
	        
			updateFacebookLoginStatus();
	    }
	    
	    private void updateFacebookLoginStatus() {
	    	Log.d("", "in Update Status");
	    	Session session = Session.getActiveSession();
	    	if (session.isOpened()) {
	    		Log.d("", "Facebook Session is Open");
	    		facebookButton.setBackgroundResource(R.drawable.facebook_active_states);
	    		facebookButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						onClickLogout();
					}
				});
	    	} else {
	    		Log.d("", "Facebook Session is Closed");
	    		facebookButton.setBackgroundResource(R.drawable.facebook_inactive_states);
	    		facebookButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						onClickLogin();
					}
				});
	    	}
	    }

	    @Override
	    public void onClickLogin() {
	    	Log.d("", "Inside Login");
	    	Session session = Session.getActiveSession();
	    	if (!session.isOpened() && !session.isClosed()) {
	    		session.openForRead(new Session.OpenRequest(this).setCallback(callback));
	    	} else {
	    		Session.openActiveSession(this, true, callback);
	    	}
	    }
	      
	    public void onClickLogout() {
	    	Log.d("", "Inside Logout");
	    	Session session = Session.getActiveSession();
	    	if (!session.isClosed()) {
	    		session.closeAndClearTokenInformation();
	    	}
	    }
	  	
	  	private class SessionStatusCallback implements Session.StatusCallback {
	  		@Override
	  		public void call(Session session, SessionState state, Exception exception) {	
	  			Log.d("", "Inside Session Status Callback");
	          	updateFacebookLoginStatus();
	  		}
	  	}
	  	
	  	public Session getSession() {
			return session;
		}
	  //**********************************************************//**********************************************************
	  //**********************************************************//**********************************************************
	  //**********************************************************//**********************************************************
}
