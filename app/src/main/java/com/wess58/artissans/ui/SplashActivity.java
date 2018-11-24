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

@BindView(R.id.splashText) TextView mSplashText;
@BindView(R.id.footer) TextView mFooter;
@BindView(R.id.uptodown) LinearLayout mUptodown;
@BindView(R.id.downtoup) LinearLayout mDowntoup;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
    downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
    mUptodown.setAnimation(uptodown);
    mDowntoup.setAnimation(downtoup);

    //<--- Rotate image START
    RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(2000);
    rotate.setInterpolator(new LinearInterpolator());

    CircleImageView imageView = findViewById(R.id.imageView);
    imageView.startAnimation(rotate);

    //Rotate image END --->

        Typeface lobsterFonts = Typeface.createFromAsset(getAssets(), "fonts/Lobster_Two/LobsterTwo-Regular.ttf");
        mSplashText.setTypeface(lobsterFonts);

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
        },4000);
    }
}
