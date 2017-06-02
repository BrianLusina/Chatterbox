package com.chatterbox.chatterbox.ui.auth

import com.chatterbox.chatterbox.di.PerActivity
import com.chatterbox.chatterbox.ui.base.BasePresenter

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */

@PerActivity
interface AuthPresenter<V : AuthView> : BasePresenter<V> {
}