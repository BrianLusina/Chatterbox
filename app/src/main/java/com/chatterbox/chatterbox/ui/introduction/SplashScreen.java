package com.chatterbox.chatterbox.ui.introduction;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.ui.login.LogSignActivity;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 13/08/16 at 07:47
 * Description: Handles the SplashScreen Activity, which is the first thing to open when app starts
 */
public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    private final String SPLASHSCREEN_TAG = SplashScreen.class.getSimpleName();
    private Animation animFadeIn;
    private TextView appName, appTag;
    private ImageView appIcon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*request full screen*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen_layout);
        initUICtrls();
        //load animations
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        //add animation listener
        animFadeIn.setAnimationListener(this);

        /*Sets the timer*/
        Thread timer = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3000);
                    /*open sign in screen*/
                    Intent openMain = new Intent(SplashScreen.this, LogSignActivity.class);
                    startActivity(openMain);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                    Log.d(ie.toString(),SPLASHSCREEN_TAG);
                }
            }
        };
        timer.start();
    }
    /**initialized the UI controls for this splash screen*/
    public void initUICtrls(){
        appIcon = (ImageView)findViewById(R.id.appicon_splash);
        appName = (TextView)findViewById(R.id.appname_splash);
        appTag = (TextView)findViewById(R.id.apptag_splash);
        //set the fonts
        String fontPath = "fonts/roboto_rediumitalic.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);
        appName.setTypeface(typeface);

        /*add animation to application tag*/
        appTag.setAnimation(animFadeIn);
    }
    /*kill this splash screen to save memory*/
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
/*CLASS END*/
}