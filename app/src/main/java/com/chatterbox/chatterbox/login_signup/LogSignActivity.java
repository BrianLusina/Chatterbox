package com.chatterbox.chatterbox.login_signup;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.chatterbox.chatterbox.R;
import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import io.fabric.sdk.android.Fabric;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 27/08/16 at 16:38
 * Description: Activity with signin and login fragments
 */
public class LogSignActivity extends AppCompatActivity{
    private MaterialViewPager materialViewPager;
    private Toolbar toolbar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin_activity);
        initViews();

        setSupportActionBar(toolbar);
        setUpViewPager(mViewPager);
        materialViewPager.getPagerTitleStrip().setViewPager(mViewPager);

        CustomActivityOnCrash.install(this);
        CustomActivityOnCrash.setEnableAppRestart(true);
        CustomActivityOnCrash.setRestartActivityClass(LogSignActivity.class);
        Fabric.with(this, new Crashlytics());

        //TODO: Set to false when publishing app
        CustomActivityOnCrash.setShowErrorDetails(true);
    }
    /**Initialize the UI contols*/
    private void initViews() {
        materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mViewPager = materialViewPager.getViewPager();
        toolbar = materialViewPager.getToolbar();

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }

        //TODO:SET MATERIALVIEWPAGER Listener for header animations
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page){
                    case 0:

                        break;
                    case 1:
                        break;
                }
                return null;
            }
        });
    }

    /**Defines the number of tabs by setting appropriate fragment and tab name.*/
    private void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(), "Login");
        viewPagerAdapter.addFragment(new SignUpFragment(), "Sign Up");
        mViewPager.setAdapter(viewPagerAdapter);
    }
}
