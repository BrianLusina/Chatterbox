package com.chatterbox.chatterbox.login_signup;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;
import com.chatterbox.chatterbox.R;
import com.crashlytics.android.Crashlytics;
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
        setContentView(R.layout.userlogin_activity);
        initViews();

        setSupportActionBar(toolbar);

        CustomActivityOnCrash.install(this);
        CustomActivityOnCrash.setEnableAppRestart(true);
        CustomActivityOnCrash.setRestartActivityClass(LogSignActivity.class);
        Fabric.with(this, new Crashlytics());

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
