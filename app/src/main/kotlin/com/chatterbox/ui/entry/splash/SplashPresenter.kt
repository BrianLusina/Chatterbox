package com.chatterbox.ui.entry.splash

import com.chatterbox.chatterbox.di.PerActivity
import com.chatterbox.ui.base.BasePresenter

/**
 * @author lusinabrian on 10/06/17.
 * @Notes
 */

@PerActivity
interface SplashPresenter<V : SplashView> : BasePresenter<V>{

    fun onViewInitialized() : Unit

}