package com.wess58.artissans.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.wess58.artissans.Network;
import com.wess58.artissans.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static View view;


    @BindView(R.id.aboutTextView) TextView mAboutTextView;
    @BindView(R.id.welcomeTextView) TextView mWelcomeTextView;
    @BindView(R.id.loginMain) Button mLoginMain;
    @BindView(R.id.signUpMain) Button msignUpMain;

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        //FONTS
        Typeface modulaFonts = Typeface.createFromAsset(getAssets(), "fonts/Medula_One/MedulaOne-Regular.ttf");
        mAboutTextView.setTypeface(modulaFonts);

        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Reem_Kufi/ReemKufi-Regular.ttf");
        mWelcomeTextView.setTypeface(oldEnglishFonts);

        mLoginMain.setOnClickListener(this);
        msignUpMain.setOnClickListener(this);

        //<--- CHECKING INTERNET CONNECTION START
        if (Network.isInternetAvailable(MainActivity.this)) //returns true if internet available
        {

        } else {
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "No Internet Connection");
        }

        //CHECKING INTERNET CONNECTION END --->

    }

    @Override
    public void onClick(View v) {
        if (v == mLoginMain) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();

        }

        if (v == msignUpMain) {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();

        }

    }


}
