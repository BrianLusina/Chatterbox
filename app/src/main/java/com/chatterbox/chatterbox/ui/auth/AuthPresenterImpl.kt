package com.chatterbox.chatterbox.ui.auth

import com.chatterbox.chatterbox.data.DataManager
import com.chatterbox.chatterbox.ui.base.BasePresenter
import com.chatterbox.chatterbox.ui.base.BasePresenterImpl
import com.twitter.sdk.android.core.TwitterSession
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import com.google.firebase.auth.TwitterAuthProvider
import com.chatterbox.chatterbox.data.LoggedInMode
import com.google.firebase.auth.FirebaseUser


/**
 * @author lusinabrian on 02/06/17.
 * *
 * @Notes
 */

class AuthPresenterImpl<V : AuthView>
@Inject constructor(mDataManager: DataManager, mCompositeDisposable: CompositeDisposable): BasePresenterImpl<V>(mDataManager, mCompositeDisposable), AuthPresenter<V>{

    override fun onAttach(mBaseView: V) {
        super.onAttach(mBaseView)
    }

    override fun onTwitterLoginClick(twitterSession: TwitterSession) {
        val credential = TwitterAuthProvider.getCredential(
                twitterSession.authToken.token,
                twitterSession.authToken.secret)

        // get the login success and firebase user
        val (loginSuccess, FirebaseUser) = mDataManager.doLoginWithTwitter(credential)

        loginUserCallback(loginSuccess, loggedInMode = LoggedInMode.LOGGED_IN_MODE_TWITTER,
                firebaseUser = FirebaseUser)
    }

    override fun onFacebookLoginClick() {
    }

    override fun onGoogleLoginClick() {
    }

    override fun onServerLoginClick(email: String, password: String) {
    }

    /**
     * Logs in the user to the application, if it is successful, the user will be logged in to
     * the application and the credentials will be updated
     * @param loginStatus True/False
     * @param firebaseUser to retrieve their credentials
     * */
    fun loginUserCallback(loginStatus : Boolean, firebaseUser: FirebaseUser, loggedInMode: LoggedInMode) : Unit{
        // if login is successful
        // update user information and log them into the application
        if(loginStatus){
            mDataManager.updateUserInfo(
                    accessToken = firebaseUser.uid,
                    userId = firebaseUser.uid.toLong(),
                    loggedInMode = loggedInMode,
                    userName = firebaseUser.displayName,
                    email = firebaseUser.email,
                    profilePicPath = firebaseUser.photoUrl.toString()
            )

            baseView?.openMainActivity()
        }else{
            // display error to user
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}
