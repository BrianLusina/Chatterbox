package com.chatterbox.chatterbox.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.chatterbox.chatterbox.MainActivity;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.SplashScreen;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 27/08/16 at 16:37
 * Description:
 */
public class LoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    /*FIELDS*/
    private SignInButton mSignInButton;
    //    private TwitterLoginButton twitterLoginButton;
    private AutoCompleteTextView mEmail;
    private EditText passwordField;
    private Button loginBtn;

    private static final String LOGINFRAGMENT_TAG = LoginFragment.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mFirebaseAuth;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "piwheAPcw5HJq2ShHJrrIOzWA";
    private static final String TWITTER_SECRET = "c4y9c29daJEhgoSUzOpvE7egeMOPXg6UsULQ0n0DyP1jQ0cd4r";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureGoogleSignIn();

        /*instantiate Firebase*/
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signin_layout, container, false);
        initUICtrls(rootView);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**initialize the user controls and set events*/
    public void initUICtrls(View rootView){
        mSignInButton = (SignInButton) rootView.findViewById(R.id.sign_in_button);
/*
        twitterLoginButton = (TwitterLoginButton)findViewById(R.id.twitter_login_button);
        twitterLoginButton.setOnClickListener(this);
*/
        mSignInButton.setOnClickListener(this);

        mEmail = (AutoCompleteTextView) rootView.findViewById(R.id.useremail_id);
        passwordField = (EditText) rootView.findViewById(R.id.userpassword_id);
        loginBtn = (Button) rootView.findViewById(R.id.login_btn_id);
    }

    /*configures Google Sign in*/
    public void configureGoogleSignIn(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    public void handleFirebaseAuthResult(AuthResult authResult){
        if(authResult != null){
            //welcome the user
            FirebaseUser firebaseUser = authResult.getUser();
            SuperToast superToast = new SuperToast(getActivity());
            superToast.setDuration(Style.DURATION_SHORT);
            superToast.setAnimations(Style.ANIMATIONS_FLY);
            superToast.setText("Welcome," + firebaseUser.getEmail());
            superToast.show();

            //go back to main activity
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_button:
                //sign in the user
                signIn();
                break;

/*            case R.id.twitter_login_button:
                //sign in with Twitter
//                siginInTwitter();
                break;*/
        }
    }

    /**Method that signs in the user*/
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signInTwitter(){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(LOGINFRAGMENT_TAG, "Google Sign In failed.");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(LOGINFRAGMENT_TAG, "firebaseAuthWithGooogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOGINFRAGMENT_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(LOGINFRAGMENT_TAG, "signInWithCredential", task.getException());
                            SuperToast superToast = new SuperToast(getActivity());
                            superToast.setDuration(Style.DURATION_SHORT);
                            superToast.setAnimations(Style.ANIMATIONS_FLY);
                            superToast.setText("Authentication failed");
                            superToast.show();
                        } else {
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            onDetach();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(LOGINFRAGMENT_TAG, "onConnectionFailed:" + connectionResult);
        SuperToast superToast = new SuperToast(getActivity());
        superToast.setDuration(Style.DURATION_SHORT);
        superToast.setAnimations(Style.ANIMATIONS_FLY);
        superToast.setText("Google Play Services error.");
        superToast.show();
    }
}
