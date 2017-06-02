package com.chatterbox.chatterbox.app

import android.app.Application
import com.chatterbox.chatterbox.data.DataManager
import com.chatterbox.chatterbox.di.components.AppComponent
import com.chatterbox.chatterbox.di.modules.AppModule
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import javax.inject.Inject
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.chatterbox.chatterbox.BuildConfig
import com.chatterbox.chatterbox.R
import com.chatterbox.chatterbox.ui.MainActivity
import com.chatterbox.chatterbox.R.mipmap.ic_launcher


/**
 * @author lusinabrian on 02/06/17.
 * @Notes ChatterBox app singleton
 */

class ChatterBoxApp : Application(){
    @Inject
    lateinit var mDataManager: DataManager

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

//        mAppComponent = DaggerAppComponent.builder()
//                .appModule(AppModule(this)).build()

        mAppComponent.inject(this)

        installCustomCrash()
    }

    fun getComponent(): AppComponent{
        return mAppComponent
    }

    fun setComponent(mAppComponent: AppComponent): Unit{
        this.mAppComponent = mAppComponent
    }

    /**
     * Install custom crash to remove ANRs, because no one likes them :D
     * This will not display a logcat in production/release builds only in debug builds
     */
    private fun installCustomCrash() {

        // do not start this error launcher when in the background
        CustomActivityOnCrash.setLaunchErrorActivityWhenInBackground(false)

        // set the default icon
        CustomActivityOnCrash.setDefaultErrorActivityDrawable(R.mipmap.ic_launcher)

        //setting the activity to start when the application crashes
        CustomActivityOnCrash.setRestartActivityClass(MainActivity::class.java)

        //install the Custom Activity on crash
        CustomActivityOnCrash.install(this)
        CustomActivityOnCrash.setShowErrorDetails(BuildConfig.DEBUG)
        CustomActivityOnCrash.setEnableAppRestart(true)

        // report to Fabric!
        Fabric.with(this, Crashlytics())
    }
}