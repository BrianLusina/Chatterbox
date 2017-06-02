package com.chatterbox.chatterbox.ui.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import butterknife.Unbinder
import com.chatterbox.chatterbox.R
import com.chatterbox.chatterbox.app.ChatterBoxApp
import com.chatterbox.chatterbox.di.components.ActivityComponent
import com.chatterbox.chatterbox.di.modules.ActivityModule
import com.chatterbox.chatterbox.utils.CommonUtils
import com.chatterbox.chatterbox.utils.NetworkUtils

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Base activity from which other activities inherit from
 */

abstract class BaseActivity : AppCompatActivity(), BaseView, BaseFragment.Callback{

    private lateinit var mProgressDialog: ProgressDialog
    lateinit var activityComponent: ActivityComponent
    private var mUnbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        activityComponent = DaggerActivityComponent.builder()
//                .activityModule(ActivityModule(this))
//                .appComponent((application as ChatterBoxApp).getComponent())
//                .build()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * this binds the activity
     * @param mUnbinder the butterknife views
     */
    fun setUnbinder(mUnbinder: Unbinder) {
        this.mUnbinder = mUnbinder
    }
    /**
     * before destroying the view, do a sanity check of the view bindings before destroying
     * the activity */
    override fun onDestroy() {
        if (mUnbinder != null) {
            mUnbinder!!.unbind()
        }
        super.onDestroy()
    }

    /**
     * Abstract method that will be implemented by child activity classes
     * used to setup the views in the activity */
    protected abstract fun setUp()

    override fun showNetworkErrorSnackbar(message: String, length: Int) {
    }

    override fun showNetworkErrorSnackbar(message: Int, length: Int) {
    }

    override fun showLoadingProgress() {
        dismissLoadingProgress()
        mProgressDialog = CommonUtils.showProgressDialog(this)
        mProgressDialog.show()
    }

    override fun dismissLoadingProgress() {
        if(mProgressDialog.isShowing() && mProgressDialog != null){
            mProgressDialog.dismiss()
        }
    }

    override val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkAvailable(applicationContext)

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onErrorSnackBar(message: String, length: Int) {
        if (message != null) {
            showSnackbar(message, length)
        } else {
            showSnackbar(getString(R.string.snackbar_api_error), length)
        }
    }

    override fun onErrorSnackBar(resId: Int, length: Int) {
        onErrorSnackBar(getString(resId), length)
    }

    /**
     * Shows a [android.support.design.widget.Snackbar]
     * @param message the message to display in the snackbar
     * *
     * @param length how long to display this snackbar
     */
    private fun showSnackbar(message: String, length: Int) {
        val snackbar = Snackbar.make(findViewById(R.id.frame_container), message, length)
        val view = snackbar.view
        val textView = view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

//    override fun showNetworkErrorSnackbar(@StringRes resId: Int, length: Int) {
//        showNetworkErrorSnackbar(getString(resId), length)
//    }

//    override fun showNetworkErrorSnackbar(message: String, length: Int) {
//        val snackbar = Snackbar.make(findViewById(R.id.frame_container), message, length)
//        snackbar.setAction(R.string.reconnect_snackbar_action) {
//            registerReceiver(
//                    ConnChangeReceiver(),
//                    IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
//            )
//            startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
//        }
//
//        val sbView = snackbar.view
//        val textView = sbView.findViewById(android.support.design.R.id.snackbar_text) as TextView
//        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
//        snackbar.show()
//    }

    /**
     * Callback for when a fragment is attached to an activity
     */
    override fun onFragmentAttached() {

    }

    /**
     * Callback for when a fragment is detached from an activity

     * @param tag the fragment tag to detach
     */
    override fun onFragmentDetached(tag: String) {

    }
}