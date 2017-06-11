package com.chatterbox.chatterbox.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import butterknife.ButterKnife
import com.chatterbox.chatterbox.R
import android.widget.LinearLayout
import android.widget.TextView
import android.view.animation.AnimationUtils
import android.support.percent.PercentRelativeLayout
import android.view.View
import android.widget.ImageButton
import com.chatterbox.chatterbox.ui.HomeActivity
import com.chatterbox.chatterbox.ui.MainActivity
import com.chatterbox.chatterbox.ui.base.BaseActivity
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.SignInButton
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.TwitterException
import android.R.attr.data
import com.twitter.sdk.android.core.Result


class AuthActivity : BaseActivity(), AuthView, View.OnClickListener {
    private var isLoginScreen = true
    private lateinit var registerInvokerTxtView: TextView
    private lateinit var registerLayout: LinearLayout
    private lateinit var loginInvokerTxtView: TextView
    private lateinit var loginLayout: LinearLayout
    private lateinit var registerBtn: Button
    private lateinit var loginBtn: Button

    private lateinit var fbImageBtn : ImageButton
    private lateinit var fbLoginButton : LoginButton

    private lateinit var twitterImageBtn : ImageButton
    private lateinit var twitterLoginButton : TwitterLoginButton

    private lateinit var googleImageBtn : ImageButton
    private lateinit var googleSignInButton : SignInButton
    
    //@Inject
    lateinit var authPresenter: AuthPresenter<AuthView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        activityComponent.inject(this)

        setUnbinder(ButterKnife.bind(this))

        authPresenter.onAttach(this)

        setUp()
    }

    override fun setUp() {

        registerInvokerTxtView = findViewById(R.id.registerInvokerTxtView) as TextView
        loginInvokerTxtView = findViewById(R.id.loginInvokerTxtView) as TextView

        registerBtn = findViewById(R.id.registerBtn) as Button
        loginBtn = findViewById(R.id.loginBtn) as Button
        
        fbImageBtn = findViewById(R.id.auth_fbLogin_imageBtn) as ImageButton
        fbLoginButton =  findViewById(R.id.auth_facebook_login_button) as LoginButton
        
        twitterImageBtn = findViewById(R.id.auth_twitterLogin_imgBtn) as ImageButton
        twitterLoginButton = findViewById(R.id.auth_twitter_login_button) as TwitterLoginButton

        googleSignInButton = findViewById(R.id.auth_googleSign_in_button) as SignInButton
        
        registerLayout = findViewById(R.id.registerLayout) as LinearLayout
        loginLayout = findViewById(R.id.loginLayout) as LinearLayout

        twitterImageBtn.setOnClickListener(this)
        fbImageBtn.setOnClickListener(this)

        registerInvokerTxtView.setOnClickListener {
            isLoginScreen = false
            showRegisterForm()
        }

        loginInvokerTxtView.setOnClickListener {
            isLoginScreen = true
            showLoginForm()
        }

        showLoginForm()

        registerBtn.setOnClickListener {
            val clockwise = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_right_to_left)
            if (isLoginScreen)
                registerBtn.startAnimation(clockwise)
        }
    }

    private fun showRegisterForm() {
        val paramsLogin = loginLayout.layoutParams as PercentRelativeLayout.LayoutParams
        val infoLogin = paramsLogin.percentLayoutInfo
        infoLogin.widthPercent = 0.15f
        loginLayout.requestLayout()

        val paramsSignup = registerLayout.layoutParams as PercentRelativeLayout.LayoutParams
        val infoSignup = paramsSignup.percentLayoutInfo
        infoSignup.widthPercent = 0.85f
        registerLayout.requestLayout()

        registerInvokerTxtView.visibility = View.GONE
        loginInvokerTxtView.visibility = View.VISIBLE
        val translate = AnimationUtils.loadAnimation(applicationContext, R.anim.translate_right_to_left)
        registerLayout.startAnimation(translate)

        val clockwise = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_right_to_left)
        registerBtn.startAnimation(clockwise)

    }

    private fun showLoginForm() {
        val paramsLogin = loginLayout.layoutParams as PercentRelativeLayout.LayoutParams
        val infoLogin = paramsLogin.percentLayoutInfo
        infoLogin.widthPercent = 0.85f
        loginLayout.requestLayout()


        val paramsSignup = registerLayout.layoutParams as PercentRelativeLayout.LayoutParams
        val infoSignup = paramsSignup.percentLayoutInfo
        infoSignup.widthPercent = 0.15f
        registerLayout.requestLayout()

        val translate = AnimationUtils.loadAnimation(applicationContext, R.anim.translate_left_to_right)
        loginLayout.startAnimation(translate)

        registerInvokerTxtView.visibility = View.VISIBLE
        loginInvokerTxtView.visibility = View.GONE
        val clockwise = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_left_to_right)
        loginBtn.startAnimation(clockwise)
    }

    override fun openMainActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.auth_twitterLogin_imgBtn -> {

                twitterLoginButton.callback = object : Callback<TwitterSession>() {
                    override  fun success(result: Result<TwitterSession>) {
                        authPresenter.onTwitterLoginClick(result.data)
                    }

                    override fun failure(exception: TwitterException) {
                        // inform user of failure
                    }
                }
            }

            R.id.auth_fbLogin_imageBtn -> authPresenter.onFacebookLoginClick()
        }

    }
}
