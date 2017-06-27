package com.chatterbox.data

/**
 * @author lusinabrian on 02/06/17.
 * @Notes This class will be used to determine how the user logged in
 * Easier to determine how the user logged in and act accordingly
 */
enum class LoggedInMode(val mType: Int) {

    LOGGED_IN_MODE_LOGGED_OUT(0),
    LOGGED_IN_MODE_GOOGLE(1),
    LOGGED_IN_MODE_FB(2),
    LOGGED_IN_MODE_TWITTER(2),
    LOGGED_IN_MODE_SERVER(3);

    fun getType() : Int{
        return mType
    }
}