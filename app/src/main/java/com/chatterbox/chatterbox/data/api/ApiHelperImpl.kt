package com.chatterbox.chatterbox.data.api

import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult



/**
 * @author lusinabrian on 02/06/17.
 * @Notes Deals with talking to apis and returning results back to application
 */

@Singleton
class ApiHelperImpl @Inject constructor(): ApiHelper{

    @Inject
    lateinit var firebaseAuth : FirebaseAuth

    @Inject
    lateinit var firebaseUser : FirebaseUser

    override fun doLoginWithTwitter(twitterAuthCredential: AuthCredential) : Pair<Boolean, FirebaseUser>{
        var loginSuccess : Boolean = false
        firebaseAuth.signInWithCredential(twitterAuthCredential).addOnCompleteListener(
                { task ->
                    if (task.isSuccessful) {
                        firebaseUser = firebaseAuth.currentUser!!
                        loginSuccess = true
                    } else {
                        loginSuccess = false
                    }
                })
        return Pair(loginSuccess, firebaseUser)
    }
}