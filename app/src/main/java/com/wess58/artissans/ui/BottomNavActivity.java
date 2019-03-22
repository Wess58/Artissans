package com.wess58.artissans.ui;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wess58.artissans.Network;
import com.wess58.artissans.R;
import com.wess58.artissans.fragments.ArtFeedFragment;
import com.wess58.artissans.fragments.PostFragment;
import com.wess58.artissans.fragments.ProfileFragment;
import com.wess58.artissans.fragments.UploadsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomNavActivity extends AppCompatActivity {

    public static View view;

    boolean doubleBackToExitPressedOnce = false;



    @BindView(R.id.navigationWidget) BottomNavigationView mBottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        ButterKnife.bind(this);

        //TOOLBAR INFLATE
//        Toolbar toolbar = findViewById(R.id.postToolBar);
//        setSupportActionBar(toolbar);

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new ArtFeedFragment());

        if(Network.isInternetAvailable(BottomNavActivity.this)) //returns true if internet available
        {

        }
        else
        {
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "No Internet Connection");

        }

        //CHECKING INTERNET CONNECTION END --->



    }


    private  BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;

                    switch (menuItem.getItemId()){
                        case R.id.NewsItem:
                            fragment = new ArtFeedFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.ArtItem:
                            fragment = new UploadsFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.PostItem:
                            fragment = new PostFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.ProfileItem:
                            fragment = new ProfileFragment();
                            loadFragment(fragment);
                            return true;
                    }
                    return false;
                }
            };


            private void loadFragment(Fragment fragment){

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameBottomContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( BottomNavActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}
