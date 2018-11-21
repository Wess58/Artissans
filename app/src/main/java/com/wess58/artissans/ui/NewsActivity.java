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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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

public class NewsActivity extends AppCompatActivity {
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
