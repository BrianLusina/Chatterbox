package com.chatterbox.chatterbox.ui.auth.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.chatterbox.chatterbox.BuildConfig;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.ui.entry.IntroduceMe;
import com.chatterbox.chatterbox.ui.adapters.ViewPagerAdapter;
import com.chatterbox.chatterbox.ui.auth.signup.SignUpFragment;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.facebook.CallbackManager;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import io.fabric.sdk.android.Fabric;

import static com.chatterbox.chatterbox.BuildConfig.TWITTER_CONSUMER_KEY;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 27/08/16 at 16:38
 * Description: Activity with signin and login fragments
 */
public class LogSignActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin_activity);
        initViews();
    }

    /**Initialize the UI contols*/
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.userlogin_viewpager_id);

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
