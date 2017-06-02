package com.chatterbox.chatterbox.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import com.chatterbox.chatterbox.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */
object CommonUtils{


    @JvmStatic
    fun showProgressDialog(mContext: Context): ProgressDialog {
        val progressDialog = ProgressDialog(mContext)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog

    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    val timeStamp: String
        get() = SimpleDateFormat(Constants.TIMESTAMP_FORMAT, Locale.UK).format(Date())
}