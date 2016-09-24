package com.chatterbox.chatterbox.presenters.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;

/**
 Chatterbox-app
 ${PACKAGE_NAME}
 Created by lusinabrian on 24/09/16.
 Description: Default implementation of {@link LoginPresenter}
*/

public class LoginPresenterImpl implements LoginPresenter {
    ProgressDialog progressDialog;

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
