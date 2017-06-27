package com.chatterbox.ui.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.chatterbox.R;
import com.chatterbox.app.ChatterBoxApp;
import com.chatterbox.di.components.ActivityComponent;
import com.chatterbox.di.components.DaggerActivityComponent;
import com.chatterbox.di.modules.ActivityModule;
import com.chatterbox.utils.CommonUtils;
import com.chatterbox.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.Unbinder;

/**
 * @author lusinabrian on 10/06/17.
 * @Notes
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView, BaseFragment.Callback {

    private ActivityComponent activityComponent;
    private Unbinder unbinder;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((ChatterBoxApp) getApplication()).getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
    /**
     * this binds the activity
     * @param mUnbinder the butterknife views
     */
    public void setUnbinder(Unbinder mUnbinder) {
        this.unbinder = mUnbinder;
    }

    /**
     * before destroying the view, do a sanity check of the view bindings before destroying the
     * activity */
    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    /**
     * Abstract method that will be implemented by child activity classes
     * used to setup the views in the activity */
    protected abstract void setUp();

    @Override
    public void showNetworkErrorSnackbar(@NotNull String message, int length) {
//        @SuppressLint("WrongViewCast") Snackbar snackbar = Snackbar.make(findViewById(R.id.frame_container), message, length);
//        snackbar.setAction(R.string.reconnect_snackbar_action, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                registerReceiver(
//                        new ConnChangeReceiver(),
//                        new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
//                );
//                startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
//            }
//        });
//
//        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
//        snackbar.show();
    }

    @Override
    public void showNetworkErrorSnackbar(int message, int length) {
        showNetworkErrorSnackbar(getString(message), length);
    }

    @Override
    public void showLoadingProgress() {
        dismissLoadingProgress();
        progressDialog = CommonUtils.showProgressDialog(this);
        progressDialog.show();
    }

    @Override
    public void dismissLoadingProgress() {
        if(progressDialog.isShowing() && progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkAvailable(getApplicationContext());
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onErrorSnackBar(@NotNull String message, int length) {
        if (message != null) {
            showSnackbar(message, length);
        } else {
            showSnackbar(getString(R.string.snackbar_api_error), length);
        }
    }

    @Override
    public void onErrorSnackBar(int resId, int length) {
        onErrorSnackBar(getString(resId), length);
    }

    /**
     * Shows a [android.support.design.widget.Snackbar]
     * @param message the message to display in the snackbar
     * *
     * @param length how long to display this snackbar
     */
    private void showSnackbar(String message, int length) {
        @SuppressLint("WrongViewCast") Snackbar snackbar = Snackbar.make(findViewById(R.id.frame_container), message, length);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(@NotNull String tag) {

    }
}
