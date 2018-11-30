package com.wess58.artissans.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wess58.artissans.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {

Animation uptodown,downtoup;

@BindView(R.id.splashText1) TextView mSplashText1;
@BindView(R.id.splashText2) TextView mSplashText2;
@BindView(R.id.splashText3) TextView mSplashText3;
@BindView(R.id.footer) TextView mFooter;
@BindView(R.id.uptodown) LinearLayout mUptodown;
@BindView(R.id.downtoup) LinearLayout mDowntoup;
@BindView(R.id.circleimageView) CircleImageView mCircleImageView;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
    downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
    mUptodown.setAnimation(uptodown);
    mDowntoup.setAnimation(downtoup);

    //<--- for FadeIn anim

//    Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
//    fadeIn.setDuration(3000);
//    mFooter.startAnimation(fadeIn);

    //for FadeIn END --->


    //<--- Rotate image START

    RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(2800);
    rotate.setInterpolator(new LinearInterpolator());
    mCircleImageView.startAnimation(rotate);

    //Rotate image END --->

        Typeface lobsterFonts = Typeface.createFromAsset(getAssets(), "fonts/Lobster_Two/lobster.ttf");
        mSplashText1.setTypeface(lobsterFonts);
        mSplashText3.setTypeface(lobsterFonts);

        Typeface brushFonts = Typeface.createFromAsset(getAssets(),"fonts/Permanent_Marker/PermanentMarker-Regular.ttf");
        mSplashText2.setTypeface(brushFonts);

        Typeface modulaFonts = Typeface.createFromAsset(getAssets(), "fonts/Medula_One/MedulaOne-Regular.ttf");
        mFooter.setTypeface(modulaFonts);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }



}
