package com.chatterbox.chatterbox.ui.auth

import com.chatterbox.chatterbox.data.DataManager
import com.chatterbox.chatterbox.ui.base.BasePresenter
import com.chatterbox.chatterbox.ui.base.BasePresenterImpl
import com.twitter.sdk.android.core.TwitterSession
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.AuthCredential
import android.icu.lang.UCharacter.GraphemeClusterBreak.V



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
        
    }

    override fun onFacebookLoginClick() {
    }

    override fun onGoogleLoginClick() {
    }

    override fun onServerLoginClick(email: String, password: String) {
    }

    override fun onDetach() {
        super.onDetach()
    }
}
