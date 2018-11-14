package com.wess58.artissans.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wess58.artissans.R;
import com.wess58.artissans.models.News;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {


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

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.accessionyearTextView) TextView mAccessionyearTextView;
        @BindView(R.id.techniqueTextView) TextView mTechniqueTextView;
        @BindView(R.id.copyrightTextView) TextView mCopyrightTextView;
        @BindView(R.id.urlTextView) TextView mUrlTextView;
        @BindView(R.id.primaryImageView) CircleImageView mPrimaryImageView;

        private Context mContext;


        public NewsViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindNews(News artNews) {
            Picasso.get().load(artNews.getmImage()).into(mPrimaryImageView);
            mAccessionyearTextView.setText(artNews.getmAccessionYear());
            mTechniqueTextView.setText(artNews.getmTechnique());
            mCopyrightTextView.setText(artNews.getmCopyright());
            mUrlTextView.setText(artNews.getmUrl());

        }
    }
}
