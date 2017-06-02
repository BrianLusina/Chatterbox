package com.chatterbox.chatterbox.di.components;

import com.chatterbox.chatterbox.di.PerActivity;
import com.chatterbox.chatterbox.di.modules.ActivityModule;
import com.chatterbox.chatterbox.ui.auth.AuthActivity;
import com.chatterbox.chatterbox.ui.introduction.SplashScreen;

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
