package com.chatterbox.di.modules;

import android.app.Service;

import dagger.Module;

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Used to provide objects that will be used in the application
 * methods annotated with @Provides will be available for injection
 */

@Module
public class ServiceModule {

    private final Service service;

    public ServiceModule(Service service){
        this.service = service;
    }
}
