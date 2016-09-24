package com.chatterbox.chatterbox.presenters.login;

import android.content.Context;

/**
 Chatterbox-app
 ${PACKAGE_NAME}
 * Created by lusinabrian on 24/09/16.
 Class Description: Interface for {@link com.chatterbox.chatterbox.views.login.LoginFragment}
 that will be implemented
 */

public interface LoginPresenter {
    /**Cancel the user login in case client experiences network failures*/
    void cancelLogin();

    /**Display the login dialog to show login progress
     * @param display whether to display the dialog or not
     * @param context in which context to display the dialog*/
    void displayDialog(boolean display, Context context);

    /**Incase of any errors display errors to the user in case of any login failure*/
    void displayErrorMessage(boolean show);

}
