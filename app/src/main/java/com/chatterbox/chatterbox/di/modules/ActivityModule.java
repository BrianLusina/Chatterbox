package com.chatterbox.chatterbox.di.modules;

import android.app.Activity;
import android.content.Context;

import com.chatterbox.chatterbox.di.ActivityContext;
import com.chatterbox.chatterbox.di.PerActivity;
import com.chatterbox.chatterbox.ui.auth.AuthPresenter;
import com.chatterbox.chatterbox.ui.auth.AuthPresenterImpl;
import com.chatterbox.chatterbox.ui.auth.AuthView;
import com.chatterbox.chatterbox.ui.entry.splash.SplashPresenter;
import com.chatterbox.chatterbox.ui.entry.splash.SplashPresenterImpl;
import com.chatterbox.chatterbox.ui.entry.splash.SplashView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Provides objects that will be used by activities and fragments
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity){
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }

    @Provides
    Activity provideActivity(){
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    SplashPresenter<SplashView> provideSplashPresenter(SplashPresenterImpl<SplashView> splashPresenter){
        return splashPresenter;
    }

    @Provides
    @PerActivity
    AuthPresenter<AuthView> provideAuthPresenter(AuthPresenterImpl<AuthView> authPresenter){
        return authPresenter;
    }
}
