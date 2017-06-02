package com.chatterbox.chatterbox.di.modules;

import android.app.Activity;
import android.content.Context;

import com.chatterbox.chatterbox.di.ActivityContext;

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
    CompositeDisposable provideCompositeDisposible(){
        return new CompositeDisposable();
    }
}
