package com.chatterbox.chatterbox.login_signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.chatterbox.chatterbox.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 27/08/16 at 16:37
 * <p/>
 * Description:
 */
public class SignUpFragment extends Fragment {
    private EditText signUp_email, signUp_password;
    private Button registerbtn;
    private TextInputLayout signUp_emailtxtInptLayout, signUp_passwordTxtInptLayout;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup_layout, container, false);
        signUp_email = (EditText)rootView.findViewById(R.id.signup_email_id);
        signUp_password = (EditText)rootView.findViewById(R.id.signup_password_id);
        registerbtn = (Button) rootView.findViewById(R.id.signup_button_id);
        signUp_emailtxtInptLayout = (TextInputLayout)rootView.findViewById(R.id.useremail_txtinputlayout_id);
        signUp_passwordTxtInptLayout = (TextInputLayout)rootView.findViewById(R.id.userpassword_txtinputlayout_id);
        progressBar = (ProgressBar)rootView.findViewById(R.id.signup_progressBar_id);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
