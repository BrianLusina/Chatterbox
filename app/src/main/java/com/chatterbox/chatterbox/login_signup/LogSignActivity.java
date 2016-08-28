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

        /*SETS THE viewpager adapter*/
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(), "Login");
        viewPagerAdapter.addFragment(new SignUpFragment(), "Sign Up");
        mViewPager.setAdapter(viewPagerAdapter);
        materialViewPager.getPagerTitleStrip().setViewPager(mViewPager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }

        //TODO:SET MATERIAL VIEWPAGER Listener for header animations
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page){
                    /*LOGIN*/
                    case 0:
                        /*return HeaderDesign.fromColorResAndDrawable(R.color.holo_blue,);*/
                        break;
                    /*SIGN UP*/
                    case 1:
                        break;
                    /*return HeaderDesign.fromColorResAndDrawable(R.color.accent_color,);*/
                }
                return null;
            }
        });
    }
}
