package com.chatterbox.chatterbox.ui.auth.signup;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.ui.MainActivity;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 27/08/16 at 16:37
 * <p/>
 * Description:
 */
public class SignUpFragment extends Fragment{
    public static final String SIGNUPFRAGMENT_TAG = SignUpFragment.class.getSimpleName();
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
        signUp_emailtxtInptLayout = (TextInputLayout)rootView.findViewById(R.id.signup_emailtxtInput_id);
        signUp_passwordTxtInptLayout = (TextInputLayout)rootView.findViewById(R.id.signup_pass_txtInput_id);
        progressBar = (ProgressBar)rootView.findViewById(R.id.signup_progressBar_id);

        signUp_email.addTextChangedListener(new MyTextWatcher(signUp_email));
        signUp_password.addTextChangedListener(new MyTextWatcher((signUp_password)));
        //get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFormDetails();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    /**SEND email details if all data fields are valid*/
    /*todo: submit details to FIREBASE*/
    private void submitFormDetails() {
        if(!validateEmail() && validatePassword()){
            final SuperToast superToast = new SuperToast(getActivity());

            progressBar.setVisibility(View.VISIBLE);
            String password = signUp_password.getText().toString().trim();
            String email = signUp_email.getText().toString().trim();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            /*TODO: display a better message to user on sign up*/
                            Log.d(SIGNUPFRAGMENT_TAG, "Create User with Email"+ task.isSuccessful());
                            superToast.setText("Success! :)");
                            superToast.setDuration(Style.DURATION_SHORT);
                            superToast.show();
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                superToast.setText("Authentication failed. "+ task.getException());
                                superToast.setDuration(Style.DURATION_SHORT);
                                superToast.show();
                            } else {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                onDetach();
                            }
                        }
                    });
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
        }else if(password.length() < 6){
            signUp_passwordTxtInptLayout.setError(getString(R.string.err_msg_password_short));
            requestFocus(signUp_password);
        }else{
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
