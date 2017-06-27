package com.chatterbox.di.components;

import com.chatterbox.chatterbox.di.PerActivity;
import com.chatterbox.chatterbox.di.modules.ActivityModule;
import com.chatterbox.ui.auth.AuthActivity;
import com.chatterbox.ui.entry.splash.SplashScreen;

import dagger.Component;

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashScreen splashScreen);

    void inject(AuthActivity authActivity);
}
