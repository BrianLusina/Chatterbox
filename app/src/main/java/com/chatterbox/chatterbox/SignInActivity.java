package com.chatterbox.chatterbox;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 13/08/16 at 08:13
 * <p/>
 * Description: sign in activity to authorize user to application
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    /*FIELDS*/
    private SignInButton mSignInButton;
    private static final String SIGNINACTIVITY_TAG = SplashScreen.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        initUICtrls();
        configureGoogleSignIn();

        /*instantiate Firebase*/
        mFirebase = FirebaseAuth.getInstance();
    }

    /*initialize the user controls and set evnts*/
    public void initUICtrls(){
        mSignInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
    }

    /*configures Google Sign in*/
    public void configureGoogleSignIn(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    public void handleFirebaseAuthResult(AuthResult authResult){
        if(authResult != null){
            //welcome the user
            FirebaseUser firebaseUser = authResult.getUser();
            SuperToast superToast = new SuperToast(this);
            superToast.setDuration(Style.DURATION_SHORT);
            superToast.setAnimations(Style.ANIMATIONS_FLY);
            superToast.setText("Welcome," + firebaseUser.getEmail());
            superToast.show();

            //go back to main activity
            startActivity(new Intent(this, MainActivity.class));
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_button:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(SIGNINACTIVITY_TAG, "onConnectionFailed:" + connectionResult);
        SuperToast superToast = new SuperToast(this);
        superToast.setDuration(Style.DURATION_SHORT);
        superToast.setAnimations(Style.ANIMATIONS_FLY);
        superToast.setText("Google Play Services error.");
        superToast.show();
    }
/*CLASS END*/
}
