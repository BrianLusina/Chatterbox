package com.chatterbox.chatterbox.di.modules;

import android.app.Application;
import android.content.Context;

import com.chatterbox.chatterbox.data.DataManager;
import com.chatterbox.chatterbox.data.DataManagerImpl;
import com.chatterbox.chatterbox.data.api.ApiHelper;
import com.chatterbox.chatterbox.data.api.ApiHelperImpl;
import com.chatterbox.chatterbox.di.ActivityContext;
import com.chatterbox.chatterbox.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Application Module to provide application specific dependencies. This will be used
 * to provide objects that will be available for injection in requesting classes
 * {@link com.chatterbox.chatterbox.di.components.AppComponent} will handle injecting them
 * into requesting classes. The methods annotated with {@link dagger.Provides} will be used
 * for injection
 */

@Module
public class AppModule {
    private final Application mApplication;

    public AppModule(Application mApplication){
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideApplicatonContext(){
        return mApplication;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(DataManagerImpl dataManager){
        return dataManager;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(ApiHelperImpl apiHelper){
        return apiHelper;
    }
}
