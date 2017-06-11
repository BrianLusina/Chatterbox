package com.chatterbox.chatterbox.ui.auth

import com.chatterbox.chatterbox.ui.base.BaseView

/**
 * @author lusinabrian on 02/06/17.
 * @Notes
 */
interface AuthView : BaseView {
    /**
     * Opens main Activity whe operation of authentication is successful
     * */
    fun openMainActivity() : Unit

}