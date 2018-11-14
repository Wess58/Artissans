package com.wess58.artissans.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wess58.artissans.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.Username) EditText mUsername;
    @BindView(R.id.editText2) EditText mPassword;
    @BindView(R.id.button) Button mLoginButton;
    @BindView(R.id.checkBox) CheckBox mStayLoggedin;
    @BindView(R.id.loginText) TextView mLoginTextView;
    @BindView(R.id.signUpLink) TextView mSignUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mLoginTextView.setTypeface( oldEnglishFonts);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        mSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}
