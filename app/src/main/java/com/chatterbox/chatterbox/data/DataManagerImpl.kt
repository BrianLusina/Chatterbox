package com.chatterbox.chatterbox.data

import android.content.Context
import com.chatterbox.chatterbox.data.api.ApiHelper
import com.chatterbox.chatterbox.data.db.DbHelper
import com.chatterbox.chatterbox.data.prefs.PrefsHelper
import com.chatterbox.chatterbox.di.ApplicationContext
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.TwitterAuthCredential
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Implementation of [DataManager], Used to implement methods from all other interfaces
 * in the Model layer. Note that this simply interacts with the given interface methods and
 * DOES not access the classes themselves
 * This is a singleton which means that there will only be 1 instance of this class
 */

@Singleton
class DataManagerImpl
/**
 * Constructor for the DataMangerImpl. This will create a new object of this class with the
 * following params
 * @param mContext the application context to use when invoking this object
 * @param mDbHelper Interface to interact with the database layer of the application
 * @param mApiHelper Interface when interacting with the API for data fetching
 * @param mPreferenceHelper preference interface when interacting with the preference layer
 * of the application when storing data persistently in [android.content.SharedPreferences]
 */
@Inject
constructor(@param:ApplicationContext private val mContext: Context, private val mDbHelper: DbHelper, private val mPreferenceHelper: PrefsHelper, private val mApiHelper: ApiHelper): DataManager{

    override fun updateApiHeader(userId: Long?, accessToken: String) {

    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(
                accessToken = null,
                userId = null,
                loggedInMode = LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                userName = null,
                email = null,
                profilePicPath = null
        )
    }

    override fun updateUserInfo(accessToken: String?, userId: Long?, loggedInMode: LoggedInMode, userName: String?, email: String?, profilePicPath: String?) {
    }

    override fun getFirstStart(): Boolean {
        return mPreferenceHelper.getFirstStart()
    }

    override fun setFirstStart(firstStart: Boolean) {
        return mPreferenceHelper.setFirstStart(firstStart)
    }

    override fun doLoginWithTwitter(twitterAuthCredential: AuthCredential) : Pair<Boolean, FirebaseUser>{
        return mApiHelper.doLoginWithTwitter(twitterAuthCredential)
    }
}