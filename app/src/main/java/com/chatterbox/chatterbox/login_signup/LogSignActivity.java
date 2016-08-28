package com.chatterbox.chatterbox.login_signup;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin_activity);
        initViews();

        setSupportActionBar(toolbar);
        setUpViewPager(viewPager);

        //assigns the viewpager to TabLayout
        tabLayout.setupWithViewPager(viewPager);

        CustomActivityOnCrash.install(this);
        CustomActivityOnCrash.setEnableAppRestart(true);
        CustomActivityOnCrash.setRestartActivityClass(LogSignActivity.class);
        Fabric.with(this, new Crashlytics());

        //TODO: Set to false when publishing app
        CustomActivityOnCrash.setShowErrorDetails(true);
    }
    /**Initialize the UI contols*/
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.user_login_toolbar);
        tabLayout = (TabLayout)findViewById(R.id.user_login_tabs);
        viewPager = (ViewPager)findViewById(R.id.user_login_viewpager);
    }

    /**Defines the number of tabs by setting appropriate fragment and tab name.*/
    private void setUpViewPager(ViewPager viewPager){
       ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(), "Login");
        viewPagerAdapter.addFragment(new SignUpFragment(), "Sign Up");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
