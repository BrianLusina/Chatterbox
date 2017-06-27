package com.chatterbox.di.modules;

import android.app.Activity;
import android.content.Context;

import com.chatterbox.di.ActivityContext;
import com.chatterbox.di.PerActivity;
import com.chatterbox.ui.auth.AuthPresenter;
import com.chatterbox.ui.auth.AuthPresenterImpl;
import com.chatterbox.ui.auth.AuthView;
import com.chatterbox.ui.entry.splash.SplashPresenter;
import com.chatterbox.ui.entry.splash.SplashPresenterImpl;
import com.chatterbox.ui.entry.splash.SplashView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides
    FirebaseUser provideFirebaseUser(FirebaseAuth firebaseAuth) {
        return firebaseAuth.getCurrentUser();
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
