package com.chatterbox.chatterbox.di.components;

import com.chatterbox.chatterbox.di.PerService;
import com.chatterbox.chatterbox.di.modules.ServiceModule;

import dagger.Component;

/**
 * @author lusinabrian on 02/06/17.
 * @Notes connector between ServiceModule providing objects and classes requiring them
 */
@PerService
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    // todo:
    // inject services
}
