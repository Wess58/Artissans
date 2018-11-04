package com.wess58.artissans;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.SinguptextView) TextView mSignUpTextView;
    @BindView (R.id.FirstName) EditText mFirstName;
    @BindView(R.id.LastName) EditText mLastName;
    @BindView(R.id.UsernameSignUp) EditText mUsernameSignUp;
    @BindView(R.id.emailtext) EditText mEmailText;
    @BindView(R.id.Phone) EditText mPhone;
    @BindView(R.id.signUp) Button mSignUpButton;
    @BindView(R.id.emailConfirm) TextView mEmailConfirmText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/UnifrakturMaguntia/UnifrakturMaguntia-Book.ttf");
        mSignUpTextView.setTypeface(oldEnglishFonts);

        Typeface modulaFonts = Typeface.createFromAsset(getAssets(), "fonts/Medula_One/MedulaOne-Regular.ttf");
        mEmailConfirmText.setTypeface(modulaFonts);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}
