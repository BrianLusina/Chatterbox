package com.chatterbox.chatterbox.mainpack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.chatterbox.chatterbox.Constants;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.SignInActivity;
import com.chatterbox.chatterbox.login_signup.LogSignActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

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
    private Drawer drawer = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;

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

        /*create the account header*/
        buildHeader(false, savedInstanceState);

        // create the account drawer
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDrawerLayout(R.layout.home_crossfade_drawer)
                .withGenerateMiniDrawer(true)
                .withDrawerWidthDp(72)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Notifications").withIcon(FontAwesome.Icon.faw_bell).withTag("Notifications").withIdentifier(0),

                        new PrimaryDrawerItem().withName("Friends").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_account).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Friends").withIdentifier(1),

                        new PrimaryDrawerItem().withName("Rooms").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus_box).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Rooms").withIdentifier(2),

                        new PrimaryDrawerItem().withName("Images").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_image).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Images").withIdentifier(3),

                        /**/
                        new SectionDrawerItem().withName("Section"),

                        new SecondaryDrawerItem().withName("Help").withSelectedIconColor(Color.RED).withIconTintingEnabled(true).withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_help).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Help").withIdentifier(2)
                ).addStickyDrawerItems(
                    new SecondaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_cogs).withIdentifier(10),
                    new SecondaryDrawerItem().withName("About").withIcon(FontAwesome.Icon.faw_exclamation)
                )
                .withShowDrawerOnFirstLaunch(true)
                .withSavedInstance(savedInstanceState)
                .build();

        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        crossfadeDrawerLayout = (CrossfadeDrawerLayout)drawer.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));

        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = drawer.getMiniDrawer();

        //build the view for the MiniDrawer
        View view = miniResult.build(this);

        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(
                this,
                com.mikepenz.materialdrawer.R.attr.material_drawer_background,
                com.mikepenz.materialdrawer.R.color.material_drawer_background)
        );

        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView()
                .addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    drawer.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

    }

    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the header of the drawer with a compact/normal header
     *
     * @param b
     * @param savedInstanceState
     */
    private void buildHeader(boolean b, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.home_drawer_header_view)
                .withCompactStyle(b)
                .addProfiles(
                        profile,
                        new ProfileSettingDrawerItem()
                                .withName("Add Account")
                                .withDescription("Add new GitHub Account")
                                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar()
                                        .paddingDp(5)
                                        .colorRes(R.color.material_drawer_dark_primary_text))
                                .withIdentifier(Constants.PROFILE_SETTING),

                        new ProfileSettingDrawerItem()
                                .withName("Manage Account")
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile)
                                .getIdentifier() == Constants.PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem()
                                    .withNameShown(true)
                                    .withName(mUsername)
                                    .withEmail(mEmail)
                                    .withIcon(mPhotoUrl);
                            if (headerResult.getProfiles() != null) {
                              headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar_id);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.home_floating_action_btn);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
