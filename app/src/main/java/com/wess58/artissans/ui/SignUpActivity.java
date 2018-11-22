package com.wess58.artissans.ui;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.wess58.artissans.R;
import com.wess58.artissans.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;


//implemented onClickListener on class
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.SinguptextView) TextView mSignUpTextView;
    @BindView (R.id.FirstName) EditText mFirstName;
    @BindView(R.id.LastName) EditText mLastName;
    @BindView(R.id.emailtext) EditText mEmailText;
    @BindView(R.id.Phone) EditText mPhone;
    @BindView(R.id.signUp) Button mSignUpButton;
    @BindView(R.id.emailConfirm) TextView mEmailConfirmText;
    @BindView(R.id.Passord) EditText mPasswordSignUp;


    //member variable to get the instance of the FirebaseAuth object
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private ProgressDialog mAuthProgressDialog;
    private String fName;
    private String lName;
    private String userPhone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        //For Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
//        createAuthStateListener();
        createAuthProgressDialog();


        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mSignUpTextView.setTypeface(oldEnglishFonts);

        Typeface modulaFonts = Typeface.createFromAsset(getAssets(), "fonts/Medula_One/MedulaOne-Regular.ttf");
        mEmailConfirmText.setTypeface(modulaFonts);

        //setting onClickListener New Way :-)
        mSignUpButton.setOnClickListener(this);
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
    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmailText.setError("enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }


    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            mPasswordSignUp.setError("create a password with at least 6 characters");
            return false;
        }
        return true;
    }

    //VALIDATE FORM END --->




    //< - - - onStop and onStart Overrides Start

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

//        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //onStop and onStart Override END - - - >

    //onClick Method
    @Override
    public void onClick(View v) {

        if (v == mSignUpButton){
            createNewUser();

        }

    }

        //< - - - CREATE USER START
    private void createNewUser() {

        final String email = mEmailText.getText().toString().trim();
        String password = mPasswordSignUp.getText().toString().trim();
        fName = mFirstName.getText().toString().trim();
        lName = mLastName.getText().toString().trim();
        userPhone = mPhone.getText().toString().trim();


        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password);
        if(!validEmail || !validPassword) return;

        //this line is only called after the form validation methods have returned true
        mAuthProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

            @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
        //we dismiss the dialog so that the user may either continue using the app, or view any error messages.
                mAuthProgressDialog.dismiss();

                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignUpActivity.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    Toast.makeText(SignUpActivity.this, "Authentication  Failed.", Toast.LENGTH_SHORT).show();

                }
            }

            });

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Email Sent.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        }


    //<--- STORING UserDetails START

//    private void writeNewUser(String userID, String fName, String lName, String userPhone){
//        User user = new User(fName, lName, userPhone);
//        String json = JsonUtils.ToJson(user);
//
//        mDatabaseRef.Child("users").Child(UserID).Child("firstname").Child("lastname").Child("phone").setValueAsync(name);
//
//    }


    //STORING UserDetails END --->


}
