package com.chatterbox.chatterbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 13/08/16 at 08:01
 * <p/>
 * Description:
 */
public class MainActivity extends AppCompatActivity{
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_layout);
    }

    /*CLASS END*/
}
