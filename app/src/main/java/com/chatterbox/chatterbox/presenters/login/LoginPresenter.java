package com.chatterbox.chatterbox.presenters.login;

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

    /**Display the login dialog*/
    void displayDialog();

    /**Incase of any errors display errors to the user in case of any login failure*/
    void displayErrorMessage();
    
}
