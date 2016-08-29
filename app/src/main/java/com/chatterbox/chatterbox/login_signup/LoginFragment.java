package com.chatterbox.chatterbox.login_signup;

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
import com.chatterbox.chatterbox.MainActivity;
import com.chatterbox.chatterbox.R;
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
    private static final String LOGINFRAGMENT_TAG = LoginFragment.class.getSimpleName();

    /*FIELDS*/
    private SignInButton mSignInButton_google;
    //private TwitterLoginButton twitterLoginButton;
    private AutoCompleteTextView mEmail;
    private EditText passwordField;
    private TextInputLayout mEmailTextInputLayout, mPasswordTxtInputLayout;
    private Button loginBtn, login_reset_btb;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mFirebaseAuth;
    //responds to changes in user's sign in state
    private FirebaseAuth.AuthStateListener mAuthListener;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "piwheAPcw5HJq2ShHJrrIOzWA";
    private static final String TWITTER_SECRET = "c4y9c29daJEhgoSUzOpvE7egeMOPXg6UsULQ0n0DyP1jQ0cd4r";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureGoogleSignIn();

        /*instantiate Firebase*/
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    Log.d(LOGINFRAGMENT_TAG, "onAuthStateChanged:signed_in: " + user.getUid());
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

        /*
        twitterLoginButton = (TwitterLoginButton)findViewById(R.id.twitter_login_button);
        twitterLoginButton.setOnClickListener(this);
        */

        mSignInButton_google.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        login_reset_btb.setOnClickListener(this);
        mEmail.addTextChangedListener(new MyTextWatcher(mEmail));
        passwordField.addTextChangedListener(new MyTextWatcher(passwordField));
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
                signIn_google();
                break;

            case R.id.login_btn_id:
                submitEmailDetails();
                break;

            case R.id.login_reset_btn_id:
                resetPassword();
                break;
/*            case R.id.twitter_login_button:
                //sign in with Twitter
//                siginInTwitter();
                break;*/
        }
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
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    //TODO:Change display messages to the user
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(LOGINFRAGMENT_TAG, "SignInWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(LOGINFRAGMENT_TAG, "signInWithEmail:failed", task.getException());
                                superToast.setText("Authentication failed. "+ task.getException());
                                superToast.setDuration(Style.DURATION_SHORT);
                                superToast.show();
                            } else {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                onDetach();
                            }
                        }
                    });
        }

    }

    /**Method that signs in the user*/
    private void signIn_google(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /*sign in with TWITTER*/
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
            //TODO: SEND password to FIREBASEAUTH
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
