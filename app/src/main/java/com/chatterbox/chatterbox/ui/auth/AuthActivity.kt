package com.chatterbox.chatterbox.ui.auth

import android.os.Bundle
import com.chatterbox.chatterbox.R
import com.chatterbox.chatterbox.ui.base.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        activityComponent.inject(this)

        setUp()
    }

    override fun setUp() {
    }

}
