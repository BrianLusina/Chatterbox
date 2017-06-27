package com.chatterbox.data.api

import android.support.annotation.NonNull
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseUser


/**
 * @author lusinabrian on 02/06/17.
 * @Notes Deals with talking to apis and returning results back to application
 */

@Singleton
class ApiHelperImpl @Inject constructor(): ApiHelper{

    override fun doLoginWithTwitter(firebaseAuth: FirebaseAuth, twitterAuthCredential: AuthCredential) : Pair<Boolean, FirebaseUser>{
        var loginSuccess : Boolean = false
        var firebaseUser : FirebaseUser = firebaseAuth.currentUser as FirebaseUser

        firebaseAuth.signInWithCredential(twitterAuthCredential).addOnCompleteListener(
                { task ->
                    if (task.isSuccessful) {
                        firebaseUser = firebaseAuth.currentUser as FirebaseUser
                        loginSuccess = true
                    } else {
                        loginSuccess = false
                    }
                })

        return Pair(loginSuccess, firebaseUser)
    }
}