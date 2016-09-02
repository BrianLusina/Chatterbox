package com.chatterbox.chatterbox.mainpack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.chatterbox.chatterbox.drawerfragments.ChatsFragment;
import com.chatterbox.chatterbox.Constants;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.SignInActivity;
import com.chatterbox.chatterbox.login_signup.LogSignActivity;
import com.chatterbox.chatterbox.settings.SettingsActivity;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.mainpack
 * Created by lusinabrian on 30/08/16 at 20:20
 */
public class HomeActivity extends AppCompatActivity{
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private static final String HOMEACTIVITY_TAG = HomeActivity.class.getSimpleName();

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer drawer = null;
    // private MiniDrawer miniDrawer = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private Uri mPhotoUrl;
    private String mEmail;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAnalytics mFirebaseAnalytics;

    private SharedPreferences mSharedPreferences;

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
            mPhotoUrl = mFirebaseUser.getPhotoUrl();
        }

        /*user profile*/
        final IProfile user_profile = new ProfileDrawerItem().withName(mUsername).withEmail(mEmail).withIcon(mPhotoUrl);
        Log.i(HOMEACTIVITY_TAG,"Username: "+ mUsername + " Email: " + mEmail + " Photo Url: " + mPhotoUrl);

        /*Build the header*/
        headerResult  = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.home_drawer_header_view)
                .addProfiles(user_profile,
                        new ProfileSettingDrawerItem()
                                .withName("Manage Account")
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                                .withIdentifier(1)
/*                        new ProfileSettingDrawerItem()
                                .withName("Add Account")
                                .withDescription("Add new GitHub Account")
                                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar()
                                        .paddingDp(5)
                                        .colorRes(R.color.material_drawer_dark_primary_text))
                                .withIdentifier(Constants.PROFILE_SETTING)
                                */
                ).withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // Initialize Firebase Measurement.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize Firebase Remote Config.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("friendly_msg_length", 10L);

        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);

        // Fetch remote config.
        fetchConfig();

        crossfadeDrawerLayout = new CrossfadeDrawerLayout(this);
        // create the account drawer
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
//                .withDrawerLayout(R.layout.home_crossfade_drawer)
//                .withGenerateMiniDrawer(true)
//                .withDrawerWidthDp(72)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Chats").withIcon(FontAwesome.Icon.faw_smile_o).withTag("Home").withIdentifier(0),

                        new PrimaryDrawerItem().withName("Friends").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_account).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Friends").withIdentifier(1),

                        new PrimaryDrawerItem().withName("Notifications").withIcon(FontAwesome.Icon.faw_bell).withTag("Notifications").withIdentifier(2),

                        new ExpandableDrawerItem().withName("My Rooms").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus_box).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withTag("Rooms").withSelectable(false).withSubItems(
                            new SecondaryDrawerItem().withName("Rooms").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(3000),
                            new SecondaryDrawerItem().withName("Add Room").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(3001)
                        ),

                        new DividerDrawerItem(),

                        new SecondaryDrawerItem().withName("Help").withSelectedIconColor(Color.RED).withIconTintingEnabled(true).withIcon(FontAwesome.Icon.faw_question_circle).withTag("Help").withIdentifier(4),
                        new SecondaryDrawerItem().withName("Open Source").withSelectedIconColor(Color.RED).withIconTintingEnabled(true).withIcon(FontAwesome.Icon.faw_github).withTag("Open Source").withIdentifier(5)
                ).addStickyDrawerItems(
                    new SecondaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_cogs).withIdentifier(6),
                    new SecondaryDrawerItem().withName("About").withIcon(FontAwesome.Icon.faw_exclamation).withIdentifier(7)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem != null){
                            if(drawerItem instanceof Nameable){
                                String name = ((Nameable) drawerItem).getName().getText(HomeActivity.this);
                                Fragment fragment = null;
                                String title = name;
                                switch ((int) drawerItem.getIdentifier()){
                                /*chat fragment*/
                                    case 0:
                                        fragment = ChatsFragment.newInstance();
                                        title = getString(R.string.title_chats);
                                        break;

                                    /*TODO: notifications*/
                                    case 1:

                                        break;
                                    /*TODO: display rooms*/
                                    case 3000:

                                        break;

                                    /*todo: add room dialog*/
                                    case 3001:

                                        break;
                                    /*todo: help screen*/
                                    case 4:

                                        break;

                                    /*TODO: display open source*/
                                    case 5:
                                        new LibsBuilder().withActivityStyle(Libs.ActivityStyle
                                                .LIGHT_DARK_TOOLBAR)
                                                .withAboutAppName(getString(R.string.app_name))
                                                .withAboutVersionShown(true)
                                                .withAboutDescription(getString(R.string.app_about_desc))
                                                .start(getApplicationContext());
                                        break;

                                    /*settings*/
                                    case 6:
                                        Intent openSettings = new Intent(HomeActivity.this, SettingsActivity.class);
                                        startActivity(openSettings);
                                        break;

                                    /*About*/
                                    case 7:

                                        break;
                                    default:
                                        fragment = ChatsFragment.newInstance();
                                        title = getString(R.string.title_chats);
                                        break;
                                }
                                /**swap the fragments appropriately*/
                                if(fragment != null){
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container_body, fragment);
                                    fragmentTransaction.commit();
                                    getSupportActionBar().setTitle(title);
                                }
                            }
                        }

                        //we do not consume the event and want the Drawer to continue with the event chain
                        return false;
                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                .build();

        //set the default screen to FvHome
        Fragment fragment = ChatsFragment.newInstance();

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }

        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        /*crossfadeDrawerLayout = (CrossfadeDrawerLayout) drawer.getDrawerLayout();

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
        );*/

        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
/*        crossfadeDrawerLayout.getSmallView()
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
        });*/

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(HOMEACTIVITY_TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == Constants.REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Use Firebase Measurement to log that invitation was sent.
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_sent");

                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.d(HOMEACTIVITY_TAG, "Invitations sent: " + ids.length);
            } else {
                // Use Firebase Measurement to log that invitation was not sent
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_not_sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, payload);

                // Sending failed or it was canceled, show failure message to the user
                Log.d(HOMEACTIVITY_TAG, "Failed to send invitation.");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.invite_menu:
                sendInvitation();
                return true;
            case R.id.crash_menu:
                FirebaseCrash.logcat(Log.ERROR, HOMEACTIVITY_TAG, "crash caused");
                causeCrash();
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mFirebaseUser = null;
                mUsername = Constants.ANONYMOUS;
                mPhotoUrl = null;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.fresh_config_menu:
                fetchConfig();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*cause a fake crash*/
    private void causeCrash() {
        throw new NullPointerException("Fake null pointer exception");
    }

    /*send app invite*/
    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, Constants.REQUEST_INVITE);
    }

    /**Fetch the config to determine the allowed length of messages.*/
    public void fetchConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that each fetch goes to the
        // server. This should not be used in release builds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Make the fetched config available via FirebaseRemoteConfig get<type> calls.
                        mFirebaseRemoteConfig.activateFetched();
                        applyRetrievedLengthLimit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // There has been an error fetching the config
                        Log.w(HOMEACTIVITY_TAG, "Error fetching config: " + e.getMessage());
                        applyRetrievedLengthLimit();
                    }
                });
    }

    /**
     * Apply retrieved length limit to edit text field. This result may be fresh from the server or it may be from cached values.
     */
    private void applyRetrievedLengthLimit() {
        Long friendly_msg_length = mFirebaseRemoteConfig.getLong("friendly_msg_length");
//        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(friendly_msg_length.intValue())});
//        Log.d(HOMEACTIVITY_TAG, "FML is: " + friendly_msg_length);
    }

}
