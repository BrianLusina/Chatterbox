package com.chatterbox.chatterbox.ui.entry.splash

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife

import com.chatterbox.chatterbox.R
import com.chatterbox.chatterbox.ui.auth.AuthActivity
import com.chatterbox.chatterbox.ui.auth.login.LogSignActivity
import com.chatterbox.chatterbox.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 13/08/16 at 07:47
 * Description: Handles the SplashScreen Activity, which is the first thing to open when app starts
 */
class SplashScreen : BaseActivity(), Animation.AnimationListener, SplashView {

    private val TAG = SplashScreen::class.java.simpleName
    
    private lateinit var animFadeIn: Animation
    private lateinit var appName: TextView
    private lateinit var appTag: TextView
    private lateinit var appIcon: ImageView
    
    @Inject
    lateinit var splashPresenter : SplashPresenter<SplashView>

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*request full screen*/
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

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

        //set the fonts
        val fontPath = "fonts/roboto_rediumitalic.ttf"
        val typeface = Typeface.createFromAsset(assets, fontPath)

        appName.typeface = typeface

        /*add animation to application tag*/
        appTag.animation = animFadeIn

        //load animations
        animFadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        //add animation listener
        animFadeIn.setAnimationListener(this)

        /*Sets the timer*/
        val timer = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                    /*open sign in screen*/
                    val openMain = Intent(this@SplashScreen, AuthActivity::class.java)
                    startActivity(openMain)
                } catch (ie: InterruptedException) {
                    ie.printStackTrace()
                    Log.d(ie.toString(), TAG)
                }

            }
        }
        timer.start()
    }
    
    /*kill this splash screen to save memory*/
    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onAnimationStart(animation: Animation) {

    }

    override fun onAnimationEnd(animation: Animation) {

    }

    override fun onAnimationRepeat(animation: Animation) {

    }

}