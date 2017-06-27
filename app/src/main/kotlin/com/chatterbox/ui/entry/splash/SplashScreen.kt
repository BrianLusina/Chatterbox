package com.chatterbox.ui.entry.splash

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife

import com.chatterbox.chatterbox.R
import com.chatterbox.ui.auth.AuthActivity
import com.chatterbox.ui.base.BaseActivity
import com.chatterbox.ui.entry.IntroduceMe
import javax.inject.Inject

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 13/08/16 at 07:47
 * Description: Handles the SplashScreen Activity, which is the first thing to open when app starts
 */
class SplashScreen : BaseActivity(), SplashView {
    
    private lateinit var appName: TextView
    private lateinit var appTag: TextView
    private lateinit var appIcon: ImageView
    
    @Inject
    lateinit var splashPresenter : SplashPresenter<SplashView>

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splashscreen_layout)
        
        activityComponent.inject(this)
        
        setUnbinder(ButterKnife.bind(this))
        
        splashPresenter.onAttach(this)
        
        setUp()
    }

    override fun setUp() {
        appIcon = findViewById(R.id.appicon_splash) as ImageView
        appName = findViewById(R.id.appname_splash) as TextView
        appTag = findViewById(R.id.apptag_splash) as TextView

        splashPresenter.onViewInitialized()
    }

    override fun openAuthActivity() {
        val openMain = Intent(this, AuthActivity::class.java)
        startActivity(openMain)
    }

    override fun openIntroductionActivity() {
        val openMain = Intent(this, IntroduceMe::class.java)
        startActivity(openMain)
    }

    /*kill this splash screen to save memory*/
    override fun onPause() {
        super.onPause()
        finish()
    }
}