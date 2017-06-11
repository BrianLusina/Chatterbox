package com.chatterbox.chatterbox.app;

import android.app.Application;
import com.chatterbox.chatterbox.BuildConfig;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.data.DataManager;
import com.chatterbox.chatterbox.di.components.AppComponent;
import com.chatterbox.chatterbox.di.components.DaggerAppComponent;
import com.chatterbox.chatterbox.di.modules.AppModule;
import com.chatterbox.chatterbox.ui.MainActivity;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import javax.inject.Inject;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import io.fabric.sdk.android.Fabric;

/**
 * @author lusinabrian on 10/06/17.
 * @Notes
 */

public class ChatterBoxApp extends Application{

    private AppComponent mAppComponent;

    @Inject
    DataManager mDataManager;

    @Inject
    TwitterAuthConfig authConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();

        mAppComponent.inject(this);

        installCustomCrash();
    }

    public AppComponent getComponent(){
        return mAppComponent;
    }

    public void setComponent(AppComponent mAppComponent){
        this.mAppComponent = mAppComponent;
    }

    /**
     * Install custom crash to remove ANRs, because no one likes them :D
     * This will not display a logcat in production/release builds only in debug builds
     */
    private void installCustomCrash() {

        // do not start this error launcher when in the background
        CustomActivityOnCrash.setLaunchErrorActivityWhenInBackground(false);

        // set the default icon
        CustomActivityOnCrash.setDefaultErrorActivityDrawable(R.mipmap.ic_launcher);

        // setting the activity to start when the application crashes
        // CustomActivityOnCrash.setRestartActivityClass(MainActivity.class);

        //install the Custom Activity on crash
        CustomActivityOnCrash.install(this);
        CustomActivityOnCrash.setShowErrorDetails(BuildConfig.DEBUG);
        CustomActivityOnCrash.setEnableAppRestart(true);

        // report to Fabric!
        Fabric.with(this, new Crashlytics());
        /*initialize Answers*/
        Fabric.with(this, new Answers());

        // initialize AuthConfig
        Fabric.with(this, new Twitter(authConfig));
    }
}
