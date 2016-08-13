package com.chatterbox.chatterbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.SignInButton;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 13/08/16 at 08:13
 * <p/>
 * Description: sign in activity to authorize user to application
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private SignInButton mSignInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        initUICtrls();
    }

    /*initialize the user controls and set evnts*/
    public void initUICtrls(){
        mSignInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
/*CLASS END*/
}
