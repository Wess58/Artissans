package com.wess58.artissans.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wess58.artissans.Network;
import com.wess58.artissans.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.Email) EditText mEmail;
    @BindView(R.id.password1) EditText mPassword;
    @BindView(R.id.button) Button mLoginButton;
    @BindView(R.id.checkBox) CheckBox mStayLoggedin;
    @BindView(R.id.loginText) TextView mLoginTextView;
    @BindView(R.id.signUpLink) TextView mSignUpLink;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;



    @Override
    public  void onBackPressed(){
        moveTaskToBack(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        createAuthProgressDialog();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LogInActivity.this, NewsActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        };


        //<--- CHECKING INTERNET CONNECTION START
        if(Network.isInternetAvailable(LogInActivity.this))
            //returns true if internet available
        {

        }
        else
        {
            Toast.makeText(LogInActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }

        //CHECKING INTERNET CONNECTION END --->




        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mLoginTextView.setTypeface( oldEnglishFonts);

        mLoginButton.setOnClickListener(this);
        mSignUpLink.setOnClickListener(this);
    }

    //<--- PROGRESSDIALOG START
    //setCancelable() to "false" so that users cannot close the dialog manually.
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading ...");
        mAuthProgressDialog.setMessage("Authenticating in Progress...");
        mAuthProgressDialog.setCancelable(false);

    }
    //PROGRESSDIALOG END --->

    //<---VALIDATE FORMS START
    private void loginWithPassword() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LogInActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            mPassword.setError("Password cannot be blank");
            return;
        }
    }

    //VALIDATE FORM END --->

    @Override
    public void onClick(View v) {
        if(v == mSignUpLink){
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);

        }

        if (v == mLoginButton){
            loginWithPassword();
            SignInUser();
        }

    }

    private void SignInUser() {

        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        mAuthProgressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LogInActivity.this, NewsActivity.class);
                            startActivity(intent);

                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogInActivity.this, "Invalid Credentials.",
                                    Toast.LENGTH_SHORT).show();

                            }
                        }
                });
        }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    }
