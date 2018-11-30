package com.wess58.artissans.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtFeedFragment extends Fragment {

    @BindView(R.id.recyclerNewsView) RecyclerView mRecyclerNewsView;
    @BindView(R.id.newsTextView) TextView mNewsTextView;
    private NewsListAdapter mAdapter;

    public ArrayList<News> mNews = new ArrayList<>();


    public ArtFeedFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {


        // Inflate the layout for this fragment

         View root = inflater.inflate(R.layout.activity_news, container, false);
        ButterKnife.bind(this,root);
         getNews();

        Animation fadeIn = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        fadeIn.setDuration(3000);
        mNewsTextView.startAnimation(fadeIn);




         return root;
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new NewsListAdapter(getContext(), mNews);
                        mRecyclerNewsView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerNewsView.setLayoutManager(layoutManager);
                        mRecyclerNewsView.setHasFixedSize(true);

                    }
                });
            }
        });
    }

}
