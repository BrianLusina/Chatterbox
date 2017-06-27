package com.chatterbox.ui.auth

import com.chatterbox.ui.base.BaseView
import com.google.firebase.auth.FirebaseUser

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */
interface AuthView : BaseView {
    /**
     * Opens main Activity whe operation of authentication is successful
     * */
    fun openMainActivity() : Unit

    /**
     * Updates firebase user
     * */
    fun updateFirebaseUser(firebaseUser: FirebaseUser) : Unit

}