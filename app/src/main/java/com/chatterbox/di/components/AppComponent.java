package com.chatterbox.di.components;

import android.app.Application;
import android.content.Context;

import com.chatterbox.app.ChatterBoxApp;
import com.chatterbox.data.DataManager;
import com.chatterbox.chatterbox.di.ApplicationContext;
import com.chatterbox.chatterbox.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Application component that will provide access to objects returned in AppModule
 * methods
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(ChatterBoxApp chatterBoxApp);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();
}
