package com.wess58.artissans.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.wess58.artissans.R;
import com.wess58.artissans.fragments.ArtFeedFragment;
import com.wess58.artissans.fragments.PostFragment;
import com.wess58.artissans.fragments.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomNavActivity extends AppCompatActivity {

    private ActionBar toolbar;
    @BindView(R.id.navigationWidget) BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        ButterKnife.bind(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new ArtFeedFragment());
    }

    private  BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment;

                    switch (menuItem.getItemId()){
                        case R.id.ArtItem:
                        fragment = new ArtFeedFragment();
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





}
