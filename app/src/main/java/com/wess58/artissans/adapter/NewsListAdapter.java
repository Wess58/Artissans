package com.wess58.artissans.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wess58.artissans.R;
import com.wess58.artissans.models.News;
import com.wess58.artissans.ui.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>  {

    public static final int MAX_WIDTH = 600;
    public static final int MAX_HEIGHT = 700;
    private ArrayList<News> mNews= new ArrayList<>();
    private Context mContext;

    public NewsListAdapter(Context context, ArrayList<News> artNews){
        mContext = context;
        mNews = artNews;
    }



    @Override
    public NewsListAdapter.NewsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        ButterKnife.bind(this, view);
        return viewHolder;
    }








    @Override
    public void onBindViewHolder( NewsListAdapter.NewsViewHolder holder, int position ) {
            holder.bindNews(mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }



    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.accessionyearTextView) TextView mAccessionyearTextView;
        @BindView(R.id.techniqueTextView) TextView mTechniqueTextView;
        @BindView(R.id.copyrightTextView) TextView mCopyrightTextView;
        @BindView(R.id.urlTextView) TextView mUrlTextView;
        @BindView(R.id.primaryImageView) ImageView mPrimaryImageView;
        @BindView(R.id.classificationTextView) TextView mClassificationTextView;

        private Context mContext;


        public NewsViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            mUrlTextView.setOnClickListener(this);
            mContext = itemView.getContext();
;
        }


        public void bindNews(News artNews) {
            Picasso.get().load(artNews.getmImage()).placeholder(R.drawable.art).resize(MAX_WIDTH, MAX_HEIGHT).into(mPrimaryImageView);
            mAccessionyearTextView.setText(artNews.getmAccessionYear());
            mTechniqueTextView.setText(artNews.getmTechnique());
            mCopyrightTextView.setText(artNews.getmCopyright());
            mUrlTextView.setText(artNews.getmUrl());
            mClassificationTextView.setText(artNews.getmClass());


        }


//        @Override
//        public void onClick(View v) {
//            if(v == mUrlTextView){
//                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNews.getmUrl()));
//                startActivity(webIntent);
//
//            }
//        }
    }
}
