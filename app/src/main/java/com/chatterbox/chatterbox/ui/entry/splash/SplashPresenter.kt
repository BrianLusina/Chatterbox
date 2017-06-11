package com.chatterbox.chatterbox.ui.entry.splash

import com.chatterbox.chatterbox.di.PerActivity
import com.chatterbox.chatterbox.ui.base.BasePresenter
import com.chatterbox.chatterbox.ui.base.BaseView

/**
 * @author lusinabrian on 10/06/17.
 * @Notes
 */

@PerActivity
interface SplashPresenter<V : SplashView> : BasePresenter<V>{

    fun onViewInitialized() : Unit

}