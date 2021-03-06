package com.chatterbox.chatterbox.app;

import android.app.Application;
import com.chatterbox.chatterbox.BuildConfig;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.data.DataManager;
import com.chatterbox.chatterbox.di.components.AppComponent;
import com.chatterbox.chatterbox.di.components.DaggerAppComponent;
import com.chatterbox.chatterbox.di.modules.AppModule;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.Twitter;
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

    @Inject
    Crashlytics crashlytics;

    @Inject
    Answers answers;

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    FirebaseUser firebaseUser;

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
        Fabric.with(this, crashlytics);
        /*initialize Answers*/
        Fabric.with(this, answers);

        // initialize AuthConfig
        //Fabric.with(this, new Twitter(authConfig));
    }
}
