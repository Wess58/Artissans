package com.wess58.artissans.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
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
import com.wess58.artissans.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private static View view;
    private static Animation shakeAnimation;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mAuthProgressDialog;

    boolean doubleBackToExitPressedOnce = false;


    @BindView(R.id.Email) EditText mEmail;
    @BindView(R.id.password1) EditText mPassword;
    @BindView(R.id.button) Button mLoginButton;
    @BindView(R.id.checkBox) CheckBox mStayLoggedin;
    @BindView(R.id.loginText) TextView mLoginTextView;
    @BindView(R.id.signUpLink) TextView mSignUpLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        createAuthProgressDialog();
        addAuth();

        mLoginButton.setOnClickListener(this);
        mSignUpLink.setOnClickListener(this);


        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mLoginTextView.setTypeface( oldEnglishFonts);


        //<--- CHECKING INTERNET CONNECTION START
        if(Network.isInternetAvailable(LogInActivity.this))
            //returns true if internet available
        {

        }
        else
        {
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "No Internet Connection");        }

        //CHECKING INTERNET CONNECTION END --->






    }

    //<--- PROGRESSDIALOG START
    //setCancelable() to "false" so that users cannot close the dialog manually.
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading ...");
        mAuthProgressDialog.setMessage("Authentication in Progress...");
        mAuthProgressDialog.setCancelable(false);

    }
    //PROGRESSDIALOG END --->

    @Override
    public void onClick(View v) {

        if (v == mLoginButton){

            checkValidation();
        }

        if(v == mSignUpLink){

            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();

        }

    }

    // Check Validation before login
    private void checkValidation () {
        // Get email id and password
        String getEmailId = mEmail.getText().toString();
        String getPassword = mPassword.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            mLoginButton.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getBaseContext(), view,
                    "Enter both credentials.");

        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "Your Email Id is Invalid.");
            // Else do login and do your stuff
        else
            login(mEmail.getText().toString(),mPassword.getText().toString());

    }




    public  void login (String email,String password){
        mAuthProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        if(task.isSuccessful()){
                            Intent intent=new Intent(LogInActivity.this,BottomNavActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            new CustomToast().Show_Toast(getApplicationContext(), view,
                                    "Authentication Failed.");                        }
                    }
                });
    }

    public void addAuth(){
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent( LogInActivity.this, BottomNavActivity.class);
                    startActivity(intent);
                }
            }
        };
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( LogInActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected  void onStart(){
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected  void  onStop(){
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    }
