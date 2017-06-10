package com.chatterbox.chatterbox.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.ui.HomeActivity;
import com.chatterbox.chatterbox.ui.MainActivity;
import com.chatterbox.chatterbox.ui.auth.ResetPasswordActivity;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 27/08/16 at 16:37
 * Description:
 */
public class LoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    public static final String LOGINFRAGMENT_TAG = LoginFragment.class.getSimpleName();
    private SignInButton mSignInButton_google;
    private TwitterLoginButton twitterLoginButton;
    private LoginButton facebookLoginBtn;
    private AutoCompleteTextView mEmail;
    private EditText passwordField;
    private TextInputLayout mEmailTextInputLayout, mPasswordTxtInputLayout;
    private Button loginBtn, login_reset_btb;
    private GoogleApiClient mGoogleApiClient;
    private TwitterApiClient mTwitterApiClient;
    private FirebaseAuth mFirebaseAuth;
    private LoginPresenterImpl loginPresenterImpl;

    //responds to changes in user's sign in state
    private FirebaseAuth.AuthStateListener mAuthListener;

    private CallbackManager callbackManager;

    /**Required empty public constructor*/
    public LoginFragment() {}

    public static Fragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureGoogleSignIn();
        //configureFacebookSignIn();

        /*instantiate Firebase*/
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("Uid", user.getUid());
                    userData.put("Email", user.getEmail());
                    userData.put("Display Name", user.getDisplayName());
                    userData.put("Photo Url", (user.getPhotoUrl().toString() != null) ? user.getPhotoUrl().toString() : "" );

                    Log.d(LOGINFRAGMENT_TAG, "onAuthStateChanged:signed_in: " + userData);
                    new Intent(getActivity(), HomeActivity.class);
                }else{
                    /*user is signed out*/
                    Log.d(LOGINFRAGMENT_TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        initUICtrls(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**initialize the user controls and set events*/
    public void initUICtrls(View rootView){
        mSignInButton_google = (SignInButton) rootView.findViewById(R.id.sign_in_button);
        mEmail = (AutoCompleteTextView) rootView.findViewById(R.id.useremail_id);
        passwordField = (EditText) rootView.findViewById(R.id.userpassword_id);
        loginBtn = (Button) rootView.findViewById(R.id.login_btn_id);
        login_reset_btb = (Button) rootView.findViewById(R.id.login_reset_btn_id);
        mEmailTextInputLayout = (TextInputLayout)rootView.findViewById(R.id.useremail_txtinputlayout_id);
        mPasswordTxtInputLayout = (TextInputLayout)rootView.findViewById(R.id.userpassword_txtinputlayout_id);
        twitterLoginButton = (TwitterLoginButton)rootView.findViewById(R.id.twitter_login_button);
        //facebookLoginBtn = (LoginButton)rootView.findViewById(R.id.facebook_login_button);
        //initalize FaceBook login
        //initializeFacebookLogin();

        //initialize Twitter login
        intializeTwitterLogin();

        /*Click listeners*/
        mSignInButton_google.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        login_reset_btb.setOnClickListener(this);
        mEmail.addTextChangedListener(new MyTextWatcher(mEmail));
        passwordField.addTextChangedListener(new MyTextWatcher(passwordField));
    }

    /**Initializes the Facebook login*/
    private void initializeFacebookLogin() {
        facebookLoginBtn.setReadPermissions(Arrays.asList("email", "public_profile"));
        facebookLoginBtn.setFragment(this);
        // Callback registration
        facebookLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(LOGINFRAGMENT_TAG, "FacebookLogin:success");
                firebaseWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(LOGINFRAGMENT_TAG, "FacebookLogin:cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(LOGINFRAGMENT_TAG, "facebook:onError", exception);
            }
        });
    }

    /**Sign in with Twitter*/
    private void intializeTwitterLogin(){
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            final SuperToast superToast = new SuperToast(getActivity());

            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                TwitterSession session = result.data;
                Log.d(LOGINFRAGMENT_TAG, "Twitter Login: success");
                //firebaseWithTwitter(session);
                loginPresenterImpl = new LoginPresenterImpl();
                // if the return is a success, start the next activity
                if(loginPresenterImpl.handleFirebaseWithTwitter(session, mFirebaseAuth, getActivity())){
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    onDetach();
                }else{
                    loginPresenterImpl.displayErrorMessage(true, getActivity());
                }
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d(LOGINFRAGMENT_TAG, "Twitter Login: failure");
                Log.d("TwitterKit", "Login with Twitter failure", exception);
                /*TODO: display snackbar*/
                superToast.setText("Twitter login failure");
                superToast.setDuration(Style.DURATION_SHORT);
                superToast.show();
                Crashlytics.logException(exception);
            }
        });
    }


    /**After a user successfully signs in with Google, exchange the OAuth access token and OAuth secret for a Firebase credential, and authenticate with Firebase using the Firebase credential*/
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
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            onDetach();
                        }
                    }
                });
    }

    /**After a user successfully signs in with Twitter, exchange the OAuth access token and OAuth secret for a Firebase credential, and authenticate with Firebase using the Firebase credential*/
    private void firebaseWithTwitter(TwitterSession session){
        Log.d(LOGINFRAGMENT_TAG, "Handle Twitter session: " + session);
        //TODO: show dialog
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOGINFRAGMENT_TAG, "Sign in with Credential: onComplete:" + task.isSuccessful());
                        /**if sign in is not successful, display message to user*/
                        if(!task.isSuccessful()){
                            Log.w(LOGINFRAGMENT_TAG, "Sign in with TwitterCredential", task.getException());
                            SuperToast superToast = new SuperToast(getActivity());
                            superToast.setDuration(Style.DURATION_SHORT);
                            superToast.setAnimations(Style.ANIMATIONS_FLY);
                            superToast.setText("Authentication failed, Please try again");
                            superToast.show();
                        }else{
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            onDetach();
                        }
                    }
                });
    }

    /**
     * If login succeeds, the LoginResult parameter has the new AccessToken, and the most recently granted or declined permissions.*/
    private void firebaseWithFacebook(AccessToken accessToken) {
        Log.d(LOGINFRAGMENT_TAG, "handleFacebookAccessToken:" + accessToken);
        //TODO: show progress

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOGINFRAGMENT_TAG, "signInWithCredential_Facebook:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(LOGINFRAGMENT_TAG, "signInWithCredential", task.getException());
                            SuperToast superToast = new SuperToast(getActivity());
                            superToast.setDuration(Style.DURATION_SHORT);
                            superToast.setAnimations(Style.ANIMATIONS_FLY);
                            superToast.setText("Authentication failed, Please try again");
                            superToast.show();
                        }
                    }
                });
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

    /**calls FacebookSdk.sdkInitialize to initialize the SDK, and then call CallbackManager.Factory.create to create a callback manager to handle login responses.*/
    private void configureFacebookSignIn() {
        callbackManager = CallbackManager.Factory.create();
        initializeFacebookLogin();
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
            /*sign in with Google*/
            case R.id.sign_in_button:
                signIn_google();
                break;
            /*sign in with email and password*/
            case R.id.login_btn_id:
                submitEmailDetails();
                break;
            /*reset password*/
            case R.id.login_reset_btn_id:
                resetPassword();
                break;
        }
    }

    /**Method that signs in the user*/
    private void signIn_google(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Contracts.RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == Contracts.RC_SIGN_IN){
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
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        /*todo:FACEBOOK login*/
        //callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**reset the user password*/
    private void resetPassword() {
        Intent openReset = new Intent(getActivity(), ResetPasswordActivity.class);
        startActivity(openReset);
    }

    /**SEND email details if all data fields are valid*/
    /*todo: submit details to FIREBASE*/
    private void submitEmailDetails() {
        if(!validateEmail() && validatePassword()){
            final SuperToast superToast = new SuperToast(getActivity());

            String email = mEmail.getText().toString().trim();
            String password = passwordField.getText().toString();
            Log.d(LOGINFRAGMENT_TAG, "Email: " + email + " Pass: " + password);
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    //TODO:Change display messages to the user
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(LOGINFRAGMENT_TAG, "SignInWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Log.w(LOGINFRAGMENT_TAG, "signInWithEmail:failed", task.getException());
                                superToast.setText("Authentication failed. "+ task.getException());
                                superToast.setDuration(Style.DURATION_SHORT);
                                superToast.show();
                            } else {
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(intent);
                                onDetach();
                            }
                        }
                    });
        }
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

    /**TEXT WATCHER, to check to user input in the edit text and autocomplete*/
    private class MyTextWatcher implements TextWatcher {
        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.useremail_id:
                    validateEmail();
                    break;

                case R.id.userpassword_id:
                    validatePassword();
                    break;
            }
        }
    }

    /**VALIDATE user password*/
    private boolean validatePassword() {
        String password = passwordField.getText().toString().trim();
        if(password.isEmpty()){
            mPasswordTxtInputLayout.setError(getString(R.string.err_msg_password));
            requestFocus(passwordField);
            return false;
        }else{
            mPasswordTxtInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**validate user email*/
    private boolean validateEmail() {
        String email = mEmail.getText().toString().trim();

        if(email.isEmpty() || !isValidEmail(email)){
            mEmailTextInputLayout.setError(getString(R.string.err_msg_email));
            requestFocus(mEmail);
            return false;
        }else{
            mEmailTextInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**checks for a valid email address from a pattern*/
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**sets the focus to the edit text with the error message*/
    private void requestFocus(View view) {
        if(view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}/*end*/
