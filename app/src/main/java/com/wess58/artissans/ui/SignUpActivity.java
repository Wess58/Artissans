package com.wess58.artissans.ui;


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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wess58.artissans.R;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        //For Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
//        createAuthStateListener();



        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mSignUpTextView.setTypeface(oldEnglishFonts);

        Typeface modulaFonts = Typeface.createFromAsset(getAssets(), "fonts/Medula_One/MedulaOne-Regular.ttf");
        mEmailConfirmText.setTypeface(modulaFonts);

        //setting onClickListener New Way :-)
        mSignUpButton.setOnClickListener(this);
    }

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

        final String firstName = mFirstName.getText().toString().trim();
        final String lastName = mLastName.getText().toString().trim();
        final String email = mEmailText.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        String password = mPasswordSignUp.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

            @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
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

        }

    // < - - - LOGIN USER -> TO SORT LATER IN LOGIN_ACTIVITY
    //Listening for User Authentication: here we need to inform our application when the user's account is successfully authenticated

    //FirebaseAuth.AuthStateListener interface (This interface listens to changes in the current AuthState)
    //The onAuthStateChanged() method returns FirebaseAuth data.

//    private void createAuthStateListener(){
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                final FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                if (user != null){
//                    Intent intent = new Intent( SignUpActivity.this, NewsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };
//    }

    //LOGIN USER END - - - >





    //

}







//    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
//    startActivity(intent);
//    finish();