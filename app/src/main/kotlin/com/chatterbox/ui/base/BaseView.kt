package com.chatterbox.ui.base

import android.support.annotation.StringRes

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Views that want to act as view layer will and must inherit from this
 */
interface BaseView {

    /**
     * shows the connection error snackbar. If there is no connection to any internet
     * connection
     * @param message message to displaye
     * @param length how long to display this message
     */
    fun showNetworkErrorSnackbar(message: String, length: Int)

    fun showNetworkErrorSnackbar(@StringRes message: Int, length: Int)

    /**
     * Shows a loading progress dialog
     */
    fun showLoadingProgress()

    /**
     * Dismiss a loading progress dialog
     */
    fun dismissLoadingProgress()

    /**
     * Checks if there is network connected
     * Returns True if the device is connected to a network, false otherwise
     * @return [Boolean]
     */
    val isNetworkConnected: Boolean

    /**
     * Hides keyboard */
    fun hideKeyboard()

    /**
     * Shows a snackbar if an error has been encountered
     * @param length how long the snack bar should be displayed
     * *
     * @param message the message to display in the snackbar
     */
    fun onErrorSnackBar(message: String, length: Int)

    /**
     * Override of [.onErrorSnackBar] */
    fun onErrorSnackBar(@StringRes resId: Int, length: Int)
}
