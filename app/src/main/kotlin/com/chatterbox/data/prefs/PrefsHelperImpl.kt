package com.chatterbox.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.chatterbox.chatterbox.di.ApplicationContext
import com.chatterbox.chatterbox.di.PreferenceInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */

@Singleton
class PrefsHelperImpl

@Inject
constructor(@ApplicationContext context: Context, @PreferenceInfo prefFilename: String):
        PrefsHelper{

    companion object{
        // key used to determine if this is the first start of the application
        private val PREF_KEY_FIRST_START = "PREF_KEY_FIRST_START"
    }

    private val sharedPrefs : SharedPreferences = context.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)

    override fun getFirstStart(): Boolean {
        return sharedPrefs.getBoolean(PREF_KEY_FIRST_START, true)
    }

    override fun setFirstStart(firstStart: Boolean) {
        sharedPrefs.edit().putBoolean(PREF_KEY_FIRST_START, firstStart).apply()
    }
}