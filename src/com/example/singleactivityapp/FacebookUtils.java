package com.example.singleactivityapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class FacebookUtils {
	public static void postOnWall(Activity activity, String name, String description, String link) {
		Session session = Session.getActiveSession();
		if(session == null) {
			// No Active Facebook Session present. Show toast and return.
			Toast.makeText(activity, "You haven't logged in to Facebook", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (FacebookDialog.canPresentShareDialog(activity, FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			showShareDialog(activity, name, description, link);
		} else {
			// Publish the post using the Feed Dialog
			showFeedDialog(activity, name, description, link);
		}
	}
	
	private static void showShareDialog(Activity activity, String name, String description, String link) {
		UiLifecycleHelper uiHelper = new UiLifecycleHelper(activity, null);
		FacebookDialog.ShareDialogBuilder shareDialogBuilder = new FacebookDialog.ShareDialogBuilder(activity);
		shareDialogBuilder.setName(name);
		shareDialogBuilder.setCaption("Single Activity App");
		shareDialogBuilder.setDescription(description);
		if(link != null) {
			shareDialogBuilder.setLink(link);
		}
		
		FacebookDialog shareDialog = shareDialogBuilder.build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
	}
	
	private static void showFeedDialog(final Activity activity, String name, String description, String link) {
	    Bundle params = new Bundle();
	    params.putString("name", name);
	    params.putString("caption", "Single Activity App");
	    params.putString("description", description);
	    
	    if(link != null) { 
	    	params.putString("link", link);
	    }
	    
	    WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(activity, Session.getActiveSession(), params)).setOnCompleteListener(new OnCompleteListener() {

            @Override
            public void onComplete(Bundle values, FacebookException error) {
                if (error == null) {
                    final String postId = values.getString("post_id");
                    if (postId != null) {
                        Log.d("", "Posted story, id: "+postId);
                    } else {
                    	Log.d("", "Publish cancelled");
                    }
                } else if (error instanceof FacebookOperationCanceledException) {
                	Log.d("", "Publish cancelled");
                } else {
                	Log.d("", "Error posting story");
                }
            }
        }).build();
	    feedDialog.show();
	}
}
