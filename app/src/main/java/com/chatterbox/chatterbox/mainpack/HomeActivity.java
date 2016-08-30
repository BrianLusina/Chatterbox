package com.chatterbox.chatterbox.mainpack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.SignInActivity;
import com.chatterbox.chatterbox.login_signup.LogSignActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.mainpack
 * Created by lusinabrian on 30/08/16 at 20:20
 */
public class HomeActivity extends AppCompatActivity{
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer Drawer = null;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private String mEmail;
    private SharedPreferences mSharedPreferences;

    private IProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity_layout);

        initViews();
        setSupportActionBar(toolbar);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LogSignActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mEmail = mFirebaseUser.getEmail();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        }

        /*set the user profile*/
        profile = new ProfileDrawerItem().withName(mUsername).withEmail(mEmail).withIcon(mPhotoUrl);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar_id);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.home_floating_action_btn);
    }
}
