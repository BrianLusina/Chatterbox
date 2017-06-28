package com.chatterbox.ui.auth

import com.chatterbox.R
import com.chatterbox.data.DataManager
import com.chatterbox.ui.base.BasePresenterImpl
import com.twitter.sdk.android.core.TwitterSession
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import com.google.firebase.auth.TwitterAuthProvider
import com.chatterbox.data.LoggedInMode
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.error


/**
 * @author lusinabrian on 02/06/17.
 * *
 * @Notes
 */

class AuthPresenterImpl<V : AuthView>

@Inject
constructor(mDataManager: DataManager, mCompositeDisposable: CompositeDisposable):
        BasePresenterImpl<V>(mDataManager, mCompositeDisposable), AuthPresenter<V>{

    override fun onAttach(mBaseView: V) {
        super.onAttach(mBaseView)
    }

    override fun onTwitterLoginClick(firebaseAuth: FirebaseAuth,twitterSession: TwitterSession) {

        val credential = TwitterAuthProvider.getCredential(
                twitterSession.authToken.token,
                twitterSession.authToken.secret)

        // get the login success and firebase user
        val (loginSuccess, FirebaseUser) = mDataManager.doLoginWithTwitter(firebaseAuth,credential)

        loginUserCallback(loginSuccess, loggedInMode = LoggedInMode.LOGGED_IN_MODE_TWITTER,
                firebaseUser = FirebaseUser)
    }

    override fun onFacebookLoginSuccess(firebaseAuth: FirebaseAuth, fbAccessToken: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(fbAccessToken!!.token)

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener({
                    if(it.isSuccessful){
                        val firebaseUser = firebaseAuth.currentUser!!

                        // update the user
                        mDataManager.updateFirebaseUser(firebaseUser)
                        mDataManager.updateUserInfo(
                                accessToken = fbAccessToken.token,
                                userId = firebaseUser.uid.toLong(),
                                loggedInMode = LoggedInMode.LOGGED_IN_MODE_FB,
                                userName = firebaseUser.displayName,
                                email = firebaseUser.email,
                                profilePicPath = firebaseUser.photoUrl.toString())

                        baseView.updateFirebaseUser(firebaseUser)
                        baseView.openMainActivity()
                    }else{
                        error("Unsuccessful login request ${it.exception}")
                        // display error to user
                        baseView.displayLoginError(R.string.login_error)
                    }
                })
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

            // update firebase user
            baseView.updateFirebaseUser(mDataManager.updateFirebaseUser(firebaseUser))

            // open main activity
            baseView.openMainActivity()
        }else{
            // display error to user
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}
