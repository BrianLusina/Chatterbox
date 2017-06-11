package com.chatterbox.chatterbox.ui.auth

import com.chatterbox.chatterbox.di.PerActivity
import com.chatterbox.chatterbox.ui.base.BasePresenter
import com.google.firebase.auth.FirebaseAuth

import com.twitter.sdk.android.core.TwitterSession

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */

@PerActivity
interface AuthPresenter<V : AuthView> : BasePresenter<V> {

    /**
     * Callback for logging in with Twitter
     * This will attempt to log in with Twitter and return response of operation back to
     * View
     * @param twitterSession TwitterSession that will be passed in from TwitterLoginButton SDK
     * */
    fun onTwitterLoginClick(firebaseAuth: FirebaseAuth, twitterSession: TwitterSession) : Unit

    /**
     * Callback for logging in with Facebook
     * */
    fun onFacebookLoginClick() : Unit

    /**
     * Callback for logging in with Google
     * */
    fun onGoogleLoginClick() : Unit

    /**
     * Callback for logging into the server
     * @param email user's email
     * @param password user's entered password
     * */
    fun onServerLoginClick(email : String, password: String) : Unit
}