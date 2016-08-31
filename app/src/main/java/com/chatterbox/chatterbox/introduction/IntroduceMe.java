package com.chatterbox.chatterbox.introduction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chatterbox.chatterbox.login_signup.LogSignActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.introduction
 * Created by lusinabrian on 31/08/16 at 14:47
 * <p/>
 * Description: App introduction
 */
public class IntroduceMe extends AppIntro{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));


        // SHOW or HIDE the statusbar
        showStatusBar(true);

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        /*animation*/
        setZoomAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent openLogin = new Intent(IntroduceMe.this, LogSignActivity.class);
        startActivity(openLogin);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent openLogin = new Intent(IntroduceMe.this, LogSignActivity.class);
        startActivity(openLogin);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
