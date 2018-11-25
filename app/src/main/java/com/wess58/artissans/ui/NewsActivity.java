package com.wess58.artissans.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.wess58.artissans.Network;
import com.wess58.artissans.R;
import com.wess58.artissans.adapter.NewsListAdapter;
import com.wess58.artissans.models.News;
import com.wess58.artissans.services.NewsService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import android.view.Menu;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity  {
    //TO SEE LOGs
    public static final String TAG = NewsActivity.class.getSimpleName();


    @BindView(R.id.recyclerNewsView) RecyclerView mRecyclerNewsView;
    @BindView(R.id.newsTextView) TextView mNewsTextView;
    private NewsListAdapter mAdapter;

    public ArrayList<News> mNews = new ArrayList<>();


    //Activity doesn't go to previous activity
    @Override
    public  void onBackPressed(){
        moveTaskToBack(false);
    }

    //Creating and Inflating an Overflow Menu START



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            logout();
            return true;
        }
            return super.onOptionsItemSelected(item);
    }

    //END

    //<--- LOGOUT START && returning to MainActivity on Logout
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(NewsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //LOGOUT END --->


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        //TOOLBAR INFLATE
        Toolbar toolbar = findViewById(R.id.newstoolbar);
        setSupportActionBar(toolbar);
        getNews();

        Typeface lobsterFonts = Typeface.createFromAsset(getAssets(), "fonts/Lobster_Two/LobsterTwo-Regular.ttf");
        mNewsTextView.setTypeface(lobsterFonts);

        //<--- CHECKING INTERNET CONNECTION START
        if(Network.isInternetAvailable(NewsActivity.this)) //returns true if internet available
        {

        }
        else
        {
            Toast.makeText(NewsActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }

        //CHECKING INTERNET CONNECTION END --->
    }




    private void getNews() {

        final NewsService newsService = new NewsService();

        NewsService.findArtWorks(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws  IOException {
                try{
                    mNews = newsService.processResults(response);
                    Log.d("Response",response.body().string());

                }catch (Exception ex){

                }
                NewsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new NewsListAdapter(getApplicationContext(), mNews);
                        mRecyclerNewsView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
                        mRecyclerNewsView.setLayoutManager(layoutManager);
                        mRecyclerNewsView.setHasFixedSize(true);

                    }
                });
            }
        });
    }


}
