package com.chatterbox.chatterbox.login_signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
public class SignUpFragment extends Fragment implements View.OnClickListener{
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

        signUp_email.addTextChangedListener(new MyTextWatcher(signUp_email));
        signUp_password.addTextChangedListener(new MyTextWatcher((signUp_password)));
        //get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEmailDetails();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**SEND email details if all data fields are valid*/
    /*todo: submit details to FIREBASE*/
    private void submitEmailDetails() {
        if(!validateEmail()){
            return ;
        }
        if(validatePassword()){
            return ;
        }

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
        String password = signUp_password.getText().toString().trim();
        if(password.isEmpty()){
            signUp_passwordTxtInptLayout.setError(getString(R.string.err_msg_password));
            requestFocus(signUp_password);
            return false;
        }else{
            //TODO: SEND password to FIREBASEAUTH
            signUp_passwordTxtInptLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**validate user email*/
    private boolean validateEmail() {
        String email = signUp_email.getText().toString().trim();

        if(email.isEmpty() || isValidEmail(email)){
            signUp_emailtxtInptLayout.setError(getString(R.string.err_msg_email));
            requestFocus(signUp_email);
            return false;
        }else{
            /*TODO: send email to FIREBASE AUTH*/
            signUp_passwordTxtInptLayout.setErrorEnabled(false);
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

}
