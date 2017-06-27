package com.chatterbox.ui.entry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chatterbox.chatterbox.R;
import com.chatterbox.ui.auth.AuthActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.views.introduction
 * Created by lusinabrian on 31/08/16 at 14:47
 * <p/>
 * Description: App introduction
 */
public class IntroduceMe extends AppIntro{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Just set a title, description, background and image
        String appTitle = getString(R.string.app_name);
        String description = getString(R.string.app_desc);

        String[] titles = new String[]{
                appTitle, "Create those rooms", "Have one on ones", "Of course, share share share"
        };

        String[] descriptions = new String[]{
                description, "Because rooms are awesome", "Because you want to be personal",getString(R.string.app_tag)
        };

        /*1st screen*/
        addSlide(AppIntroFragment.newInstance(titles[0], descriptions[0], R.drawable.chatterbox_logo, getResources().getColor(R.color.royal_blue)));

        /*2nd screen*/
        addSlide(AppIntroFragment.newInstance(titles[1], descriptions[1], R.drawable.chatterbox_logo, getResources().getColor(R.color.foreground1)));

        /*3rd screen*/
        addSlide(AppIntroFragment.newInstance(titles[2], descriptions[2], R.drawable.chatterbox_logo, getResources().getColor(R.color.holo_blue)));

        /*4th screen*/
        addSlide(AppIntroFragment.newInstance(titles[3], descriptions[3], R.drawable.chatterbox_logo, getResources().getColor(R.color.sky_blue)));

        // SHOW or HIDE the statusbar
        showStatusBar(true);

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        /*animation*/
        setZoomAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
