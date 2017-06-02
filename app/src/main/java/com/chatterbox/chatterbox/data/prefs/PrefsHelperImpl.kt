package com.chatterbox.chatterbox.data.prefs

import android.content.Context
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

}