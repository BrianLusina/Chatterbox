package com.chatterbox.chatterbox.presenters.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.TwitterSession;

import static com.chatterbox.chatterbox.views.login.LoginFragment.LOGINFRAGMENT_TAG;

/**
 Chatterbox-app
 ${PACKAGE_NAME}
 Created by lusinabrian on 24/09/16.
 Description: Default implementation of {@link LoginPresenter}
*/

public class LoginPresenterImpl implements LoginPresenter {
    private ProgressDialog progressDialog;

    /**Handles the Firebase with Twitter Login,
     * @param session Twitter Session retrieved from {@link com.chatterbox.chatterbox.views.login.LoginFragment}
     * Twitter Callback. If the callback is successful, the session is passed to this method and handled appropriately
     * @param firebaseAuth handles the siginin in with credential received from Twitter.
     * @param context The context in which this method will be called. By default it will be the LoginFragment
     * @return boolean value which is then set to trigger either an error messege if False of start the next activity*/
    public boolean handleFirebaseWithTwitter(TwitterSession session, FirebaseAuth firebaseAuth, final Context context){
        boolean success = false;
        Log.d(LOGINFRAGMENT_TAG, "FirebaseTwitter. session: "+session + "firebaseAuth " + firebaseAuth + " Context:" +context);
        // display the dialog
        displayDialog(true,context);

        // obtain credentials from Twitter
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener((Activity)context,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOGINFRAGMENT_TAG, "Sign in with Credential: onComplete:" + task.isSuccessful());
                        //if sign in is not successful
                        if(!task.isSuccessful()){
                            Log.w(LOGINFRAGMENT_TAG, "Sign in with TwitterCredential", task.getException());
                            SuperToast superToast = new SuperToast(context);
                            superToast.setDuration(Style.DURATION_SHORT);
                            superToast.setAnimations(Style.ANIMATIONS_FLY);
                            superToast.setText("Authentication failed, Please try again");
                            superToast.show();
                        }
                    }
                });
    }

    @Override
    public void cancelLogin() {

    }

    @Override
    public void displayDialog(boolean display, Context context) {
        if(display){
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }else{
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void displayErrorMessage(boolean display) {

    }
}
