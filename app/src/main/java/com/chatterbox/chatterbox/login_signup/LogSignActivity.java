package com.chatterbox.chatterbox.login_signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;
import com.chatterbox.chatterbox.Constants;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.introduction.IntroduceMe;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import io.fabric.sdk.android.Fabric;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 27/08/16 at 16:38
 * Description: Activity with signin and login fragments
 */
public class LogSignActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configure Twitter SDK
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_CONSUMER_KEY, Constants.TWITTER_CONSUMER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.userlogin_activity);
        initViews();

        setSupportActionBar(toolbar);

        /*LAUNCHES APP INTRO*/
        Thread thread = new Thread(){
            /**
             * Calls the <code>run()</code> method of the Runnable object the receiver
             * holds. If no Runnable is set, does nothing.
             * @see Thread#start
             */
            @Override
            public void run() {
                super.run();
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                //create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firststart", true);

                //if activity has never started before
                if(isFirstStart){
                    //launch this activity
                    Intent intent = new Intent(LogSignActivity.this, IntroduceMe.class);
                    startActivity(intent);

                    //make a new shared preferences editor
                    SharedPreferences.Editor editor = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    editor.putBoolean("firstStart", false);

                    //apply the changes
                    editor.apply();
                }
            }
        };
        thread.start();

        CustomActivityOnCrash.install(this);
        CustomActivityOnCrash.setEnableAppRestart(true);
        CustomActivityOnCrash.setRestartActivityClass(LogSignActivity.class);
        Fabric.with(this, new Crashlytics());
        /*initialize Answers*/
        Fabric.with(this, new Answers());

        //TODO: Set to false when publishing app
        CustomActivityOnCrash.setShowErrorDetails(true);
    }
    /**Initialize the UI contols*/
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.userlogin_viewpager_id);
        toolbar = (Toolbar)findViewById(R.id.main_toolbar_id);

        /*SETS THE viewpager adapter*/
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(LoginFragment.newInstance(), "Login");
        viewPagerAdapter.addFragment(SignUpFragment.newInstance(), "Sign Up");
        mViewPager.setAdapter(viewPagerAdapter);

        //bind the tabs to the viewpager
        pagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.userlogin_pagerslidetabs_id);
        pagerSlidingTabStrip.setViewPager(mViewPager);

        //set the titles
        pagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
