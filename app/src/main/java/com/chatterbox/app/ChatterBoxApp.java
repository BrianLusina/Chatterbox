package com.chatterbox.app;

import android.app.Application;
import com.chatterbox.BuildConfig;
import com.chatterbox.R;
import com.chatterbox.data.DataManager;
import com.chatterbox.di.components.AppComponent;
import com.chatterbox.di.components.DaggerAppComponent;
import com.chatterbox.di.modules.AppModule;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import javax.inject.Inject;
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
    Crashlytics crashlytics;

    @Inject
    Answers answers;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();

        mAppComponent.inject(this);

        // installCustomCrash();

        // report to Fabric
        Fabric.with(this, crashlytics);
        //initialize Answers
        Fabric.with(this, answers);
    }

    public AppComponent getComponent(){
        return mAppComponent;
    }

    public void setComponent(AppComponent mAppComponent){
        this.mAppComponent = mAppComponent;
    }
}
