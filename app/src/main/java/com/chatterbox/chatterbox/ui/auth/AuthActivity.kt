package com.chatterbox.chatterbox.ui.auth

import android.os.Bundle
import android.widget.Button
import butterknife.ButterKnife
import com.chatterbox.chatterbox.R
import com.chatterbox.chatterbox.ui.base.BaseActivity
import android.widget.LinearLayout
import android.widget.TextView
import android.view.animation.AnimationUtils
import android.support.percent.PercentRelativeLayout
import android.view.View

class AuthActivity : BaseActivity(), AuthView {
    private var isLoginScreen = true
    private lateinit var registerInvokerTxtView: TextView
    private lateinit var registerLayout: LinearLayout
    private lateinit var loginInvokerTxtView: TextView
    private lateinit var loginLayout: LinearLayout
    private lateinit var registerBtn: Button
    private lateinit var loginBtn: Button

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

        registerInvokerTxtView = findViewById(R.id.tvSignupInvoker) as TextView
        loginInvokerTxtView = findViewById(R.id.tvSigninInvoker) as TextView

        registerBtn = findViewById(R.id.btnSignup) as Button
        loginBtn = findViewById(R.id.btnSignin) as Button

        registerLayout = findViewById(R.id.llSignUp) as LinearLayout
        loginLayout = findViewById(R.id.llLogin) as LinearLayout

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
}
