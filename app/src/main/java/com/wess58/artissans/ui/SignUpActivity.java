package com.wess58.artissans.ui;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wess58.artissans.Network;
import com.wess58.artissans.R;
import com.wess58.artissans.Utils;
import com.wess58.artissans.models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


//implemented onClickListener on class
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    //FOR FIREBASE AUTH
    private FirebaseAuth mAuth;
    private ProgressDialog mAuthProgressDialog;

    DatabaseReference databaseReference;

    public static View view;
    private static Animation shakeAnimation;

    boolean doubleBackToExitPressedOnce = false;




    @BindView(R.id.SignUpLayout) LinearLayout mSignUpLayout;
    @BindView(R.id.SinguptextView) TextView mSignUpTextView;
    @BindView (R.id.FirstName) EditText firstName;
    @BindView(R.id.LastName) EditText lastName;
    @BindView(R.id.emailtext) EditText Email;
    @BindView(R.id.Phone) EditText mPhone;
    @BindView(R.id.signUp) Button mSignUpButton;
    @BindView(R.id.back_to_login) TextView mBack_to_login;
    @BindView(R.id.Passord) EditText mPasswordSignUp;
    @BindView(R.id.confirm_Password) EditText mConfirm_Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    //STORING DATA TO FIREBASE
        databaseReference = FirebaseDatabase.getInstance().getReference("User");


        //For Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        createAuthProgressDialog();


        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mSignUpTextView.setTypeface(oldEnglishFonts);

        Typeface modulaFonts = Typeface.createFromAsset(getAssets(), "fonts/Medula_One/MedulaOne-Regular.ttf");
        mBack_to_login.setTypeface(modulaFonts);

        //setting onClickListener New Way :-)
        mSignUpButton.setOnClickListener(this);

        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        //<--- CHECKING INTERNET CONNECTION START
        if(Network.isInternetAvailable(SignUpActivity.this)) //returns true if internet available
        {

        }
        else
        {
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "No Internet Connection");

        }

        //CHECKING INTERNET CONNECTION END --->

    }

    //<--- PROGRESSDIALOG START
    //setCancelable() to "false" so that users cannot close the dialog manually.
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading ...");
        mAuthProgressDialog.setMessage("Creating Account...");
        mAuthProgressDialog.setCancelable(false);

    }
    //PROGRESSDIALOG END --->


    //onClick Method
    @Override
    public void onClick(View v) {

        if (v == mSignUpButton){
            checkValidation();
        }

    }

        //< - - - CREATE USER START
        private void SignInUser() {

            final String email = Email.getText().toString().trim();
            String password = mPasswordSignUp.getText().toString().trim();


            mAuthProgressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password )
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mAuthProgressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignUpActivity.this, BottomNavActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Invalid Credentials.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }


    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFirstName = firstName.getText().toString();
        String getLastName = lastName.getText().toString();
        String getEmailId = Email.getText().toString();
        String getMobileNumber = mPhone.getText().toString();
        String getPassword = mPasswordSignUp.getText().toString();
        String getConfirmPassword = mConfirm_Password.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (    getFirstName.equals("") || getFirstName.length() == 0
                ||getLastName.equals("") || getLastName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("") || getConfirmPassword.length() == 0){

            mSignUpButton.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "All fields are required.");
        }



        // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "Both password doesn't match.");

            // Else do signup or do your stuff
        else
            SignInUser();

//   Make sure user should check Terms and Conditions checkbox
// *TO BE USED LATER*
//        else if (!terms_conditions.isChecked())
//            new CustomToast().Show_Toast(getApplicationContext(), view,
//                    "Please select Terms and Conditions.");




    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( SignUpActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
