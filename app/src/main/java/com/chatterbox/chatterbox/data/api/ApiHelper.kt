package com.chatterbox.chatterbox.data.api

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Interface that will allow communication with Api layer
 */
interface ApiHelper {

    /**
     * Logs in a user with Twitter
     * @param twitterAuthCredential TwitterAuthCredential used to get credentials From Twitter
     * which will then be passed on To firebase for authenticating user
     * */
    fun doLoginWithTwitter(twitterAuthCredential: AuthCredential) : Pair<Boolean, FirebaseUser>
}