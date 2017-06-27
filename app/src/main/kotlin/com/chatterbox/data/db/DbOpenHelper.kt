package com.chatterbox.data.db

import android.content.Context
import com.chatterbox.chatterbox.di.ApplicationContext
import com.chatterbox.chatterbox.di.DatabaseInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */
@Singleton
class DbOpenHelper @Inject
constructor(@ApplicationContext context: Context, @DatabaseInfo name: String) {

}