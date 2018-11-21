package com.wess58.artissans.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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





        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mLoginTextView.setTypeface( oldEnglishFonts);

        mLoginButton.setOnClickListener(this);
        mSignUpLink.setOnClickListener(this);
    }

    

    //<---VALIDATE FORMS START
    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmail.setError("enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }


    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
           mPassword.setError("create a password with at least 6 characters");
            return false;
        }
        return true;
    }

    //VALIDATE FORM END --->

    @Override
    public void onClick(View v) {
        if(v == mSignUpLink){
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }

        if (v == mLoginButton){
                SignInUser();
        }

    }

    private void SignInUser() {

        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password);
        if(!validEmail || !validPassword) return;

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


}
