package com.chatterbox.data.prefs

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Interface for preferences layer
 */
interface PrefsHelper {

    /**
     * Gets the first start. if this is the first start of this application will return True
     * */
    fun getFirstStart(): Boolean

    /**
     * Sets the first start of the user on this application
     * */
    fun setFirstStart(firstStart: Boolean) : Unit
}