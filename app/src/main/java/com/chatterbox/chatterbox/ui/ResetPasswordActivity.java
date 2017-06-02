package com.chatterbox.chatterbox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.ui.auth.login.LogSignActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.login_signup
 * Created by lusinabrian on 28/08/16 at 19:24
 * <p/>
 * Description: Activity to Reset User password
 */
public class ResetPasswordActivity extends AppCompatActivity{
    private TextInputLayout txtInputLayout;
    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword_activity);

        inputEmail = (EditText) findViewById(R.id.resetpass_email_id);
        btnReset = (Button) findViewById(R.id.resetpass_resetbtn_id);
        btnBack = (Button) findViewById(R.id.resetpass_btnback_id);
        progressBar = (ProgressBar) findViewById(R.id.resetpassword_progressBar_id);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LogSignActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
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
            switch (view.getId()) {
                case R.id.useremail_id:
                    validateEmail();
                    break;
            }
        }

        /**
         * validate user email
         */
        private boolean validateEmail() {
            String email = inputEmail.getText().toString().trim();

            if (email.isEmpty() || isValidEmail(email)) {
                txtInputLayout.setError(getString(R.string.err_msg_email));
                requestFocus(inputEmail);
                return false;
            } else {
            /*TODO: send email to FIREBASE AUTH*/
                txtInputLayout.setErrorEnabled(false);
            }
            return true;
        }

        /**
         * checks for a valid email address from a pattern
         */
        private boolean isValidEmail(String email) {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }


        /**
         * sets the focus to the edit text with the error message
         */
        private void requestFocus(View view) {
            if (view.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }
    }
}
