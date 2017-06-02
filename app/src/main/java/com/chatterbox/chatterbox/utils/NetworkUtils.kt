package com.chatterbox.chatterbox.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */
object NetworkUtils {
    /**
     * Method to check network availability
     * Using ConnectivityManager to check for IsNetwork Connection
     */
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

}