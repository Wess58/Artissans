package com.wess58.artissans;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.Username) EditText mUsername;
    @BindView(R.id.editText2) EditText mPassword;
    @BindView(R.id.button) Button mLoginButton;
    @BindView(R.id.checkBox) CheckBox mStayLoggedin;
    @BindView(R.id.loginText) TextView mLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
    }
}
